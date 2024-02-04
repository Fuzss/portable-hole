package fuzs.portablehole.client;

import fuzs.portablehole.PortableHole;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class PortableHoleFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(PortableHole.MOD_ID, PortableHoleClient::new);
    }
}
