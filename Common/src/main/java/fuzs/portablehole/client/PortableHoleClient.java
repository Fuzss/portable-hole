package fuzs.portablehole.client;

import fuzs.portablehole.client.particle.FXSparkle;
import fuzs.portablehole.client.renderer.blockentity.TemporaryHoleRenderer;
import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.BlockEntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.ParticleProvidersContext;

public class PortableHoleClient implements ClientModConstructor {

    @Override
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModRegistry.TEMPORARY_HOLE_BLOCK_ENTITY_TYPE.value(),
                TemporaryHoleRenderer::new
        );
    }

    @Override
    public void onRegisterParticleProviders(ParticleProvidersContext context) {
        context.registerParticleProvider(ModRegistry.SPARKLE_PARTICLE_TYPE.value(), FXSparkle.Factory::new);
    }
}
