package com.easynull.luxium.client.render.tile;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.client.render.LuxiumRender;
import com.easynull.luxium.client.utils.ClientTickUtil;
import com.easynull.luxium.init.tiles.TileLuxiumCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public class RenderLuxiumCrystal extends LuxiumRender<TileLuxiumCrystal> {
    public static final ModelResourceLocation CRYSTAL = new ModelResourceLocation(new ResourceLocation("enchanting_table"), "");

    public RenderLuxiumCrystal(){}

    @Override
    public void render(TileLuxiumCrystal crystal, float partialTicks, PoseStack ps, MultiBufferSource source, int light, int overlay) {
        double ticksUp = (ClientTickUtil.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;
        if(crystal.getLux() >= 0) {
            ps.pushPose();
            ps.translate(0.5F, 0.4F + (float)(Math.sin(Math.toRadians(ticksUp)) * 0.08125F), 0.5F);
            ps.scale(2.0F, 2.0F, 2.0F);
            ps.mulPose(Vector3f.YN.rotation((float)ticksUp * 0.0095F));
            renderModel(CRYSTAL, ItemTransforms.TransformType.FIXED, false, ps, source, light, OverlayTexture.NO_OVERLAY);
            ps.popPose();
        }
    }
}
