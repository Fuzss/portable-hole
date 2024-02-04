package fuzs.portablehole.data.client;

import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.level.block.Blocks;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators builder) {
        builder.blockEntityModels(ModRegistry.TEMPORARY_HOLE_BLOCK.value(), Blocks.OBSIDIAN)
                .create(ModRegistry.TEMPORARY_HOLE_BLOCK.value());
    }

    @Override
    public void addItemModels(ItemModelGenerators builder) {
        builder.generateFlatItem(ModRegistry.PORTABLE_HOLE_ITEM.value(), ModelTemplates.FLAT_ITEM);
    }
}
