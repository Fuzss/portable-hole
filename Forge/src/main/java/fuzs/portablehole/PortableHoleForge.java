package fuzs.portablehole;

import fuzs.portablehole.data.ModBlockTagsProvider;
import fuzs.portablehole.data.ModChestLootProvider;
import fuzs.portablehole.data.ModLanguageProvider;
import fuzs.portablehole.data.ModModelProvider;
import fuzs.portablehole.init.ModRegistryForge;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

import java.util.concurrent.CompletableFuture;

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
        final DataGenerator dataGenerator = evt.getGenerator();
        final PackOutput packOutput = dataGenerator.getPackOutput();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = evt.getLookupProvider();
        final ExistingFileHelper fileHelper = evt.getExistingFileHelper();
        dataGenerator.addProvider(true, new ModModelProvider(packOutput, PortableHole.MOD_ID, fileHelper));
        dataGenerator.addProvider(true, new ModBlockTagsProvider(packOutput, lookupProvider, PortableHole.MOD_ID, fileHelper));
        dataGenerator.addProvider(true, new ModLanguageProvider(packOutput, PortableHole.MOD_ID));
        dataGenerator.addProvider(true, new ModChestLootProvider(packOutput));
    }
}
