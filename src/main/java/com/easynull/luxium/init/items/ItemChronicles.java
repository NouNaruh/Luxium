package com.easynull.luxium.init.items;

import com.easynull.luxium.api.chronicles.ScreenChronicles;
import com.easynull.luxium.client.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemChronicles extends Item {
    public final Map<Integer, String> toms = new HashMap<>();
    public ItemChronicles() {
        super(new Properties());
        toms.put(1, "mastering");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player pPlayer, InteractionHand hand) {
        Utils.mc.setScreen(new ScreenChronicles());
        level.playSound(pPlayer, pPlayer.getOnPos(), SoundEvents.BOOK_PUT, SoundSource.AMBIENT, 1.0F, 1.0F);
        setTom(3,"balance");
        return super.use(level, pPlayer, hand);
    }
    public String getTom() {
        return toms.getOrDefault(toms.size(), toms.toString());
    }
    public void setTom(int number, String name) {
        toms.put(number, name);
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag fl) {
        tooltip.add(new TextComponent(getTom()));
        super.appendHoverText(pStack, pLevel, tooltip, fl);
    }
}
