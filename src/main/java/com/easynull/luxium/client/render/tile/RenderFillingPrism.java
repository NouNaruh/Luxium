package com.easynull.luxium.client.render.tile;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.client.render.LuxiumRender;
import com.easynull.luxium.client.utils.ClientUtil;
import com.easynull.luxium.init.tiles.TileFillingPrism;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RenderFillingPrism extends LuxiumRender<TileFillingPrism> {
    public RenderFillingPrism(){}

    @Override
    public void render(TileFillingPrism prism, float partialTicks, PoseStack ps, MultiBufferSource source, int light, int overlay) {
        double ticksUp = (ClientUtil.ticksInGame + partialTicks) * 4;
        ticksUp = ((ticksUp) % 360);
        ItemStack stack = prism.getInv().getItem(0);
        if(!stack.isEmpty()){
            ps.pushPose();
            ps.translate(0.5F, 1.4F + (float)(Math.sin(Math.toRadians(ticksUp)) * 0.43125F), 0.5F);
            ps.scale(0.9F, 0.9F, 0.9F);
            ps.mulPose(Vector3f.YN.rotation((float)ticksUp * 0.0360F));
            ps.mulPose(Vector3f.XP.rotation((float)ticksUp * 0.0360F));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, light, overlay, ps, source, 0);
            ps.popPose();
        }
        ps.pushPose();
        ps.translate(0.5F, 0F, 0.5F);
        ps.scale(4.0F, 4.0F, 4.0F);
        renderModel(new ModelResourceLocation(new ResourceLocation(LuxiumMod.ID, "filling_prism"), ""), ItemTransforms.TransformType.GROUND, false, ps, source, light, overlay);
        ps.popPose();
    }
}
