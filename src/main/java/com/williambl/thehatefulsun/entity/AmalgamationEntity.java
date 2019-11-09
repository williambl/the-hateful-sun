package com.williambl.thehatefulsun.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class AmalgamationEntity extends EntityMob {
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(AmalgamationEntity.class, DataSerializers.VARINT);

    public AmalgamationEntity(World world) {
        super(world);
        this.setAmalgamationType(AmalgamationType.BLOB.ordinal());
        this.setSize(this.getSize().getLeft(), this.getSize().getLeft());
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(2, new AmalgamationMeleeAttackGoal(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityPigZombie.class));
        this.targetTasks.addTask(2, new EntityAIFindEntityNearestPlayer(this));
        this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0);
        switch (getAmalgamationType()) {
            case 0:
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
                this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
                break;
            case 1:
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15);
                this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0);
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0);
                break;
            case 2:
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
                this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TYPE, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Type", this.getAmalgamationType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setAmalgamationType(compound.getInteger("Type"));
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
            this.setSize(this.getSize().getLeft(), this.getSize().getRight());
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.notifyDataManagerChange(key);
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
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    public Pair<Float, Float> getSize() {
        switch (getAmalgamationType()) {
            case 0:
                return new ImmutablePair<>(1.1f, 1.3f);
            case 1:
                return new ImmutablePair<>(2.04f, 1.04f);
            case 2:
                return new ImmutablePair<>(4f, 2.5f);
            default:
                return new ImmutablePair<>(1f, 1f);
        }
    }

    public enum AmalgamationType {
        QUADRUPED,
        BLOB,
        BIG
    }

}
