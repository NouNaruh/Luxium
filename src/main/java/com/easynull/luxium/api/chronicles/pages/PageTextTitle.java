package com.easynull.luxium.api.chronicles.pages;

import com.easynull.luxium.api.chronicles.Page;
import com.easynull.luxium.api.chronicles.ScreenChronicles;
import com.easynull.luxium.client.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.world.item.ItemStack;

public class PageTextTitle extends Page {
    ItemStack stack;
    public PageTextTitle(ItemStack stack, String titleText, String text) {
       this.stack = stack;
    }

    @Override
    public void render(ScreenChronicles s, PoseStack ps, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(s, ps, pMouseX, pMouseY, pPartialTick);
        double ticksUp = (Utils.ticksInGame + pPartialTick) * 4;
        ticksUp = (ticksUp) % 360;
        ps.pushPose();
        ps.mulPose(Vector3f.ZP.rotationDegrees((float) ticksUp * 360.0F));
        Utils.renderItemGui(stack,0,0,10,10,10);
        ps.popPose();

    }
}
