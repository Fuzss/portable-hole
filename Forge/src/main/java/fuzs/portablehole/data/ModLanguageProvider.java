package fuzs.portablehole.data;

import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTranslations() {
        this.add(ModRegistry.PORTABLE_HOLE_ITEM.get(), "Portable Hole");
        this.addAdditional(ModRegistry.PORTABLE_HOLE_ITEM.get(), "description", "Click on a block and see what happens!");
    }
}
