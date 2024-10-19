package com.easynull.luxium.client.renderer.tile;

import com.easynull.luxium.init.tiles.TileEnergyRelay;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

public class RendererEnergyRelay implements BlockEntityRenderer<TileEnergyRelay> {
    public RendererEnergyRelay(){}

    @Override
    public void render(TileEnergyRelay tile, float v, PoseStack poseStack, MultiBufferSource source, int i, int i1) {
        if(tile.work) {
            BufferBuilder bufferbuilder = (BufferBuilder) source.getBuffer(RenderType.LINES);
            poseStack.pushPose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(tile.fromX, tile.fromY, tile.toZ);
            bufferbuilder.vertex(-tile.fromX, tile.fromY, tile.toZ).uv(0.0F, 0.0F).endVertex();
            bufferbuilder.vertex(tile.toX, -tile.fromY, tile.toZ).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(tile.fromX, -tile.toY, -tile.fromZ).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(-tile.fromX, -tile.fromY, -tile.fromZ).uv(0.0F, 1.0F).endVertex();
            poseStack.popPose();
        }
    }
}
