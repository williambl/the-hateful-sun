package com.williambl.thehatefulsun.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AmalgamationEntity extends MonsterEntity {
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(AmalgamationEntity.class, DataSerializers.VARINT);

    public AmalgamationEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
        this.setAmalgamationType(AmalgamationType.BLOB.ordinal());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(AmalgamationEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0);
        switch (getAmalgamationType()) {
            case 0:
                this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
                this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
                break;
            case 1:
                this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15);
                this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0);
                break;
            case 2:
                this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
                this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TYPE, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Type", this.getAmalgamationType());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setAmalgamationType(compound.getInt("Type"));
    }

    public void setAmalgamationType(int input) {
        this.dataManager.set(TYPE, input);
    }

    public int getAmalgamationType() {
        return this.dataManager.get(TYPE);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (TYPE.equals(key)) {
            this.recalculateSize();
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.notifyDataManagerChange(key);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, amalgamations and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void livingTick() {
        super.livingTick();
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return getAmalgamationType() == 2 ? 2f : 1f;
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        switch (getAmalgamationType()) {
            case 0:
                return new EntitySize(1.1f, 1.3f, true);
            case 1:
                return new EntitySize(2.04f, 1.04f, true);
            case 2:
                return new EntitySize(4f, 2.5f, true);
            default:
                return super.getSize(poseIn);
        }
    }

    public enum AmalgamationType {
        QUADRUPED,
        BLOB,
        BIG
    }

}
