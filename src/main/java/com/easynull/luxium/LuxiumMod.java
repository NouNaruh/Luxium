package com.easynull.luxium;

import com.easynull.luxium.api.chronicles.Head;
import com.easynull.luxium.client.render.tile.RenderFillingPrism;
import com.easynull.luxium.client.render.tile.RenderLuxiumPulsar;
import com.easynull.luxium.client.utils.ClientUtil;
import com.easynull.luxium.init.ModInit;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LuxiumMod.ID)
public class LuxiumMod {
    public static final String ID = "luxium";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LuxiumMod(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModInit.register(eventBus);
        eventBus.addListener(this::setup);
        eventBus.addListener(this::client);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){
    }

    private void client(final FMLClientSetupEvent event){
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(ClientUtil::clientTick);
        Head.init();
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(ModInit.pulsar.get(), (r) -> new RenderLuxiumPulsar());
            BlockEntityRenderers.register(ModInit.prism.get(), (r) -> new RenderFillingPrism());
        });
        ItemBlockRenderTypes.setRenderLayer(ModInit.luxiumPulsar.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModInit.fillingPrism.get(), RenderType.cutout());
    }

    private void enqueueIMC(final InterModEnqueueEvent event){
    }

    private void processIMC(final InterModProcessEvent event){
    }
}
