package fuzs.portablehole.world.level.block;

import com.mojang.serialization.MapCodec;
import fuzs.portablehole.PortableHole;
import fuzs.portablehole.config.ServerConfig;
import fuzs.portablehole.core.particles.SparkleParticleData;
import fuzs.portablehole.init.ModRegistry;
import fuzs.portablehole.world.level.block.entity.TemporaryHoleBlockEntity;
import fuzs.puzzleslib.api.block.v1.entity.TickingEntityBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TemporaryHoleBlock extends BaseEntityBlock implements TickingEntityBlock<TemporaryHoleBlockEntity>, LiquidBlockContainer {
    public static final MapCodec<TemporaryHoleBlock> CODEC = simpleCodec(TemporaryHoleBlock::new);

    public TemporaryHoleBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntityType<? extends TemporaryHoleBlockEntity> getBlockEntityType() {
        return ModRegistry.TEMPORARY_HOLE_BLOCK_ENTITY_TYPE.value();
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState blockState) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!PortableHole.CONFIG.get(ServerConfig.class).sparkParticles) return;
        if (level.getBlockEntity(pos) instanceof TemporaryHoleBlockEntity blockEntity) {
            if (blockEntity.getSourceBlockState() != null) {
                int color = ChatFormatting.BLUE.getColor();
                SparkleParticleData sparkle = SparkleParticleData.noClip(1.0F,
                        (color >> 16 & 0xFF) / 255.0F,
                        (color >> 8 & 0xFF) / 255.0F,
                        (color & 0xFF) / 255.0F,
                        20);
                VoxelShape occlusionShape = blockEntity.getSourceBlockState().getShape(level, pos);
                occlusionShape.forAllEdges((x0, y0, z0, x1, y1, z1) -> {
                    Vec3 from = new Vec3(x0, y0, z0);
                    Vec3 to = new Vec3(x1, y1, z1);
                    if (Math.pow(random.nextDouble(), 2.0) < from.distanceToSqr(to)) {
                        Vec3 vec3 = from.lerp(to, random.nextDouble());
                        level.addParticle(sparkle,
                                pos.getX() + vec3.x(),
                                pos.getY() + vec3.y(),
                                pos.getZ() + vec3.z(),
                                0.0,
                                0.0,
                                0.0);
                    }
                });
            }
        }
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        // prevent liquids from flowing inside
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}
