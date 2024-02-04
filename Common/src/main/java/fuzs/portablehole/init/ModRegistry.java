package fuzs.portablehole.init;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.core.particles.SparkleParticleData;
import fuzs.portablehole.core.particles.SparkleParticleType;
import fuzs.portablehole.world.item.PortableHoleItem;
import fuzs.portablehole.world.level.block.TemporaryHoleBlock;
import fuzs.portablehole.world.level.block.entity.TemporaryHoleBlockEntity;
import fuzs.puzzleslib.api.core.v1.ModLoader;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(PortableHole.MOD_ID);
    public static final RegistryReference<Block> TEMPORARY_HOLE_BLOCK = REGISTRY.whenNotOn(ModLoader.FORGE).registerBlock("temporary_hole", () -> new TemporaryHoleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).noCollission().lightLevel((p_50854_) -> {
        return 15;
    }).strength(-1.0F, 3600000.0F).noLootTable().pushReaction(PushReaction.BLOCK)));
    public static final RegistryReference<Item> PORTABLE_HOLE_ITEM = REGISTRY.registerItem("portable_hole", () -> new PortableHoleItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryReference<BlockEntityType<TemporaryHoleBlockEntity>> TEMPORARY_HOLE_BLOCK_ENTITY_TYPE = REGISTRY.registerBlockEntityType("temporary_hole", () -> BlockEntityType.Builder.of(TemporaryHoleBlockEntity::new, TEMPORARY_HOLE_BLOCK.get()));
    public static final RegistryReference<ParticleType<SparkleParticleData>> SPARK_PARTICLE_TYPE = REGISTRY.register(Registries.PARTICLE_TYPE, "sparkle", () -> new SparkleParticleType());
    public static final TagKey<Block> PORTABLE_HOLE_IMMUNE_TAG = REGISTRY.registerBlockTag("portable_hole_immune");

    public static final ResourceLocation STRONGHOLD_CORRIDOR_INJECT_LOOT_TABLE = new ResourceLocation(PortableHole.MOD_ID, "inject/" + BuiltInLootTables.STRONGHOLD_CORRIDOR.getPath());

    public static void touch() {

    }
}
