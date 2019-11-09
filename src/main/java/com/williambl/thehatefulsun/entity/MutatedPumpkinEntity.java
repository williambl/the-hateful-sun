package com.williambl.thehatefulsun.entity;

import com.williambl.thehatefulsun.TheHatefulSun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Objects;

public class MutatedPumpkinEntity extends EntityMob {

    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private boolean wasOnGround;

    public MutatedPumpkinEntity(World worldIn) {
        super(worldIn);
        this.moveHelper = new MutatedPumpkinEntity.MoveHelperController(this);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new MutatedPumpkinEntity.FloatTask(this));
        this.tasks.addTask(2, new MutatedPumpkinEntity.AttackTask(this));
        this.tasks.addTask(3, new MutatedPumpkinEntity.FaceRandomTask(this));
        this.tasks.addTask(5, new MutatedPumpkinEntity.HopTask(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("wasOnGround", this.wasOnGround);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.wasOnGround = compound.getBoolean("wasOnGround");
    }

    protected EnumParticleTypes getSquishParticle() {
        return EnumParticleTypes.ITEM_CRACK;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.isDead = true;
        }

        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        super.onUpdate();

        if (this.onGround && !this.wasOnGround)
        {
            int i = 4;
            for (int j = 0; j < i * 8; ++j)
            {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
                float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float)i * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float)i * 0.5F * f1;
                World world = this.world;
                EnumParticleTypes enumparticletypes = this.getSquishParticle();
                double d0 = this.posX + (double)f2;
                double d1 = this.posZ + (double)f3;
                world.spawnParticle(enumparticletypes, d0, this.getEntityBoundingBox().minY, d1, 0.0D, 0.0D, 0.0D);
            }

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.squishAmount = -0.5F;
        }
        else if (!this.onGround && this.wasOnGround)
        {
            this.squishAmount = 1.0F;
        }

        this.wasOnGround = this.onGround;
        this.alterSquishAmount();
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6F;
    }

    /**
     * Gets the amount of time the mutatedPumpkin needs to wait between jumps.
     */
    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    @Override
    public void applyEntityCollision(Entity entityIn) {
        super.applyEntityCollision(entityIn);
        if (entityIn instanceof EntityIronGolem && this.canDamagePlayer()) {
            this.dealDamage((EntityIronGolem)entityIn);
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (this.canDamagePlayer()) {
            this.dealDamage(entityIn);
        }
    }

    protected void dealDamage(EntityLivingBase entityIn) {
        if (this.isEntityAlive()) {
            if (this.getDistanceSq(entityIn) < 5.76 && this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength())) {
                this.playSound(SoundEvents.BLOCK_WOOD_BREAK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.applyEnchantments(this, entityIn);
            }
        }
    }

    /**
     * Indicates weather the mutatedPumpkin is able to damage the player (based upon the mutatedPumpkin's size)
     */
    protected boolean canDamagePlayer() {
        return this.isServerWorld();
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the mutatedPumpkin.
     */
    protected int getAttackStrength() {
        return 4;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_WOOD_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    protected SoundEvent getSquishSound() {
        return SoundEvents.BLOCK_WOOD_HIT;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return new ResourceLocation(TheHatefulSun.MODID, "entities/amalgamation");
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume() {
        return 1f;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    @Override
    public int getVerticalFaceSpeed() {
        return 0;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    @Override
    protected void jump()
    {
        this.motionY = 0.41999998688697815D;
        this.isAirBorne = true;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.BLOCK_WOOD_STEP;
    }

    static class AttackTask extends EntityAIBase {
        private final MutatedPumpkinEntity mutatedPumpkin;
        private int growTieredTimer;

        public AttackTask(MutatedPumpkinEntity mutatedPumpkinIn) {
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean shouldExecute() {
            EntityLivingBase livingentity = this.mutatedPumpkin.getAttackTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isEntityAlive()) {
                return false;
            } else {
                return (!(livingentity instanceof EntityPlayer) || !((EntityPlayer) livingentity).capabilities.disableDamage) && this.mutatedPumpkin.getMoveHelper() instanceof MoveHelperController;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void startExecuting() {
            this.growTieredTimer = 300;
            super.startExecuting();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean shouldContinueExecuting() {
            EntityLivingBase livingentity = this.mutatedPumpkin.getAttackTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isEntityAlive()) {
                return false;
            } else if (livingentity instanceof EntityPlayer && ((EntityPlayer)livingentity).capabilities.disableDamage) {
                return false;
            } else {
                return --this.growTieredTimer > 0;
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask() {
            this.mutatedPumpkin.faceEntity(this.mutatedPumpkin.getAttackTarget(), 10.0F, 10.0F);
            ((MutatedPumpkinEntity.MoveHelperController)this.mutatedPumpkin.getMoveHelper()).setDirection(this.mutatedPumpkin.rotationYaw, this.mutatedPumpkin.canDamagePlayer());
        }
    }

    static class FaceRandomTask extends EntityAIBase {
        private final MutatedPumpkinEntity mutatedPumpkin;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomTask(MutatedPumpkinEntity mutatedPumpkinIn) {
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean shouldExecute() {
            return this.mutatedPumpkin.getAttackTarget() == null && (this.mutatedPumpkin.onGround || this.mutatedPumpkin.isInWater() || this.mutatedPumpkin.isInLava() || this.mutatedPumpkin.isPotionActive(Objects.requireNonNull(Potion.getPotionById(25)))) && this.mutatedPumpkin.getMoveHelper() instanceof MutatedPumpkinEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.mutatedPumpkin.getRNG().nextInt(60);
                this.chosenDegrees = (float)this.mutatedPumpkin.getRNG().nextInt(360);
            }

            ((MutatedPumpkinEntity.MoveHelperController)this.mutatedPumpkin.getMoveHelper()).setDirection(this.chosenDegrees, false);
        }
    }

    static class FloatTask extends EntityAIBase {
        private final MutatedPumpkinEntity mutatedPumpkin;

        public FloatTask(MutatedPumpkinEntity mutatedPumpkinIn) {
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.setMutexBits(5);
            ((PathNavigateGround)mutatedPumpkinIn.getNavigator()).setCanSwim(true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean shouldExecute() {
            return (this.mutatedPumpkin.isInWater() || this.mutatedPumpkin.isInLava()) && this.mutatedPumpkin.getMoveHelper() instanceof MutatedPumpkinEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask() {
            if (this.mutatedPumpkin.getRNG().nextFloat() < 0.8F) {
                this.mutatedPumpkin.getJumpHelper().setJumping();
            }

            ((MutatedPumpkinEntity.MoveHelperController)this.mutatedPumpkin.getMoveHelper()).setSpeed(1.2D);
        }
    }

    static class HopTask extends EntityAIBase {
        private final MutatedPumpkinEntity mutatedPumpkin;

        public HopTask(MutatedPumpkinEntity mutatedPumpkinIn) {
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.setMutexBits(5);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean shouldExecute() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask() {
            ((MutatedPumpkinEntity.MoveHelperController)this.mutatedPumpkin.getMoveHelper()).setSpeed(1.0D);
        }
    }

    static class MoveHelperController extends EntityMoveHelper {
        private float yRot;
        private int jumpDelay;
        private final MutatedPumpkinEntity mutatedPumpkin;
        private boolean isAggressive;

        public MoveHelperController(MutatedPumpkinEntity mutatedPumpkinIn) {
            super(mutatedPumpkinIn);
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.yRot = 180.0F * mutatedPumpkinIn.rotationYaw / (float)Math.PI;
        }

        public void setDirection(float yRotIn, boolean aggressive) {
            this.yRot = yRotIn;
            this.isAggressive = aggressive;
        }

        public void setSpeed(double speedIn) {
            this.speed = speedIn;
            this.action = Action.MOVE_TO;
        }

        public void tick() {
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
            this.entity.rotationYawHead = this.entity.rotationYaw;
            this.entity.renderYawOffset = this.entity.rotationYaw;
            if (this.action != Action.MOVE_TO) {
                this.entity.setMoveForward(0.0F);
            } else {
                this.action = Action.WAIT;
                if (this.entity.onGround) {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.mutatedPumpkin.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.mutatedPumpkin.getJumpHelper().setJumping();
                        this.mutatedPumpkin.playSound(this.mutatedPumpkin.getJumpSound(), this.mutatedPumpkin.getSoundVolume(), ((this.mutatedPumpkin.getRNG().nextFloat() - this.mutatedPumpkin.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                    } else {
                        this.mutatedPumpkin.moveStrafing = 0.0F;
                        this.mutatedPumpkin.moveForward = 0.0F;
                        this.entity.setAIMoveSpeed(0.0F);
                    }
                } else {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                }

            }
        }
    }
}
