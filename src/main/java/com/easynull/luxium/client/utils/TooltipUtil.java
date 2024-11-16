package com.easynull.luxium.client.utils;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IEnergyItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TooltipUtil {
    public static void tooltipEnergy(ItemStack st, List<Component> list){
        CompoundTag tag = st.getOrCreateTag();
        if(st.getItem() instanceof IEnergyItem stack) {
            if (stack.getMaxEnergy(EnergyType.tenebris) > 0) {
                list.add(new TranslatableComponent("tootip.luxium.energy.lux", stack.getEnergy(tag, EnergyType.lux)).withStyle(ChatFormatting.WHITE).append((new TranslatableComponent("tootip.luxium.energy.tenebris", stack.getEnergy(tag, EnergyType.tenebris)).withStyle(ChatFormatting.DARK_GRAY))));
            } else {
                list.add(new TranslatableComponent("tootip.luxium.energy.lux", stack.getEnergy(tag, EnergyType.lux)).withStyle(ChatFormatting.WHITE));
            }
        }
    }
    public static void tooltipEnergy(List<Component> list, double lux, double tenebris) {
        if (tenebris > 0) {
            list.add(new TranslatableComponent("tootip.luxium.energy.storage"));
            list.add(new TranslatableComponent("tootip.luxium.energy.lux", lux).withStyle(ChatFormatting.WHITE).append((new TranslatableComponent("tootip.luxium.energy.tenebris", tenebris).withStyle(ChatFormatting.DARK_GRAY))));
        } else {
            list.add(new TranslatableComponent("tootip.luxium.energy.lux", lux).withStyle(ChatFormatting.WHITE));
        }
    }
}
