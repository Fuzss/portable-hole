/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package fuzs.portablehole.client.particle;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import fuzs.portablehole.PortableHole;
import fuzs.portablehole.config.ClientConfig;
import fuzs.portablehole.core.particles.SparkleParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class SparkleParticle extends SingleQuadParticle {
    /**
     * Custom blend function for supporting proper translucency.
     *
     * @see RenderPipelines#TRANSLUCENT_PARTICLE
     */
    public static final RenderPipeline SPARKLE_PARTICLE_RENDER_PIPELINE = RenderPipeline.builder(RenderPipelines.PARTICLE_SNIPPET)
            .withLocation(PortableHole.id("pipeline/sparkle_particle"))
            .withBlend(BlendFunction.LIGHTNING)
            .build();
    public static final SingleQuadParticle.Layer SPARKLE_PARTICLE_LAYER = new SingleQuadParticle.Layer(true,
            TextureAtlas.LOCATION_PARTICLES,
            SPARKLE_PARTICLE_RENDER_PIPELINE);

    private final boolean corrupt;
    private final boolean fake;
    private final boolean slowdown = true;
    private final SpriteSet sprites;

    public SparkleParticle(ClientLevel clientLevel, double x, double y, double z, float size, float red, float green, float blue, int m, boolean fake, boolean noClip, boolean corrupt, SpriteSet sprites) {
        super(clientLevel, x, y, z, 0.0, 0.0, 0.0, sprites.first());
        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
        this.alpha = 0.75F;
        this.gravity = 0;
        this.xd = this.yd = this.zd = 0;
        this.quadSize = (this.random.nextFloat() * 0.5F + 0.5F) * 0.2F * size;
        this.lifetime = 3 * m;
        this.setSize(0.01F, 0.01F);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.fake = fake;
        this.corrupt = corrupt;
        this.hasPhysics = !fake && !noClip;
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public float getQuadSize(float partialTicks) {
        return this.quadSize * (this.lifetime - this.age + 1) / (float) this.lifetime;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(this.sprites);
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        this.yd -= 0.04D * this.gravity;
        if (this.hasPhysics && !this.fake) {
            this.wiggleAround(this.x, (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.z);
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
            this.remove();
        }
    }

    @Override
    protected Layer getLayer() {
        if (PortableHole.CONFIG.get(ClientConfig.class).opaqueSparkleParticles) {
            return Layer.OPAQUE;
        } else {
            return SPARKLE_PARTICLE_LAYER;
        }
    }

    public void setGravity(float value) {
        this.gravity = value;
    }

    // [VanillaCopy] Entity.moveTowardClosestSpace with tweaks
    private void wiggleAround(double x, double y, double z) {
        BlockPos blockpos = BlockPos.containing(x, y, z);
        Vec3 Vector3d = new Vec3(x - (double) blockpos.getX(),
                y - (double) blockpos.getY(),
                z - (double) blockpos.getZ());
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        Direction direction = Direction.UP;
        double d0 = Double.MAX_VALUE;

        for (Direction direction1 : new Direction[]{
                Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP
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

    public static class Factory implements ParticleProvider<SparkleParticleOptions> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SparkleParticleOptions data, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource randomSource) {
            return new SparkleParticle(level,
                    x,
                    y,
                    z,
                    data.size,
                    data.r,
                    data.g,
                    data.b,
                    data.m,
                    data.fake,
                    data.noClip,
                    data.corrupt,
                    this.sprites);
        }
    }
}
