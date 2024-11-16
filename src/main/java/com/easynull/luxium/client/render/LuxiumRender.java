package com.easynull.luxium.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class LuxiumRender<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public void renderModel(ModelResourceLocation model, ItemTransforms.TransformType tt, boolean leftHand, PoseStack ps, MultiBufferSource buffer, int light, int overlay) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), tt, leftHand, ps, buffer, light, overlay, bakedmodel);
    }
}
