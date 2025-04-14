package fuzs.portablehole.client;

import fuzs.portablehole.client.particle.FXSparkle;
import fuzs.portablehole.client.renderer.ModRenderType;
import fuzs.portablehole.client.renderer.blockentity.TemporaryHoleRenderer;
import fuzs.portablehole.init.ModRegistry;
import fuzs.portablehole.world.item.PortableHoleItem;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.BlockEntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.ParticleProvidersContext;
import fuzs.puzzleslib.api.client.core.v1.context.RenderPipelinesContext;
import fuzs.puzzleslib.api.client.gui.v2.tooltip.ItemTooltipRegistry;

public class PortableHoleClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ItemTooltipRegistry.registerItemTooltip(PortableHoleItem.class, PortableHoleItem::getDescriptionComponent);
    }

    @Override
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModRegistry.TEMPORARY_HOLE_BLOCK_ENTITY_TYPE.value(),
                TemporaryHoleRenderer::new);
    }

    @Override
    public void onRegisterParticleProviders(ParticleProvidersContext context) {
        context.registerParticleProvider(ModRegistry.SPARKLE_PARTICLE_TYPE.value(), FXSparkle.Factory::new);
    }

    @Override
    public void onRegisterRenderPipelines(RenderPipelinesContext context) {
        context.registerRenderPipeline(ModRenderType.SPARKLE_PARTICLE_RENDER_PIPELINE);
    }
}
