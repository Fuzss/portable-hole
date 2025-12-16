package fuzs.portablehole.world.level.block.entity;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.config.ServerConfig;
import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.block.v1.entity.TickingBlockEntity;
import fuzs.puzzleslib.api.util.v1.ValueSerializationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

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
    @Nullable
    private Direction growthDirection;
    private int growthDistance;

    public TemporaryHoleBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.TEMPORARY_HOLE_BLOCK_ENTITY_TYPE.value(), pos, state);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.sourceState = valueInput.read(TAG_BLOCK_STATE_SOURCE, BlockState.CODEC)
                .filter(Predicate.not(BlockBehaviour.BlockStateBase::isAir))
                .orElse(null);
        this.blockEntityTag = valueInput.read(TAG_BLOCK_ENTITY_SOURCE_TAG, CompoundTag.CODEC).orElse(null);
        this.lifetimeTicks = valueInput.getIntOr(TAG_LIFETIME_TICKS, 0);
        this.growthDirection = valueInput.read(TAG_GROWTH_DIRECTION, Direction.CODEC).orElse(null);
        this.growthDistance = valueInput.getIntOr(TAG_GROWTH_DISTANCE, 0);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.storeNullable(TAG_BLOCK_STATE_SOURCE, BlockState.CODEC, this.sourceState);
        valueOutput.storeNullable(TAG_BLOCK_ENTITY_SOURCE_TAG, CompoundTag.CODEC, this.blockEntityTag);
        valueOutput.putInt(TAG_LIFETIME_TICKS, this.lifetimeTicks);
        valueOutput.storeNullable(TAG_GROWTH_DIRECTION, Direction.CODEC, this.growthDirection);
        valueOutput.putInt(TAG_GROWTH_DISTANCE, this.growthDistance);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        if (this.sourceState != null) {
            tag.put(TAG_BLOCK_STATE_SOURCE, NbtUtils.writeBlockState(this.sourceState));
        }
        return tag;
    }

    public BlockState getSourceBlockState() {
        return this.sourceState;
    }

    public boolean shouldRenderFace(Direction direction) {
        BlockPos blockPos = this.getBlockPos();
        BlockState neighborBlockState = this.getLevel().getBlockState(blockPos.relative(direction));
        return !Block.shouldRenderFace(this.getBlockState(), neighborBlockState, direction);
    }

    @Override
    public void serverTick() {
        if (this.sourceState == null) {
            this.getLevel().removeBlock(this.getBlockPos(), false);
        } else if (this.lifetimeTicks <= 0) {
            this.getLevel().setBlock(this.getBlockPos(), this.sourceState, 3);
            if (this.blockEntityTag != null) {
                BlockEntity blockEntity = this.getLevel().getBlockEntity(this.getBlockPos());
                if (blockEntity != null) {
                    ValueSerializationHelper.load(this.problemPath(),
                            this.getLevel().registryAccess(),
                            this.blockEntityTag,
                            blockEntity::loadWithComponents);
                }
            }
            if (PortableHole.CONFIG.get(ServerConfig.class).visuals.particlesForReappearingBlocks) {
                // plays the block breaking sound to provide some feedback
                this.getLevel()
                        .levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK,
                                this.getBlockPos(),
                                Block.getId(this.sourceState));
            }
        } else {
            this.lifetimeTicks--;
            tryGrowInDirection(this.getLevel(), this.getBlockPos(), this);
        }
    }

    private static void tryGrowInDirection(Level level, BlockPos pos, TemporaryHoleBlockEntity blockEntity) {
        if (blockEntity.growthDistance > 0 && blockEntity.growthDirection != null) {
            setTemporaryHoleBlock(level,
                    pos.relative(blockEntity.growthDirection),
                    blockEntity.growthDirection,
                    blockEntity.growthDistance - 1);
            blockEntity.growthDistance = 0;
            blockEntity.growthDirection = null;
        }
    }

    public static boolean setTemporaryHoleBlock(Level level, BlockPos blockPos, Direction growthDirection, int growthDistance) {
        if (isValidHolePosition(level, blockPos)) {
            BlockState state = level.getBlockState(blockPos);
            CompoundTag blockEntityTag = null;
            if (PortableHole.CONFIG.get(ServerConfig.class).replaceBlockEntities) {
                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                if (blockEntity != null) {
                    blockEntityTag = blockEntity.saveWithoutMetadata(level.registryAccess());
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
            } else if (!blockState.isAir() && (!blockState.hasBlockEntity()
                    || PortableHole.CONFIG.get(ServerConfig.class).replaceBlockEntities)
                    && !blockState.is(ModRegistry.PORTABLE_HOLE_IMMUNE_BLOCK_TAG)) {
                Block block = blockState.getBlock();
                if (block instanceof DoublePlantBlock || block instanceof DoorBlock || block instanceof BedBlock) {
                    return false;
                }
                float destroySpeed = blockState.getDestroySpeed(level, blockPos);
                return destroySpeed != -1.0F
                        && destroySpeed <= PortableHole.CONFIG.get(ServerConfig.class).maxBlockHardness;
            }
        }
        return false;
    }
}
