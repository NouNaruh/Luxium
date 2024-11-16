package com.easynull.luxium.init;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.init.entities.DemonTenebrisArmbands;
import com.easynull.luxium.init.entities.SpiceMerchant;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LuxiumMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    //register
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModInit.spiceMerchant.get(), SpiceMerchant.createAttributes().build());
        event.put(ModInit.demonArmbands.get(), DemonTenebrisArmbands.createAttributes().build());
    }
}
