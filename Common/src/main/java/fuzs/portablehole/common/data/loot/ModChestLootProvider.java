package fuzs.portablehole.common.data.loot;

import fuzs.portablehole.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v3.loot.AbstractLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class ModChestLootProvider extends AbstractLootSubProvider {

    public ModChestLootProvider(LootTableSubProvider.Context context) {
        super(context);
    }

    @Override
    public void generate() {
        this.output.accept(ModRegistry.STRONGHOLD_CORRIDOR_INJECT_LOOT_TABLE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(ModRegistry.PORTABLE_HOLE_ITEM.value()).setWeight(1))
                        .add(EmptyLootItem.emptyItem().setWeight(4))));
    }
}
