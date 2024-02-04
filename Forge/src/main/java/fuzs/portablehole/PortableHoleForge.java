package fuzs.portablehole;

import fuzs.portablehole.data.ModBlockTagsProvider;
import fuzs.portablehole.data.ModChestLootProvider;
import fuzs.portablehole.data.ModLanguageProvider;
import fuzs.portablehole.data.ModModelProvider;
import fuzs.portablehole.init.ModRegistryForge;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(PortableHole.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PortableHoleForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(PortableHole.MOD_ID, PortableHole::new);
        ModRegistryForge.touch();
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        evt.getGenerator().addProvider(new ModModelProvider(evt, PortableHole.MOD_ID));
        evt.getGenerator().addProvider(new ModBlockTagsProvider(evt, PortableHole.MOD_ID));
        evt.getGenerator().addProvider(new ModLanguageProvider(evt, PortableHole.MOD_ID));
        evt.getGenerator().addProvider(new ModChestLootProvider(evt, PortableHole.MOD_ID));
    }
}
