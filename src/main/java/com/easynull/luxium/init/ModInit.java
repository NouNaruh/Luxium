package com.easynull.luxium.init;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.init.blocks.*;
import com.easynull.luxium.init.entities.SpiceMerchant;
import com.easynull.luxium.init.items.*;
import com.easynull.luxium.init.tiles.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

import java.awt.*;

public class ModInit {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LuxiumMod.ID);
    public static RegistryObject<Block> luxiumCrystal = BLOCKS.register("luxium_crystal", BlockLuxiumCrystal::new);
    public static RegistryObject<Block> fillingPrism = BLOCKS.register("filling_prism", BlockFillingPrism::new);

    public static DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LuxiumMod.ID);
    public static RegistryObject<BlockEntityType<TileLuxiumCrystal>> crystal = TILES.register("luxium_crystal", () -> BlockEntityType.Builder.of(TileLuxiumCrystal::new, luxiumCrystal.get()).build(null));
    public static RegistryObject<BlockEntityType<TileFillingPrism>> prism = TILES.register("filling_prism", () -> BlockEntityType.Builder.of(TileFillingPrism::new, fillingPrism.get()).build(null));

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LuxiumMod.ID);
    //Development item
    public static RegistryObject<Item> developmentItem = ITEMS.register("development_item", Tester::new);
    //Base item
    public static RegistryObject<Item> chronicles = ITEMS.register("chronicles", ItemChronicles::new);
    public static RegistryObject<Item> scepter1 = ITEMS.register("scepter_student",() -> new ItemScepter(50, 0));
    public static RegistryObject<Item> scepter2 = ITEMS.register("scepter_bright_lux",() -> new ItemScepter(150, 0));
    public static RegistryObject<Item> scepter3 = ITEMS.register("scepter_crimson_moon",() -> new ItemScepter(450, 300));
    public static RegistryObject<Item> scepter4 = ITEMS.register("scepter_apostol_balance",() -> new ItemScepter(1000, 1000));
    public static RegistryObject<Item> luxiumCrystalI = ITEMS.register("luxium_crystal",() -> new BlockItem(luxiumCrystal.get(),new Item.Properties().tab(ModCreativeTab.tab)));
    public static RegistryObject<Item> fillingPrismI = ITEMS.register("filling_prism",() -> new BlockItem(fillingPrism.get(),new Item.Properties().tab(ModCreativeTab.tab)));

    //Spawn eggs

    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LuxiumMod.ID);
    public static RegistryObject<EntityType<SpiceMerchant>> spiceMerchant = ENTITIES.register("spice_merchant", () -> EntityType.Builder.of(SpiceMerchant::new, MobCategory.CREATURE).sized(1.50F, 2.1F).build(new ResourceLocation(LuxiumMod.ID,"spice_merchant").toString()));

    public static DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LuxiumMod.ID);

    public static void register(IEventBus bus){
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TILES.register(bus);
        ENTITIES.register(bus);
        RECIPES.register(bus);
    }
}
