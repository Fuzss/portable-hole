package fuzs.portablehole.data;

import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void addTranslations() {
        this.add(ModRegistry.PORTABLE_HOLE_ITEM.get(), "Portable Hole");
        this.addAdditional(ModRegistry.PORTABLE_HOLE_ITEM.get(), "description", "Click on a block and see what happens!");
    }
}
