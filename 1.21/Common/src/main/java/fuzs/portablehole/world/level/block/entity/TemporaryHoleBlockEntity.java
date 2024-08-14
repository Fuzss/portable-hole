package fuzs.portablehole.world.level.block.entity;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.config.ServerConfig;
import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.block.v1.entity.TickingBlockEntity;
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

public class TemporaryHoleBlockEntity extends BlockEntity implements TickingBlockEntity {
    public static final String TAG_BLOCK_STATE_SOURCE = PortableHole.id("source_state").toString();
    public static final String TAG_BLOCK_ENTITY_SOURCE_TAG = PortableHole.id("source_block_entity_tag").toString();
    public static final String TAG_LIFETIME_TICKS = PortableHole.id("lifetime_ticks").toString();
    public static final String TAG_GROWTH_DIRECTION = PortableHole.id("growth_direction").toString();
    public static final String TAG_GROWTH_DISTANCE = PortableHole.id("growth_distance").toString();

    private BlockState sourceState;
    @Nullable
    private CompoundTag blockEntityTag;
    private int lifetimeTicks;
    private Direction growthDirection;
    private int growthDistance;

    public TemporaryHoleBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.TEMPORARY_HOLE_BLOCK_ENTITY_TYPE.value(), pos, state);
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

    @Override
    public void serverTick() {
        if (this.sourceState == null) {
            this.getLevel().removeBlock(this.getBlockPos(), false);
        } else if (this.lifetimeTicks <= 0) {
            this.getLevel().setBlock(this.getBlockPos(), this.sourceState, 3);
            if (this.blockEntityTag != null && this.getLevel().getBlockEntity(this.getBlockPos()) != null) {
                this.getLevel().getBlockEntity(this.getBlockPos()).load(this.blockEntityTag);
            }
            if (PortableHole.CONFIG.get(ServerConfig.class).particlesForReappearingBlocks) {
                // plays the block breaking sound to provide some feedback
                this.getLevel().levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, this.getBlockPos(), Block.getId(this.sourceState));
            }
        } else {
            this.lifetimeTicks--;
            tryGrowInDirection(this.getLevel(), this.getBlockPos(), this);
        }
    }

    private static void tryGrowInDirection(Level level, BlockPos pos, TemporaryHoleBlockEntity blockEntity) {
        if (blockEntity.growthDistance > 0 && blockEntity.growthDirection != null) {
            setTemporaryHoleBlock(level, pos.relative(blockEntity.growthDirection), blockEntity.growthDirection, blockEntity.growthDistance - 1);
            blockEntity.growthDistance = 0;
            blockEntity.growthDirection = null;
        }
    }

    public static boolean setTemporaryHoleBlock(Level level, BlockPos blockPos, Direction growthDirection, int growthDistance) {
        if (isValidHolePosition(level, blockPos)) {
            BlockState state = level.getBlockState(blockPos);
            CompoundTag blockEntityTag = null;
            if (PortableHole.CONFIG.get(ServerConfig.class).replaceBlockEntities) {
                BlockEntity blockentity = level.getBlockEntity(blockPos);
                if (blockentity != null) {
                    blockEntityTag = blockentity.saveWithoutMetadata();
                    Clearable.tryClear(blockentity);
                }
            }
            boolean replaceBlock = !state.is(ModRegistry.TEMPORARY_HOLE_BLOCK.value());
            if (replaceBlock) {
                level.setBlock(blockPos, ModRegistry.TEMPORARY_HOLE_BLOCK.value().defaultBlockState(), 3);
            }
            if (level.getBlockEntity(blockPos) instanceof TemporaryHoleBlockEntity blockEntity) {
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

    public static boolean isValidHolePosition(Level level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        if (level.hasChunkAt(blockPos) && level.isInWorldBounds(blockPos)) {
            if (blockState.is(ModRegistry.TEMPORARY_HOLE_BLOCK.value())) {
                return true;
            } else if (!blockState.isAir() && (!blockState.hasBlockEntity() || PortableHole.CONFIG.get(ServerConfig.class).replaceBlockEntities) && !blockState.is(ModRegistry.PORTABLE_HOLE_IMMUNE_TAG)) {
                Block block = blockState.getBlock();
                if (block instanceof DoublePlantBlock || block instanceof DoorBlock || block instanceof BedBlock) {
                    return false;
                }
                float destroySpeed = blockState.getDestroySpeed(level, blockPos);
                return destroySpeed != -1.0F && destroySpeed <= PortableHole.CONFIG.get(ServerConfig.class).maxBlockHardness;
            }
        }
        return false;
    }
}
