package fuzs.portablehole.data.client;

import fuzs.portablehole.init.ModRegistry;
import fuzs.portablehole.world.item.PortableHoleItem;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.add(ModRegistry.PORTABLE_HOLE_ITEM.value(), "Portable Hole");
        builder.add(ModRegistry.TEMPORARY_HOLE_BLOCK.value(), "Temporary Hole");
        builder.add(((PortableHoleItem) ModRegistry.PORTABLE_HOLE_ITEM.value()).getDescriptionComponent(),
                "Click on a block and see what happens!");
    }
}
