package com.easynull.luxium.api.chronicles.pages;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.api.chronicles.Page;
import com.easynull.luxium.api.chronicles.ScreenChronicles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class PageImage extends Page {
    ResourceLocation image;
    public PageImage(String loc) {
        this.image = new ResourceLocation(LuxiumMod.ID, "textures/chronicles/images" + loc);
    }

    @Override
    public void render(ScreenChronicles gui, PoseStack ps, int pMouseX, int pMouseY, float pPartialTick) {
        gui.renderTexture(image,ps,1,1,1,1,1,1,1,1);
        super.render(gui, ps, pMouseX, pMouseY, pPartialTick);
    }
}
