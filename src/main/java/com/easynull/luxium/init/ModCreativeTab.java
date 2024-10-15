package com.easynull.luxium.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {
    public static CreativeModeTab tab = new CreativeModeTab("tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModInit.scepter1.get(), 1);
        }
    };
}
