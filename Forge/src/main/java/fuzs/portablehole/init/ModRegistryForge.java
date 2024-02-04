package fuzs.portablehole.init;

import fuzs.portablehole.world.level.block.TemporaryHoleForgeBlock;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static fuzs.portablehole.init.ModRegistry.REGISTRY;

public class ModRegistryForge {
    public static final RegistryReference<Block> TEMPORARY_HOLE_BLOCK = REGISTRY.registerBlock("temporary_hole", () -> new TemporaryHoleForgeBlock(BlockBehaviour.Properties.copy(Blocks.END_PORTAL)));

    public static void touch() {

    }
}
