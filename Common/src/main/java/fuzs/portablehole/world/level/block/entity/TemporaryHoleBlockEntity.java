package fuzs.portablehole.world.level.block.entity;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.config.ServerConfig;
import fuzs.portablehole.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TemporaryHoleBlockEntity extends BlockEntity {
    private static final String TAG_BLOCK_STATE_SOURCE = "SourceState";
    private static final String TAG_BLOCK_ENTITY_SOURCE_TAG = "SourceEntityTag";
    private static final String TAG_LIFETIME_TICKS = "LifetimeTicks";
    private static final String TAG_GROWTH_DIRECTION = "GrowthDirection";
    private static final String TAG_GROWTH_DISTANCE = "GrowthDistance";

    private BlockState sourceState;
    @Nullable
    private CompoundTag blockEntityTag;
    private int lifetimeTicks;
    private Direction growthDirection;
    private int growthDistance;

    public TemporaryHoleBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.TEMPORARY_HOLE_BLOCK_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.sourceState != null) {
            tag.put(TAG_BLOCK_STATE_SOURCE, NbtUtils.writeBlockState(this.sourceState));
        }
        if (this.blockEntityTag != null) {
            tag.put(TAG_BLOCK_ENTITY_SOURCE_TAG, this.blockEntityTag);
        }
        tag.putInt(TAG_LIFETIME_TICKS, this.lifetimeTicks);
        if (this.growthDirection != null) {
            tag.putByte(TAG_GROWTH_DIRECTION, (byte) this.growthDirection.ordinal());
        }
        if (this.growthDistance > 0) {
            tag.putInt(TAG_GROWTH_DISTANCE, this.growthDistance);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_BLOCK_STATE_SOURCE, Tag.TAG_COMPOUND)) {
            HolderGetter<Block> holdergetter = this.level != null ? this.level.holderLookup(Registries.BLOCK) : BuiltInRegistries.BLOCK.asLookup();
            this.sourceState = NbtUtils.readBlockState(holdergetter, tag.getCompound(TAG_BLOCK_STATE_SOURCE));
            if (this.sourceState.isAir()) {
                this.sourceState = null;
            }
        }
        if (tag.contains(TAG_BLOCK_ENTITY_SOURCE_TAG, Tag.TAG_COMPOUND)) {
            this.blockEntityTag = tag.getCompound(TAG_BLOCK_ENTITY_SOURCE_TAG);
        }
        this.lifetimeTicks = tag.getInt(TAG_LIFETIME_TICKS);
        if (tag.contains(TAG_GROWTH_DIRECTION, Tag.TAG_BYTE)) {
            this.growthDirection = Direction.values()[tag.getByte(TAG_GROWTH_DIRECTION)];
        }
        this.growthDistance = tag.getInt(TAG_GROWTH_DISTANCE);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (this.sourceState != null) {
            tag.put(TAG_BLOCK_STATE_SOURCE, NbtUtils.writeBlockState(this.sourceState));
        }
        return tag;
    }

    public BlockState getSourceBlockState() {
        return this.sourceState;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TemporaryHoleBlockEntity blockEntity) {
        if (blockEntity.sourceState == null) {
            level.removeBlock(pos, false);
        } else if (blockEntity.lifetimeTicks <= 0) {
            level.setBlock(pos, blockEntity.sourceState, 3);
            if (blockEntity.blockEntityTag != null && level.getBlockEntity(pos) != null) {
                level.getBlockEntity(pos).load(blockEntity.blockEntityTag);
            }
            if (PortableHole.CONFIG.get(ServerConfig.class).particlesForReappearingBlocks) {
                // plays the block breaking sound to provide some feedback
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(blockEntity.sourceState));
            }
        } else {
            blockEntity.lifetimeTicks--;
            tryGrowInDirection(level, pos, blockEntity);
        }
    }

    private static void tryGrowInDirection(Level level, BlockPos pos, TemporaryHoleBlockEntity blockEntity) {
        if (blockEntity.growthDistance > 0 && blockEntity.growthDirection != null) {
            setTemporaryHoleBlock(level, pos.relative(blockEntity.growthDirection), blockEntity.growthDirection, blockEntity.growthDistance - 1);
            blockEntity.growthDistance = 0;
            blockEntity.growthDirection = null;
        }
    }

    public static boolean setTemporaryHoleBlock(Level level, BlockPos pos, Direction growthDirection, int growthDistance) {
        if (isValidHolePosition(level, pos)) {
            BlockState state = level.getBlockState(pos);
            CompoundTag blockEntityTag = null;
            if (PortableHole.CONFIG.get(ServerConfig.class).replaceBlockEntities) {
                BlockEntity blockentity = level.getBlockEntity(pos);
                if (blockentity != null) {
                    blockEntityTag = blockentity.saveWithoutMetadata();
                    Clearable.tryClear(blockentity);
                }
            }
            boolean replaceBlock = !state.is(ModRegistry.TEMPORARY_HOLE_BLOCK.get());
            if (replaceBlock) {
                level.setBlock(pos, ModRegistry.TEMPORARY_HOLE_BLOCK.get().defaultBlockState(), 3);
            }
            if (level.getBlockEntity(pos) instanceof TemporaryHoleBlockEntity blockEntity) {
                if (replaceBlock) {
                    blockEntity.sourceState = state;
                    blockEntity.blockEntityTag = blockEntityTag;
                }
                blockEntity.growthDirection = growthDirection;
                blockEntity.growthDistance = growthDistance;
                blockEntity.lifetimeTicks = PortableHole.CONFIG.get(ServerConfig.class).temporaryHoleDuration;
            }
            return true;
        }
        return false;
    }

    public static boolean isValidHolePosition(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (level.hasChunkAt(pos) && level.isInWorldBounds(pos)) {
            if (state.is(ModRegistry.TEMPORARY_HOLE_BLOCK.get())) {
                return true;
            } else if (!state.isAir() && (!state.hasBlockEntity() || PortableHole.CONFIG.get(ServerConfig.class).replaceBlockEntities) && !state.is(ModRegistry.PORTABLE_HOLE_IMMUNE_TAG)) {
                Block block = state.getBlock();
                if (block instanceof DoublePlantBlock || block instanceof DoorBlock || block instanceof BedBlock) {
                    return false;
                }
                float destroySpeed = state.getDestroySpeed(level, pos);
                return destroySpeed != -1.0F && destroySpeed <= PortableHole.CONFIG.get(ServerConfig.class).maxBlockHardness;
            }
        }
        return false;
    }
}
