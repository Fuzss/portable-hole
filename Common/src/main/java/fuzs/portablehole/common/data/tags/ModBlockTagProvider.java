package fuzs.portablehole.common.data.tags;

import fuzs.portablehole.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

public class ModBlockTagProvider extends AbstractTagProvider<Block> {

    public ModBlockTagProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.SUBSTRATE_OVERWORLD).add(ModRegistry.TEMPORARY_HOLE_BLOCK.value());
        this.tag(BlockTags.SUPPORTS_CROPS).add(ModRegistry.TEMPORARY_HOLE_BLOCK.value());
        this.tag(ModRegistry.PORTABLE_HOLE_IMMUNE_BLOCK_TAG);
    }
}
