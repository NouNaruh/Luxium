package com.easynull.luxium.api.chronicles;

import com.easynull.luxium.client.utils.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenChronicles extends Screen {
    public ScreenChronicles() {
        super(new TextComponent("chapters"));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(PoseStack ps, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(ps);
        double ticksUp = (Minecraft.getInstance().level.getGameTime() + pPartialTick) * 4;
        ticksUp = (ticksUp) % 360;
        ps.pushPose();
        RenderUtil.renderItemGui(Items.ANVIL.getDefaultInstance(),ps,0,10,10,10,10);
        ps.mulPose(Vector3f.ZP.rotationDegrees((float) ticksUp));
        ps.popPose();
        super.render(ps, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return false;
    }
    public void renderTexture(ResourceLocation loc, PoseStack ps, int xPos, int yPos,  float uOffset, float vOffset, int height, int width, int heightTex, int widthTex) {
        Minecraft.getInstance().textureManager.getTexture(loc);
        blit(ps, xPos, yPos, uOffset, vOffset, width, height, widthTex, heightTex);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
