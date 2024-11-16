package com.easynull.luxium.init.entities.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class LeaveGoal extends Goal {
    private Mob mob;
    public LeaveGoal(Mob mob){
        this.mob = mob;
    }
    @Override
    public boolean canUse() {
        if(mob.getHealth() < 2.0f){
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
    }
}
