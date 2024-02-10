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
import org.joml.Matrix4f;

public class TemporaryHoleRenderer implements BlockEntityRenderer<TemporaryHoleBlockEntity> {

   public TemporaryHoleRenderer(BlockEntityRendererProvider.Context context) {
      // NO-OP
   }

   @Override
   public void render(TemporaryHoleBlockEntity blockEntity, float p_112651_, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_112654_, int p_112655_) {
      if (!PortableHole.CONFIG.get(ServerConfig.class).portalOverlay) return;
      Matrix4f matrix4f = poseStack.last().pose();
      this.renderCube(blockEntity, matrix4f, multiBufferSource.getBuffer(RenderType.endGateway()));
   }

   // Motified verticies, shouldn't have issues with shaders anymore.
   private void renderCube(TemporaryHoleBlockEntity blockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
      // don't render in same z-level as opposite face from other block, same offset as shulker boxes
      this.renderFace(blockEntity, matrix4f, vertexConsumer, 1.0F, 0.0F, 0.9995F, 0.9995F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.SOUTH);
      this.renderFace(blockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0005F, 0.0005F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.NORTH);
      this.renderFace(blockEntity, matrix4f, vertexConsumer, 0.9995F, 0.9995F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
      this.renderFace(blockEntity, matrix4f, vertexConsumer, 0.0005F, 0.0005F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
      this.renderFace(blockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0005F, 0.0005F, 0.0005F, 0.0005F, Direction.DOWN);
      this.renderFace(blockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 0.9995F, 0.9995F, 0.9995F, 0.9995F, Direction.UP);
   }

   // Extracted out vertexConsumer.vertex to add UV's
   private void renderFace(TemporaryHoleBlockEntity blockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, float x0, float x1, float z0, float z1, float y0, float y1, float y2, float y3, Direction direction) {
      if (!shouldRenderFace(blockEntity, direction)) {
         addVertex(vertexConsumer, mat, x1, y1, z1, y1 - 0.5f);
         addVertex(vertexConsumer, mat, x2, y2, z1, y2 - 0.5f);
         addVertex(vertexConsumer, mat, x2, y3, z2, y3 - 0.5f);
         addVertex(vertexConsumer, mat, x1, y4, z2, y4 - 0.5f);
      }
   }

   // Extracted method (vertexConsumer)
   private void addVertex(VertexConsumer vertexConsumer, Matrix4f mat, float x, float y, float z, float p) {
        vertexConsumer.vertex(mat, x, y, z);
        vertexConsumer.uv(0.0f, p);
        vertexConsumer.endVertex();
    }

   private static boolean shouldRenderFace(TemporaryHoleBlockEntity blockEntity, Direction direction) {
      BlockPos blockPos = blockEntity.getBlockPos();
      return Block.shouldRenderFace(blockEntity.getBlockState(), blockEntity.getLevel(), blockPos, direction, blockPos.relative(direction));
   }
}
