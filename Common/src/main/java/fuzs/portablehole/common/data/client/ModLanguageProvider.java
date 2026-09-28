package fuzs.portablehole.common.data.client;

import fuzs.portablehole.common.init.ModRegistry;
import fuzs.portablehole.common.world.item.PortableHoleItem;
import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.add(ModRegistry.PORTABLE_HOLE_ITEM.value(), "Portable Hole");
        this.add(ModRegistry.TEMPORARY_HOLE_BLOCK.value(), "Temporary Hole");
        this.add(((PortableHoleItem) ModRegistry.PORTABLE_HOLE_ITEM.value()).getDescriptionComponent(),
                "Click on a block and see what happens!");
    }
}
