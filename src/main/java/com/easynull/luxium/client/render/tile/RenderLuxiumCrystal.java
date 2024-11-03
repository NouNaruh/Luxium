package com.easynull.luxium.client.render.tile;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.client.Utils;
import com.easynull.luxium.client.render.LuxiumRender;
import com.easynull.luxium.init.tiles.TileLuxiumCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public class RenderLuxiumCrystal extends LuxiumRender<TileLuxiumCrystal> {
    public static final ModelResourceLocation CRYSTAL = new ModelResourceLocation(new ResourceLocation(LuxiumMod.ID, "luxium_crystal"), "");

    public RenderLuxiumCrystal(){}

    @Override
    public void render(TileLuxiumCrystal crystal, float tick, PoseStack ps, MultiBufferSource source, int light, int overlay) {
        double ticksUp = (Utils.ticksInGame + tick) * 8;
        ticksUp = (ticksUp) % 360;
        if(crystal.getLux() >= 0) {
            ps.pushPose();
            ps.translate(0.5F, 0.5, 0.5F);
            ps.scale(2.0F, 2.0F, 2.0F);
            ps.mulPose(Vector3f.YN.rotationDegrees((float)ticksUp * 360.0F));
            renderModel(CRYSTAL, ItemTransforms.TransformType.FIXED, false, ps, source, light, overlay);
            ps.popPose();
        }
    }
}
