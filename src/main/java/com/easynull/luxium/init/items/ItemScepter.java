package com.easynull.luxium.init.items;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IMagic;
import com.easynull.luxium.client.utils.TooltipUtil;
import com.easynull.luxium.init.ModCreativeTab;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemScepter extends Item implements IMagic {
    final double maxLight;
    final double maxDark;

    public ItemScepter(double maxLight, double maxDark) {
        super(new Properties().stacksTo(1).tab(ModCreativeTab.tab));
        this.maxLight = maxLight;
        this.maxDark = maxDark;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag fl) {
        TooltipUtil.tooltipEnergy(stack, list);
        super.appendHoverText(stack, world, list, fl);
    }
    @Override
    public double getMaxEnergy(EnergyType type) {
        return switch (type) {
            case lux -> maxLight;
            case tenebris -> maxDark;
        };
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();
        setEnergy(tag, EnergyType.tenebris, 8.56);
        setEnergy(tag, EnergyType.lux, 10.99);
        return super.use(world, player, hand);
    }
}
