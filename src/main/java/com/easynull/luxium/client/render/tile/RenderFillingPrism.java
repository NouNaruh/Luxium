package com.easynull.luxium.client.render.tile;

import com.easynull.luxium.client.Utils;
import com.easynull.luxium.client.render.LuxiumRender;
import com.easynull.luxium.init.tiles.TileFillingPrism;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class RenderFillingPrism extends LuxiumRender<TileFillingPrism> {
    public RenderFillingPrism(){}

    @Override
    public void render(TileFillingPrism prism, float tick, PoseStack ps, MultiBufferSource source, int light, int overlay) {
        double ticksUp = (Utils.ticksInGame + tick) * 4;
        ticksUp = (ticksUp) % 360;
        ItemStack stack = prism.getItemHandler().getItem(0);
        BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(stack);
        if(!stack.isEmpty()){
            ps.pushPose();
            ps.translate(0.5F, 1.0F, 0.5F);
            ps.scale(1.0F, 1.0F, 1.0F);
            ps.mulPose(Vector3f.YN.rotationDegrees((float)ticksUp * 180.0F));
            ps.mulPose(Vector3f.XN.rotationDegrees((float)ticksUp * 30.0F));
            Minecraft.getInstance().getItemRenderer().render(stack, ItemTransforms.TransformType.FIXED, false, ps, source, light, overlay, model);
            ps.popPose();
        }
    }
}
