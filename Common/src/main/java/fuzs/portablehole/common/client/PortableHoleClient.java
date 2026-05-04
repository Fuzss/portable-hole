package fuzs.portablehole.common.client;

import fuzs.portablehole.common.client.particle.SparkleParticle;
import fuzs.portablehole.common.client.renderer.blockentity.TemporaryHoleRenderer;
import fuzs.portablehole.common.init.ModRegistry;
import fuzs.portablehole.common.world.item.PortableHoleItem;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.BlockEntityRenderersContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.ParticleProvidersContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.RenderPipelinesContext;
import fuzs.puzzleslib.common.api.client.gui.v2.tooltip.ItemTooltipRegistry;

public class PortableHoleClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ItemTooltipRegistry.ITEM.registerItemTooltip(PortableHoleItem.class, PortableHoleItem::getDescriptionComponent);
    }

    @Override
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModRegistry.TEMPORARY_HOLE_BLOCK_ENTITY_TYPE.value(),
                TemporaryHoleRenderer::new);
    }

    @Override
    public void onRegisterParticleProviders(ParticleProvidersContext context) {
        context.registerParticleProvider(ModRegistry.SPARKLE_PARTICLE_TYPE.value(), SparkleParticle.Factory::new);
    }

    @Override
    public void onRegisterRenderPipelines(RenderPipelinesContext context) {
        context.registerRenderPipeline(SparkleParticle.SPARKLE_PARTICLE_RENDER_PIPELINE);
    }
}
