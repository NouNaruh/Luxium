package com.easynull.luxium.init.items;

import com.easynull.luxium.init.tiles.TileLuxiumCrystal;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class Tester extends Item {
    public Tester() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player pl = pContext.getPlayer();
        BlockPos pos = pContext.getClickedPos();
        Level world = pContext.getLevel();
        TileLuxiumCrystal crystal = (TileLuxiumCrystal)world.getBlockEntity(pos);
        pl.sendMessage(new TextComponent(crystal.getLux() + ""), Util.NIL_UUID);
        return super.useOn(pContext);
    }
}
