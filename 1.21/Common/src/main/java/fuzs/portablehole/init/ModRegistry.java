package fuzs.portablehole.init;

import fuzs.portablehole.PortableHole;
import fuzs.portablehole.core.particles.SparkleParticleData;
import fuzs.portablehole.core.particles.SparkleParticleType;
import fuzs.portablehole.world.item.PortableHoleItem;
import fuzs.portablehole.world.level.block.TemporaryHoleBlock;
import fuzs.portablehole.world.level.block.entity.TemporaryHoleBlockEntity;
import fuzs.puzzleslib.api.core.v1.ModLoader;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.BoundTagFactory;
import net.minecraft.core.Holder;
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

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.from(PortableHole.MOD_ID);
    public static final Holder.Reference<Block> TEMPORARY_HOLE_BLOCK = REGISTRY.whenOnFabricLike()
            .registerBlock("temporary_hole",
                    () -> new TemporaryHoleBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BLACK)
                            .noCollission()
                            .lightLevel((p_50854_) -> {
                                return 15;
                            })
                            .strength(-1.0F, 3600000.0F)
                            .noLootTable()
                            .pushReaction(PushReaction.BLOCK))
            );
    public static final Holder.Reference<BlockEntityType<TemporaryHoleBlockEntity>> TEMPORARY_HOLE_BLOCK_ENTITY_TYPE = REGISTRY.registerBlockEntityType(
            "temporary_hole",
            () -> BlockEntityType.Builder.of(TemporaryHoleBlockEntity::new, TEMPORARY_HOLE_BLOCK.value())
    );
    public static final Holder.Reference<Item> PORTABLE_HOLE_ITEM = REGISTRY.registerItem("portable_hole",
            () -> new PortableHoleItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
    );
    public static final Holder.Reference<ParticleType<SparkleParticleData>> SPARK_PARTICLE_TYPE = REGISTRY.register(
            Registries.PARTICLE_TYPE,
            "sparkle",
            () -> new SparkleParticleType()
    );

    public static final ResourceLocation STRONGHOLD_CORRIDOR_INJECT_LOOT_TABLE = REGISTRY.makeKey(
            "chests/inject/stronghold_corridor");

    static final BoundTagFactory TAGS = BoundTagFactory.make(PortableHole.MOD_ID);
    public static final TagKey<Block> PORTABLE_HOLE_IMMUNE_TAG = TAGS.registerBlockTag("portable_hole_immune");

    public static void touch() {

    }
}
