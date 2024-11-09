package com.easynull.luxium.client.utils;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class RenderUtil {
    public static void renderItemGui(ItemStack stack, PoseStack ps, float x, float y, float xSize, float ySize, float zSize) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = renderer.getModel(stack, null, null, 0);

        ps.pushPose();
        ps.translate(x, y, 100.0F);
        ps.translate((double) xSize / 2, (double) ySize / 2, 0.0D);
        ps.scale(xSize, -ySize, zSize);

        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        if (!model.usesBlockLight()) {
            Lighting.setupForFlatItems();
        } else {
            Lighting.setupFor3DItems();
        }

        renderer.render(stack, ItemTransforms.TransformType.GUI, false, new PoseStack(), buffer, 15728880, OverlayTexture.NO_OVERLAY, model);
        buffer.endBatch();
        ps.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}
