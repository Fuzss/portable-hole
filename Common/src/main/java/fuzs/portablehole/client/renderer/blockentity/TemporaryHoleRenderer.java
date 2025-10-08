package fuzs.portablehole.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.portablehole.PortableHole;
import fuzs.portablehole.config.ServerConfig;
import fuzs.portablehole.world.level.block.entity.TemporaryHoleBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.EndPortalRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.EnumSet;

public class TemporaryHoleRenderer implements BlockEntityRenderer<TemporaryHoleBlockEntity, EndPortalRenderState> {

    public TemporaryHoleRenderer(BlockEntityRendererProvider.Context context) {
        // NO-OP
    }

    @Override
    public EndPortalRenderState createRenderState() {
        return new EndPortalRenderState();
    }

    @Override
    public void extractRenderState(TemporaryHoleBlockEntity blockEntity, EndPortalRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity,
                renderState,
                partialTick,
                cameraPosition,
                crumblingOverlay);
        renderState.facesToShow.clear();
        for (Direction direction : Direction.values()) {
            if (blockEntity.shouldRenderFace(direction)) {
                renderState.facesToShow.add(direction);
            }
        }
    }

    @Override
    public void submit(EndPortalRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (!PortableHole.CONFIG.get(ServerConfig.class).visuals.portalOverlay) {
            return;
        }

        submitNodeCollector.submitCustomGeometry(poseStack,
                RenderType.endGateway(),
                (PoseStack.Pose pose, VertexConsumer vertexConsumer) -> {
                    this.renderCube(renderState.facesToShow, pose.pose(), vertexConsumer);
                });
    }

    private void renderCube(EnumSet<Direction> facesToShow, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        // don't render in same z-level as opposite face from other block, same offset as shulker boxes
        this.renderFace(facesToShow,
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
        this.renderFace(facesToShow,
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
        this.renderFace(facesToShow,
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
        this.renderFace(facesToShow,
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
        this.renderFace(facesToShow,
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
        this.renderFace(facesToShow,
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

    private void renderFace(EnumSet<Direction> facesToShow, Matrix4f matrix4f, VertexConsumer vertexConsumer, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, Direction direction) {
        if (facesToShow.contains(direction)) {
            vertexConsumer.addVertex(matrix4f, x0, y1, z3);
            vertexConsumer.addVertex(matrix4f, x1, y1, z2);
            vertexConsumer.addVertex(matrix4f, x1, y0, z1);
            vertexConsumer.addVertex(matrix4f, x0, y0, z0);
        }
    }
}
