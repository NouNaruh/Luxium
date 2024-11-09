package com.easynull.luxium.init.entities;

import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DemonTenebrisArmbands extends AmbientCreature {
    boolean hasSpawned = false;
    public DemonTenebrisArmbands(EntityType<? extends AmbientCreature> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.MOVEMENT_SPEED, 0.23000000417232513).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.MAX_HEALTH, 80.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide && !hasSpawned) {
            hasSpawned = true;
            sendMessage("message.luxium.entity.demon.first_meeting.1");
            sendMessage("message.luxium.entity.demon.first_meeting.2");
        }
    }
    public void sendMessage(String message){
        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, getBoundingBox().inflate(50))) {
            player.sendMessage(new TranslatableComponent(message), Util.NIL_UUID);
        }
    }
}
