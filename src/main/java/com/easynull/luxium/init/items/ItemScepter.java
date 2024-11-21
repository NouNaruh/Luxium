package com.easynull.luxium.init.items;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IEnergyItem;
import com.easynull.luxium.client.utils.TooltipUtil;
import com.easynull.luxium.init.ModCreativeTab;
import com.easynull.luxium.init.tiles.TileFillingPrism;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemScepter extends Item implements IEnergyItem {
    final double maxLux, maxTen;

    public ItemScepter(double maxLux, double maxTen) {
        super(new Properties().stacksTo(1).tab(ModCreativeTab.tab));
        this.maxLux = maxLux;
        this.maxTen = maxTen;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag fl) {
        TooltipUtil.tooltipEnergy(stack, list);
        super.appendHoverText(stack, world, list, fl);
    }
    @Override
    public double getMaxEnergy(EnergyType type) {
        return switch (type) {
            case lux -> maxLux;
            case tenebris -> maxTen;
        };
    }
    @Override
    public InteractionResult useOn(UseOnContext uon) {
        activateCraftPrism(uon.getLevel(), uon.getClickedPos());
        return super.useOn(uon);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();
        return super.use(world, player, hand);
    }
    public void activateCraftPrism(Level lv, BlockPos pos) {
        TileFillingPrism prism = (TileFillingPrism) lv.getBlockEntity(pos);
        if (prism != null) {
            prism.canCraft = true;
        }
    }
}
