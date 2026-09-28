package fuzs.portablehole.neoforge;

import fuzs.portablehole.common.PortableHole;
import fuzs.portablehole.common.data.tags.ModBlockTagsProvider;
import fuzs.portablehole.common.data.loot.ModChestLootProvider;
import fuzs.portablehole.neoforge.init.NeoForgeModRegistry;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v3.core.DataProviderBuilder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.fml.common.Mod;

@Mod(PortableHole.MOD_ID)
public class PortableHoleNeoForge {

    public PortableHoleNeoForge() {
        NeoForgeModRegistry.bootstrap();
        ModConstructor.construct(PortableHole.MOD_ID, PortableHole::new);
        DataProviderBuilder.of(PortableHole.MOD_ID)
                .addProvider(ModBlockTagsProvider::new)
                .addLootProvider(ModChestLootProvider::new, LootContextParamSets.CHEST);
    }
}
