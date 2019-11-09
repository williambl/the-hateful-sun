package com.williambl.thehatefulsun.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class AmalgamationMeleeAttackGoal extends EntityAIAttackMelee {

    public AmalgamationMeleeAttackGoal(AmalgamationEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }

    @Override
    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
        return this.attacker.width * this.attacker.width + attackTarget.width + 4.0f;
    }
}
