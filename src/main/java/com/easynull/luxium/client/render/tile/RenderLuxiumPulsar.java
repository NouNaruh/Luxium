package com.easynull.luxium.client.render.tile;

import com.easynull.luxium.client.render.LuxiumRender;
import com.easynull.luxium.client.utils.ClientUtil;
import com.easynull.luxium.init.tiles.TileLuxiumPulsar;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

import static net.minecraft.client.renderer.entity.EnderDragonRenderer.CRYSTAL_BEAM_LOCATION;

public class RenderLuxiumPulsar extends LuxiumRender<TileLuxiumPulsar> {
    public RenderLuxiumPulsar() {
    }

    @Override
    public void render(TileLuxiumPulsar crystal, float partialTicks, PoseStack ps, MultiBufferSource source, int light, int overlay) {
        double ticksUp = (ClientUtil.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;
        Tesselator tessellate = Tesselator.getInstance();
        BufferBuilder bb = tessellate.getBuilder();
        for (BlockPos p : crystal.receivers) {
            if (p != null && crystal.getLux() > 0) {
                float pX = (float) (p.getX() - Mth.lerp((double) partialTicks, crystal.pX(), crystal.pX()));
                float pY = (float) (p.getY() - Mth.lerp((double) partialTicks, crystal.pY(), crystal.pY()));
                float pZ = (float) (p.getZ() - Mth.lerp((double) partialTicks, crystal.pZ(), crystal.pZ()));
                if (crystal.getLux() >= 0) {
                    float $$8 = Mth.sqrt(pX * pX + pZ * pZ);
                    float centerPos = Mth.sqrt(pX * pX + pY * pY + pZ * pZ);
                    ps.pushPose();
                    ps.translate(0.0, 2.0, 0.0);
                    ps.mulPose(Vector3f.YP.rotation((float) (-Math.atan2(pZ, pX)) - 1.5707964F));
                    ps.mulPose(Vector3f.XP.rotation((float) (-Math.atan2($$8, pY)) - 1.5707964F));
                    VertexConsumer vc = source.getBuffer(RenderType.entitySmoothCutout(CRYSTAL_BEAM_LOCATION));
                    float $$11 = 0.0F - ((float) ticksUp) * 0.01F;
                    float $$12 = Mth.sqrt(pX * pX + pY * pY + pZ * pZ) / 32.0F - ((float) ticksUp) * 0.01F;
                    float $$14 = 0.0F;
                    float $$15 = 0.75F;
                    float $$16 = 0.0F;
                    PoseStack.Pose $$17 = ps.last();
                    Matrix4f $$18 = $$17.pose();
                    Matrix3f $$19 = $$17.normal();

                    for (int $$20 = 1; $$20 <= 8; ++$$20) {
                        float $$21 = Mth.sin((float) $$20 * 6.2831855F / 8.0F) * 0.75F;
                        float $$22 = Mth.cos((float) $$20 * 6.2831855F / 8.0F) * 0.75F;
                        float $$23 = (float) $$20 / 8.0F;
                        bb.vertex($$18, $$14 * 0.2F, $$15 * 0.2F, 0.0F).color(0, 0, 0, 255).uv($$16, $$11).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal($$19, 0.0F, -1.0F, 0.0F).endVertex();
                        bb.vertex($$18, $$14, $$15, centerPos).color(255, 255, 255, 255).uv($$16, $$12).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal($$19, 0.0F, -1.0F, 0.0F).endVertex();
                        bb.vertex($$18, $$21, $$22, centerPos).color(255, 255, 255, 255).uv($$23, $$12).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal($$19, 0.0F, -1.0F, 0.0F).endVertex();
                        bb.vertex($$18, $$21 * 0.2F, $$22 * 0.2F, 0.0F).color(0, 0, 0, 255).uv($$23, $$11).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal($$19, 0.0F, -1.0F, 0.0F).endVertex();
                        $$14 = $$21;
                        $$15 = $$22;
                        $$16 = $$23;
                    }
                    bb.building();
                    ps.popPose();
                }
            }
        }
    }
}
