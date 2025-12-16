package fuzs.portablehole.init;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.core.particles.SparkleParticleOptions;
import fuzs.portablehole.world.item.PortableHoleItem;
import fuzs.portablehole.world.level.block.TemporaryHoleBlock;
import fuzs.portablehole.world.level.block.entity.TemporaryHoleBlockEntity;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(PortableHole.MOD_ID);
    public static final Holder.Reference<Block> TEMPORARY_HOLE_BLOCK = REGISTRIES.whenOnFabricLike()
            .registerBlock("temporary_hole", TemporaryHoleBlock::new, ModRegistry::temporaryHole);
    public static final Holder.Reference<BlockEntityType<TemporaryHoleBlockEntity>> TEMPORARY_HOLE_BLOCK_ENTITY_TYPE = REGISTRIES.registerBlockEntityType(
            "temporary_hole",
            TemporaryHoleBlockEntity::new,
            TEMPORARY_HOLE_BLOCK);
    public static final Holder.Reference<Item> PORTABLE_HOLE_ITEM = REGISTRIES.registerItem("portable_hole",
            PortableHoleItem::new,
            () -> {
                return new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON);
            });
    public static final Holder.Reference<ParticleType<SparkleParticleOptions>> SPARKLE_PARTICLE_TYPE = REGISTRIES.registerParticleType(
            "sparkle",
            false,
            (ParticleType<SparkleParticleOptions> particleType) -> {
                return SparkleParticleOptions.CODEC;
            },
            (ParticleType<SparkleParticleOptions> particleType) -> {
                return SparkleParticleOptions.STREAM_CODEC;
            });

    public static final ResourceKey<LootTable> STRONGHOLD_CORRIDOR_INJECT_LOOT_TABLE = REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/inject/stronghold_corridor");

    static final TagFactory TAGS = TagFactory.make(PortableHole.MOD_ID);
    public static final TagKey<Block> PORTABLE_HOLE_IMMUNE_BLOCK_TAG = TAGS.registerBlockTag("portable_hole_immune");

    public static void bootstrap() {
        // NO-OP
    }

    public static BlockBehaviour.Properties temporaryHole() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .noCollision()
                .lightLevel((BlockState blockState) -> {
                    return 15;
                })
                .strength(-1.0F, 3600000.0F)
                .noLootTable()
                .pushReaction(PushReaction.BLOCK);
    }
}
