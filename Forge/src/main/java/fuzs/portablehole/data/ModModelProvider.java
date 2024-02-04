package fuzs.portablehole.data;

import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void registerStatesAndModels() {
        this.builtInBlock(ModRegistry.TEMPORARY_HOLE_BLOCK.get(), Blocks.OBSIDIAN);
        this.basicItem(ModRegistry.PORTABLE_HOLE_ITEM.get());
    }
}
