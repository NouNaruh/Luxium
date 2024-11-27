package com.easynull.luxium.init;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.init.blocks.*;
import com.easynull.luxium.init.entities.DemonTenebrisArmbands;
import com.easynull.luxium.init.entities.SpiceMerchant;
import com.easynull.luxium.init.items.*;
import com.easynull.luxium.init.recipes.RecipePrism;
import com.easynull.luxium.init.tiles.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class ModInit {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LuxiumMod.ID);
    public static RegistryObject<Block> luxiumPulsar = BLOCKS.register("luxium_pulsar", BlockLuxiumPulsar::new);
    public static RegistryObject<Block> fillingPrism = BLOCKS.register("filling_prism", BlockFillingPrism::new);

    public static DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LuxiumMod.ID);
    public static RegistryObject<BlockEntityType<TileLuxiumPulsar>> pulsar = TILES.register("luxium_pulsar", () -> BlockEntityType.Builder.of(TileLuxiumPulsar::new, luxiumPulsar.get()).build(null));
    public static RegistryObject<BlockEntityType<TileFillingPrism>> prism = TILES.register("filling_prism", () -> BlockEntityType.Builder.of(TileFillingPrism::new, fillingPrism.get()).build(null));

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LuxiumMod.ID);
    //Development items
    public static RegistryObject<Item> developmentItem = ITEMS.register("development_item", Tester::new);
    //Base items
    public static RegistryObject<Item> chronicles = ITEMS.register("chronicles", ItemChronicles::new);
    public static RegistryObject<Item> charmingDust = ITEMS.register("charming_dust",() -> new Item(new Item.Properties().tab(ModCreativeTab.tab)));
    public static RegistryObject<Item> scepter1 = ITEMS.register("scepter_student",() -> new ItemScepter(50, 0));
    public static RegistryObject<Item> scepter2 = ITEMS.register("scepter_bright_lux",() -> new ItemScepter(150, 0));
    public static RegistryObject<Item> scepter3 = ITEMS.register("scepter_crimson_moon",() -> new ItemScepter(450, 300));
    public static RegistryObject<Item> scepter4 = ITEMS.register("scepter_apostol_balance",() -> new ItemScepter(1000, 1000));
    public static RegistryObject<Item> luxShard = ITEMS.register("luxium_shard",() -> new Item(new Item.Properties().tab(ModCreativeTab.tab)));
    public static RegistryObject<Item> luxiumPulsarI = ITEMS.register("luxium_pulsar",() -> new BlockItem(luxiumPulsar.get(),new Item.Properties().tab(ModCreativeTab.tab)));
    public static RegistryObject<Item> fillingPrismI = ITEMS.register("filling_prism",() -> new BlockItem(fillingPrism.get(),new Item.Properties().tab(ModCreativeTab.tab)));

    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LuxiumMod.ID);
    public static RegistryObject<EntityType<SpiceMerchant>> spiceMerchant = ENTITIES.register("spice_merchant", () -> EntityType.Builder.of(SpiceMerchant::new, MobCategory.CREATURE).sized(1.50F, 2.1F).build(new ResourceLocation(LuxiumMod.ID,"spice_merchant").toString()));
    public static RegistryObject<EntityType<DemonTenebrisArmbands>> demonArmbands = ENTITIES.register("demon_tenebris_armbands", () -> EntityType.Builder.of(DemonTenebrisArmbands::new, MobCategory.CREATURE).sized(0.9F, 1.4F).build(new ResourceLocation(LuxiumMod.ID,"demon_tenebris_armbands").toString()));

    public static DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LuxiumMod.ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE.key(), LuxiumMod.ID);
    public static RegistryObject<RecipeType<RecipePrism>> recipeTypePrism = RECIPE_TYPES.register(RecipePrism.Type.ID, () -> RecipePrism.Type.INSTANCE);
    public static RegistryObject<RecipeSerializer<RecipePrism>> recipeSerPrism = RECIPE_SERIALIZERS.register("filling_prism", () -> RecipePrism.Serializer.INSTANCE);

    public static void register(IEventBus bus){
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TILES.register(bus);
        ENTITIES.register(bus);
        RECIPE_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
    }
}
