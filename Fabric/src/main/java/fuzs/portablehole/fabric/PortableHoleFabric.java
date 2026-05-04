package fuzs.portablehole.fabric;

import fuzs.portablehole.common.PortableHole;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class PortableHoleFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(PortableHole.MOD_ID, PortableHole::new);
    }
}
