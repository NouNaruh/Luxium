package com.easynull.luxium.client;

import com.easynull.luxium.init.ModCreativeTab;
import com.easynull.luxium.init.blocks.BlockFillingPrism;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

import java.util.Random;

public class Utils {
    public static Minecraft mc = Minecraft.getInstance();
    public static Random rand = new Random();
    public static int ticksInGame = 0;
    public static float partialTicks = 0;


    public static void renderItemGui(ItemStack stack, float x, float y, float xSize, float ySize, float zSize) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        PoseStack posestack = RenderSystem.getModelViewStack();
        BakedModel model = renderer.getModel(stack, null, null, 0);

        posestack.pushPose();
        posestack.translate(x, y, 100.0F);
        posestack.translate((double) xSize / 2, (double) ySize / 2, 0.0D);
        posestack.scale(xSize, -ySize, zSize);

        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        if (!model.usesBlockLight()) {
            Lighting.setupForFlatItems();
        } else {
            Lighting.setupFor3DItems();
        }

        renderer.render(stack, ItemTransforms.TransformType.GUI, false, new PoseStack(), buffer, 15728880, OverlayTexture.NO_OVERLAY, model);
        buffer.endBatch();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (Minecraft.getInstance().isPaused()) {
                ticksInGame++;
                partialTicks = 0;
            }
        }
    }
}
