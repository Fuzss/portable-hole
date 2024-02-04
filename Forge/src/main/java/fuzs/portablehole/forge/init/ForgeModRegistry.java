package fuzs.portablehole.forge.init;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.forge.world.level.block.ForgeTemporaryHoleBlock;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ForgeModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.from(PortableHole.MOD_ID);
    public static final Holder.Reference<Block> TEMPORARY_HOLE_BLOCK = REGISTRY.registerBlock("temporary_hole",
            () -> new ForgeTemporaryHoleBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .noCollission()
                    .lightLevel((p_50854_) -> {
                        return 15;
                    })
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .pushReaction(PushReaction.BLOCK))
    );

    public static void touch() {

    }
}
