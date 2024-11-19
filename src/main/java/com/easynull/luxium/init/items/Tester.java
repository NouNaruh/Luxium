package com.easynull.luxium.init.items;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.init.tiles.TileFillingPrism;
import com.easynull.luxium.init.tiles.TileLuxiumCrystal;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class Tester extends Item {
    public Tester() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player pl = pContext.getPlayer();
        BlockPos pos = pContext.getClickedPos();
        Level world = pContext.getLevel();
        BlockEntity l = world.getBlockEntity(pos);
        if(l instanceof TileLuxiumCrystal crystal) {
            pl.sendMessage(new TextComponent(crystal.getLux() + ""), Util.NIL_UUID);
        }
        if(l instanceof TileFillingPrism prism) {
            for (EnergyType type : EnergyType.values()) {
                pl.sendMessage(new TextComponent(prism.getEnergy(type) + type.name()), Util.NIL_UUID);
            }
            pl.sendMessage(new TextComponent(prism.getEnergyMax() + ""), Util.NIL_UUID);
        }
        return super.useOn(pContext);
    }
}
