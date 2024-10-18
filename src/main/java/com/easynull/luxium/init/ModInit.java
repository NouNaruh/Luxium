package com.easynull.luxium.init;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.init.blocks.*;
import com.easynull.luxium.init.items.*;
import com.easynull.luxium.init.tiles.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModInit {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LuxiumMod.ID);
    public static RegistryObject<Block> clepsydraLuxium = BLOCKS.register("clepsydra_luxium", BlockClepsydraLight::new);
    public static RegistryObject<Block> luminaryMembraniumTotem = BLOCKS.register("luminary_membranium_totem",() -> new BlockMembraniumTotem(EnergyType.LUX));
    public static RegistryObject<Block> umbralMembraniumTotem = BLOCKS.register("umbral_membranium_totem",() -> new BlockMembraniumTotem(EnergyType.TENEBRIS));
    public static RegistryObject<Block> energyRelay = BLOCKS.register("energy_relay", BlockEnergyRelay::new);

    public static DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LuxiumMod.ID);
    public static RegistryObject<BlockEntityType<TileClepsydraLight>> clepsydra = TILES.register("clepsydra", () -> BlockEntityType.Builder.of(TileClepsydraLight::new, clepsydraLuxium.get()).build(null));
    public static RegistryObject<BlockEntityType<TileMembraniumTotem>> membraniumTotem = TILES.register("membranium_totem", () -> BlockEntityType.Builder.of(TileMembraniumTotem::new, luminaryMembraniumTotem.get(), umbralMembraniumTotem.get()).build(null));
    public static RegistryObject<BlockEntityType<TileEnergyRelay>> relay = TILES.register("relay", () -> BlockEntityType.Builder.of(TileEnergyRelay::new, energyRelay.get()).build(null));

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LuxiumMod.ID);
    public static RegistryObject<Item> chronicles = ITEMS.register("chronicles", ItemChronicles::new);
    public static RegistryObject<Item> scepter1 = ITEMS.register("scepter_student",() -> new ItemScepter(50, 0));
    public static RegistryObject<Item> scepter2 = ITEMS.register("scepter_bright_lux",() -> new ItemScepter(150, 0));
    public static RegistryObject<Item> scepter3 = ITEMS.register("scepter_crimson_moon",() -> new ItemScepter(450, 300));
    public static RegistryObject<Item> scepter4 = ITEMS.register("scepter_apostol_balance",() -> new ItemScepter(1000, 1000));
    public static RegistryObject<Item> clepsydraLuxiumI = ITEMS.register("clepsydra_luxium",() -> new BlockItem(clepsydraLuxium.get(),new Item.Properties().tab(ModCreativeTab.tab)));
    public static RegistryObject<Item> luminaryMembraniumTotemI = ITEMS.register("luminary_membranium_totem",() -> new BlockItem(luminaryMembraniumTotem.get(),new Item.Properties().tab(ModCreativeTab.tab)));
    public static RegistryObject<Item> umbralMembraniumTotemI = ITEMS.register("umbral_membranium_totem",() -> new BlockItem(umbralMembraniumTotem.get(),new Item.Properties().tab(ModCreativeTab.tab)));
    public static RegistryObject<Item> energyRelayI = ITEMS.register("energy_relay",() -> new BlockItem(energyRelay.get(),new Item.Properties().tab(ModCreativeTab.tab)));


    public static void register(IEventBus bus){
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TILES.register(bus);
    }
}
