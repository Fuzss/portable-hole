/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package fuzs.portablehole.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fuzs.portablehole.PortableHole;
import fuzs.portablehole.client.core.ClientAbstractions;
import fuzs.portablehole.config.ClientConfig;
import fuzs.portablehole.core.particles.SparkleParticleData;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;

public class FXSparkle extends TextureSheetParticle {
    public static final ParticleRenderType NORMAL_RENDER = new ParticleRenderType() {

        @Override
        public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {

            RenderSystem.enableDepthTest();
            // from https://github.com/VazkiiMods/Botania/pull/4525
            if (PortableHole.CONFIG.get(ClientConfig.class).opaqueSparkleParticles) {
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.setShader(GameRenderer::getParticleShader);
            } else {
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.7F);
            }

            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public String toString() {
            return "botania:sparkle";
        }
    };

    private final boolean corrupt;
    private final boolean fake;
    private final boolean slowdown = true;
    private final SpriteSet sprite;

    public FXSparkle(ClientLevel world, double x, double y, double z, float size, float red, float green, float blue, int m, boolean fake, boolean noClip, boolean corrupt, SpriteSet sprite) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
        this.alpha = 0.75F;
        this.gravity = 0;
        this.xd = this.yd = this.zd = 0;
        this.quadSize = (this.random.nextFloat() * 0.5F + 0.5F) * 0.2F * size;
        this.lifetime = 3 * m;
        setSize(0.01F, 0.01F);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.fake = fake;
        this.corrupt = corrupt;
        this.hasPhysics = !fake && !noClip;
        this.sprite = sprite;
        setSpriteFromAge(sprite);
    }

    @Override
    public float getQuadSize(float partialTicks) {
        return this.quadSize * (this.lifetime - this.age + 1) / (float) this.lifetime;
    }

    @Override
    public void tick() {
        setSpriteFromAge(this.sprite);
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            remove();
        }

        this.yd -= 0.04D * this.gravity;

        if (this.hasPhysics && !this.fake) {
            wiggleAround(this.x, (getBoundingBox().minY + getBoundingBox().maxY) / 2.0D, this.z);
        }

        this.move(this.xd, this.yd, this.zd);

        if (this.slowdown) {
            this.xd *= 0.908;
            this.yd *= 0.908;
            this.zd *= 0.908;

            if (this.onGround) {
                this.xd *= 0.7;
                this.zd *= 0.7;
            }
        }

        if (this.fake && this.age > 1) {
            remove();
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        AbstractTexture abstractTexture = minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_PARTICLES);
        ClientAbstractions.INSTANCE.setFilterSave(abstractTexture, true, false);
        super.render(buffer, camera, partialTicks);
        ClientAbstractions.INSTANCE.restoreLastFilter(abstractTexture);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return NORMAL_RENDER;
    }

    public void setGravity(float value) {
        this.gravity = value;
    }

    // [VanillaCopy] Entity.moveTowardClosestSpace with tweaks
    private void wiggleAround(double x, double y, double z) {
        BlockPos blockpos = BlockPos.containing(x, y, z);
        Vec3 Vector3d = new Vec3(x - (double) blockpos.getX(), y - (double) blockpos.getY(),
                z - (double) blockpos.getZ()
        );
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        Direction direction = Direction.UP;
        double d0 = Double.MAX_VALUE;

        for (Direction direction1 : new Direction[]{
                Direction.NORTH,
                Direction.SOUTH,
                Direction.WEST,
                Direction.EAST,
                Direction.UP
        }) {
            blockpos$mutable.set(blockpos).move(direction1);
            if (!this.level.getBlockState(blockpos$mutable).isCollisionShapeFullBlock(this.level, blockpos$mutable)) {
                double d1 = Vector3d.get(direction1.getAxis());
                double d2 = direction1.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 1.0D - d1 : d1;
                if (d2 < d0) {
                    d0 = d2;
                    direction = direction1;
                }
            }
        }

        // Botania - made multiplier and add both smaller
        float f = this.random.nextFloat() * 0.05F + 0.025F;
        float f1 = (float) direction.getAxisDirection().getStep();
        // Botania - Randomness in other axes as well
        float secondary = (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
        float secondary2 = (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
        if (direction.getAxis() == Direction.Axis.X) {
            this.xd = f1 * f;
            this.yd = secondary;
            this.zd = secondary2;
        } else if (direction.getAxis() == Direction.Axis.Y) {
            this.xd = secondary;
            this.yd = f1 * f;
            this.zd = secondary2;
        } else if (direction.getAxis() == Direction.Axis.Z) {
            this.xd = secondary;
            this.yd = secondary2;
            this.zd = f1 * f;
        }
    }

    // moved here from SparkleParticleType
    public static class Factory implements ParticleProvider<SparkleParticleData> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SparkleParticleData data, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FXSparkle(level, x, y, z, data.size, data.r, data.g, data.b, data.m, data.fake, data.noClip,
                    data.corrupt, this.sprite
            );
        }
    }
}
