package fuzs.portablehole.neoforge.init;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.init.ModRegistry;
import fuzs.portablehole.neoforge.world.level.block.NeoForgeTemporaryHoleBlock;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;

public class NeoForgeModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(PortableHole.MOD_ID);
    public static final Holder.Reference<Block> TEMPORARY_HOLE_BLOCK = REGISTRIES.registerBlock("temporary_hole",
            NeoForgeTemporaryHoleBlock::new,
            ModRegistry::temporaryHole);

    public static void bootstrap() {
        // NO-OP
    }
}
