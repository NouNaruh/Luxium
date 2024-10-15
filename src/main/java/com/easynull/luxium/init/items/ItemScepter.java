package com.easynull.luxium.init.items;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IMagic;
import com.easynull.luxium.init.ModCreativeTab;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
        super.appendHoverText(stack, world, list, fl);
        list.add(new TextComponent(EnergyType.LUX.name().toLowerCase() +" "+ getEnergy(stack, EnergyType.LUX) +"/"+ getMaxEnergy(stack, EnergyType.LUX)));
        if(getMaxEnergy(stack, EnergyType.TENEBRIS) > 0){
            list.add(new TextComponent(EnergyType.TENEBRIS.name().toLowerCase() +" "+ getEnergy(stack, EnergyType.TENEBRIS) +"/"+ getMaxEnergy(stack, EnergyType.TENEBRIS)).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public double getMaxEnergy(ItemStack stack, EnergyType type) {
        return switch (type) {
            case LUX -> maxLight;
            case TENEBRIS -> maxDark;
        };
    }
    public void setEnergy(ItemStack stack, EnergyType type, double amount) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putDouble("Energy_" + type.name().toLowerCase(), getEnergy(stack, type) + amount);
        if(getEnergy(stack, type) == getMaxEnergy(stack, type)){
            tag.putDouble("Energy_" + type.name().toLowerCase(), getMaxEnergy(stack, type));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        setEnergy(stack, EnergyType.TENEBRIS, 8);
        setEnergy(stack, EnergyType.LUX, 10);
        return super.use(world, player, hand);
    }
}
