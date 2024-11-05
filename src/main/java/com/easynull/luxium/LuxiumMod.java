package com.easynull.luxium;

import com.easynull.luxium.api.chronicles.Head;
import com.easynull.luxium.client.Utils;
import com.easynull.luxium.client.render.tile.RenderFillingPrism;
import com.easynull.luxium.client.render.tile.RenderLuxiumCrystal;
import com.easynull.luxium.init.ModInit;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

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
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            forgeBus.addListener(Utils::clientTick);
            return new Object();
        });
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event){
    }

    private void client(final FMLClientSetupEvent event){
        Head.init();
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(ModInit.crystal.get(), (r) -> new RenderLuxiumCrystal());
            BlockEntityRenderers.register(ModInit.prism.get(), (r) -> new RenderFillingPrism());
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
