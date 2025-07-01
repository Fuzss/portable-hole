package fuzs.portablehole.neoforge.client;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.client.PortableHoleClient;
import fuzs.portablehole.data.client.ModLanguageProvider;
import fuzs.portablehole.data.client.ModModelProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = PortableHole.MOD_ID, dist = Dist.CLIENT)
public class PortableHoleNeoForgeClient {

    public PortableHoleNeoForgeClient() {
        ClientModConstructor.construct(PortableHole.MOD_ID, PortableHoleClient::new);
        DataProviderHelper.registerDataProviders(PortableHole.MOD_ID, ModModelProvider::new, ModLanguageProvider::new);
    }
}
