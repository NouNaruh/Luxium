package com.easynull.luxium;

import com.easynull.luxium.init.ModInit;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
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
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){
    }

    private void enqueueIMC(final InterModEnqueueEvent event){
    }

    private void processIMC(final InterModProcessEvent event){
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
    }
}
