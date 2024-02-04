package fuzs.portablehole.data;

import fuzs.portablehole.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLootProvider;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModChestLootProvider extends AbstractLootProvider.Simple {

    public ModChestLootProvider(GatherDataEvent evt, String modId) {
        super(LootContextParamSets.CHEST, evt, modId);
    }

    @Override
    public void generate() {
        this.add(ModRegistry.STRONGHOLD_CORRIDOR_INJECT_LOOT_TABLE, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModRegistry.PORTABLE_HOLE_ITEM.get()).setWeight(1)).add(EmptyLootItem.emptyItem().setWeight(4))));
    }
}
