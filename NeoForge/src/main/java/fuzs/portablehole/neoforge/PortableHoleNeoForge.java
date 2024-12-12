package fuzs.portablehole.neoforge;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.data.ModBlockTagProvider;
import fuzs.portablehole.data.ModChestLootProvider;
import fuzs.portablehole.neoforge.init.NeoForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(PortableHole.MOD_ID)
public class PortableHoleNeoForge {

    public PortableHoleNeoForge() {
        NeoForgeModRegistry.bootstrap();
        ModConstructor.construct(PortableHole.MOD_ID, PortableHole::new);
        DataProviderHelper.registerDataProviders(PortableHole.MOD_ID, ModBlockTagProvider::new,
                ModChestLootProvider::new
        );
    }
}
