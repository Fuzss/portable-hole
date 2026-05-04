package fuzs.portablehole.fabric.client;

import fuzs.portablehole.common.PortableHole;
import fuzs.portablehole.common.client.PortableHoleClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class PortableHoleFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(PortableHole.MOD_ID, PortableHoleClient::new);
    }
}
