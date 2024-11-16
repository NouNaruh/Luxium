package com.easynull.luxium.init.items;

import com.easynull.luxium.api.chronicles.ScreenChronicles;
import com.easynull.luxium.init.ModCreativeTab;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemChronicles extends Item {
    public final List<TranslatableComponent> tomes = new ArrayList<>();

    public ItemChronicles() {
        super(new Properties().tab(ModCreativeTab.tab));
        addTomes("tootip.luxium.chronicles.tom1");
    }

    @OnlyIn(Dist.CLIENT)
    public List<TranslatableComponent> getTomes() {
        return tomes;
    }

    @OnlyIn(Dist.CLIENT)
    public void addTomes(String tome) {
        if(!tomes.contains(new TranslatableComponent(tome))) {
            tomes.add(new TranslatableComponent(tome));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player pPlayer, InteractionHand hand) {
        if (level.isClientSide) {
            Minecraft.getInstance().setScreen(new ScreenChronicles());
            level.playSound(pPlayer, pPlayer.getOnPos(), SoundEvents.BOOK_PUT, SoundSource.AMBIENT, 1.0F, 1.0F);
            addTomes("tootip.luxium.chronicles.tom2");
        }
        return super.use(level, pPlayer, hand);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag fl) {
        tooltip.addAll(getTomes());
    }
}
