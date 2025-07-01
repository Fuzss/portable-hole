package fuzs.portablehole.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.portablehole.PortableHole;
import fuzs.portablehole.config.ServerConfig;
import fuzs.portablehole.world.level.block.entity.TemporaryHoleBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class TemporaryHoleRenderer implements BlockEntityRenderer<TemporaryHoleBlockEntity> {

    public TemporaryHoleRenderer(BlockEntityRendererProvider.Context context) {
        // NO-OP
    }

    @Override
    public void render(TemporaryHoleBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPosition) {
        if (!PortableHole.CONFIG.get(ServerConfig.class).portalOverlay) return;
        Matrix4f matrix4f = poseStack.last().pose();
        this.renderCube(blockEntity, matrix4f, bufferSource.getBuffer(RenderType.endGateway()));
    }

    private void renderCube(TemporaryHoleBlockEntity blockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        // don't render in same z-level as opposite face from other block, same offset as shulker boxes
        this.renderFace(blockEntity,
                matrix4f,
                vertexConsumer,
                0.0F,
                1.0F,
                0.0F,
                1.0F,
                0.9995F,
                0.9995F,
                0.9995F,
                0.9995F,
                Direction.SOUTH);
        this.renderFace(blockEntity,
                matrix4f,
                vertexConsumer,
                0.0F,
                1.0F,
                1.0F,
                0.0F,
                0.0005F,
                0.0005F,
                0.0005F,
                0.0005F,
                Direction.NORTH);
        this.renderFace(blockEntity,
                matrix4f,
                vertexConsumer,
                0.9995F,
                0.9995F,
                1.0F,
                0.0F,
                0.0F,
                1.0F,
                1.0F,
                0.0F,
                Direction.EAST);
        this.renderFace(blockEntity,
                matrix4f,
                vertexConsumer,
                0.0005F,
                0.0005F,
                0.0F,
                1.0F,
                0.0F,
                1.0F,
                1.0F,
                0.0F,
                Direction.WEST);
        this.renderFace(blockEntity,
                matrix4f,
                vertexConsumer,
                0.0F,
                1.0F,
                0.0005F,
                0.0005F,
                0.0F,
                0.0F,
                1.0F,
                1.0F,
                Direction.DOWN);
        this.renderFace(blockEntity,
                matrix4f,
                vertexConsumer,
                0.0F,
                1.0F,
                0.9995F,
                0.9995F,
                1.0F,
                1.0F,
                0.0F,
                0.0F,
                Direction.UP);
    }

    private void renderFace(TemporaryHoleBlockEntity blockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, Direction direction) {
        if (!shouldRenderFace(blockEntity, direction)) {
            vertexConsumer.addVertex(matrix4f, x0, y1, z3);
            vertexConsumer.addVertex(matrix4f, x1, y1, z2);
            vertexConsumer.addVertex(matrix4f, x1, y0, z1);
            vertexConsumer.addVertex(matrix4f, x0, y0, z0);
        }
    }

    private static boolean shouldRenderFace(TemporaryHoleBlockEntity blockEntity, Direction direction) {
        BlockPos blockPos = blockEntity.getBlockPos();
        BlockState neighborBlockState = blockEntity.getLevel().getBlockState(blockPos.relative(direction));
        return Block.shouldRenderFace(blockEntity.getBlockState(), neighborBlockState, direction);
    }
}