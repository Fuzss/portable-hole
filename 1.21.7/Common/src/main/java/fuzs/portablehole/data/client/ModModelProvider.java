package fuzs.portablehole.data.client;

import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.world.level.block.Blocks;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createParticleOnlyBlock(ModRegistry.TEMPORARY_HOLE_BLOCK.value(), Blocks.OBSIDIAN);
    }

    @Override
    public void addItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModRegistry.PORTABLE_HOLE_ITEM.value(), ModelTemplates.FLAT_ITEM);
    }
}
