package fuzs.portablehole.data;

import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(PackOutput packOutput, String modId, ExistingFileHelper fileHelper) {
        super(packOutput, modId, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.builtInBlock(ModRegistry.TEMPORARY_HOLE_BLOCK.get(), Blocks.OBSIDIAN);
        this.basicItem(ModRegistry.PORTABLE_HOLE_ITEM.get());
    }
}
