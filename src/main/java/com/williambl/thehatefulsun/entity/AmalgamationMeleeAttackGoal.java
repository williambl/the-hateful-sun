package com.williambl.thehatefulsun.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class AmalgamationMeleeAttackGoal extends MeleeAttackGoal {

    public AmalgamationMeleeAttackGoal(AmalgamationEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return this.attacker.getWidth() * this.attacker.getWidth() + attackTarget.getWidth() + 4.0f;
    }
}
