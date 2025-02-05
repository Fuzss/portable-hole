package fuzs.portablehole.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import fuzs.portablehole.PortableHole;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

public final class ModRenderType extends RenderType {
    private static final Function<ResourceLocation, RenderType> SPARKLE_PARTICLE = Util.memoize((ResourceLocation resourceLocation) -> create(
            PortableHole.id("sparkle_particle").toString(),
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            false,
            CompositeState.builder()
                    .setShaderState(PARTICLE_SHADER)
                    .setTextureState(new TextureStateShard(resourceLocation, TriState.FALSE, false))
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setOutputState(PARTICLES_TARGET)
                    .setLightmapState(LIGHTMAP)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .createCompositeState(false)));

    private ModRenderType(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    public static RenderType sparkleParticle(ResourceLocation resourceLocation) {
        return SPARKLE_PARTICLE.apply(resourceLocation);
    }
}
