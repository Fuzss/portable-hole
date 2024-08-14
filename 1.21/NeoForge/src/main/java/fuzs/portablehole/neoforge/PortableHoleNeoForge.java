package fuzs.portablehole.neoforge;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.data.ModBlockTagProvider;
import fuzs.portablehole.data.ModChestLootProvider;
import fuzs.portablehole.data.client.ModLanguageProvider;
import fuzs.portablehole.data.client.ModModelProvider;
import fuzs.portablehole.neoforge.init.NeoForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(PortableHole.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PortableHoleNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        NeoForgeModRegistry.touch();
        ModConstructor.construct(PortableHole.MOD_ID, PortableHole::new);
        DataProviderHelper.registerDataProviders(PortableHole.MOD_ID,
                ModModelProvider::new,
                ModBlockTagProvider::new,
                ModLanguageProvider::new,
                ModChestLootProvider::new
        );
    }
}
