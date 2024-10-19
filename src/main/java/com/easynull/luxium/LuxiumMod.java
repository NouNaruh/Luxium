package com.easynull.luxium;

import com.easynull.luxium.api.chronicles.Head;
import com.easynull.luxium.client.renderer.tile.RendererEnergyRelay;
import com.easynull.luxium.init.ModInit;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LuxiumMod.ID)
public class LuxiumMod {
    public static final String ID = "luxium";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LuxiumMod(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

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
        Head.init();
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(ModInit.relay.get(), (r) -> new RendererEnergyRelay());
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event){
    }

    private void processIMC(final InterModProcessEvent event){
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
    }
}
