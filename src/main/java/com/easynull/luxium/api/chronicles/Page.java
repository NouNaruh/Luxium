package com.easynull.luxium.api.chronicles;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;

public abstract class Page {
    public Page(){
        Head.pages.add(this);
    }

    public void render(ScreenChronicles s, PoseStack ps, int pMouseX, int pMouseY, float pPartialTick) {
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return false;
    }
}
