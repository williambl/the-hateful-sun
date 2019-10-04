package com.williambl.thehatefulsun.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class MutatedPumpkinEntity extends MobEntity {

    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private boolean wasOnGround;

    public MutatedPumpkinEntity(EntityType<? extends MutatedPumpkinEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new MutatedPumpkinEntity.MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MutatedPumpkinEntity.FloatGoal(this));
        this.goalSelector.addGoal(2, new MutatedPumpkinEntity.AttackGoal(this));
        this.goalSelector.addGoal(3, new MutatedPumpkinEntity.FaceRandomGoal(this));
        this.goalSelector.addGoal(5, new MutatedPumpkinEntity.HopGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (target) -> Math.abs(target.posY - this.posY) <= 4.0D));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("wasOnGround", this.wasOnGround);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.wasOnGround = compound.getBoolean("wasOnGround");
    }

    protected IParticleData getSquishParticle() {
        return new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Item.getItemFromBlock(Blocks.PUMPKIN)));
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove(); //Forge: Kill entity with notification to caps/subclasses.
        }

        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        super.tick();
        if (this.onGround && !this.wasOnGround) {

            int i = 4;
            for(int j = 0; j < i * 8; ++j) {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
                float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float)i * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float)i * 0.5F * f1;
                World world = this.world;
                IParticleData iparticledata = this.getSquishParticle();
                double d0 = this.posX + (double)f2;
                double d1 = this.posZ + (double)f3;
                world.addParticle(iparticledata, d0, this.getBoundingBox().minY, d1, 0.0D, 0.0D, 0.0D);
            }

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.squishAmount = -0.5F;
        } else if (!this.onGround && this.wasOnGround) {
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

    @Override
    public EntityType<? extends MutatedPumpkinEntity> getType() {
        return (EntityType<? extends MutatedPumpkinEntity>) super.getType();
    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    @Override
    public void applyEntityCollision(Entity entityIn) {
        super.applyEntityCollision(entityIn);
        if (entityIn instanceof IronGolemEntity && this.canDamagePlayer()) {
            this.dealDamage((LivingEntity)entityIn);
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        if (this.canDamagePlayer()) {
            this.dealDamage(entityIn);
        }
    }

    protected void dealDamage(LivingEntity entityIn) {
        if (this.isAlive()) {
            if (this.getDistanceSq(entityIn) < 5.76 && this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength())) {
                this.playSound(SoundEvents.BLOCK_WOOD_BREAK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.applyEnchantments(this, entityIn);
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.625F * sizeIn.height;
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
        return SoundEvents.BLOCK_PUMPKIN_CARVE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    protected SoundEvent getSquishSound() {
        return SoundEvents.BLOCK_PUMPKIN_CARVE;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return this.getType().getLootTable();
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
    protected void jump() {
        Vec3d vec3d = this.getMotion();
        this.setMotion(vec3d.x, (double)0.42F, vec3d.z);
        this.isAirBorne = true;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.BLOCK_WOOD_STEP;
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn);
    }

    static class AttackGoal extends Goal {
        private final MutatedPumpkinEntity mutatedPumpkin;
        private int growTieredTimer;

        public AttackGoal(MutatedPumpkinEntity mutatedPumpkinIn) {
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            LivingEntity livingentity = this.mutatedPumpkin.getAttackTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                return livingentity instanceof PlayerEntity && ((PlayerEntity)livingentity).abilities.disableDamage ? false : this.mutatedPumpkin.getMoveHelper() instanceof MutatedPumpkinEntity.MoveHelperController;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.growTieredTimer = 300;
            super.startExecuting();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            LivingEntity livingentity = this.mutatedPumpkin.getAttackTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (livingentity instanceof PlayerEntity && ((PlayerEntity)livingentity).abilities.disableDamage) {
                return false;
            } else {
                return --this.growTieredTimer > 0;
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.mutatedPumpkin.faceEntity(this.mutatedPumpkin.getAttackTarget(), 10.0F, 10.0F);
            ((MutatedPumpkinEntity.MoveHelperController)this.mutatedPumpkin.getMoveHelper()).setDirection(this.mutatedPumpkin.rotationYaw, this.mutatedPumpkin.canDamagePlayer());
        }
    }

    static class FaceRandomGoal extends Goal {
        private final MutatedPumpkinEntity mutatedPumpkin;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomGoal(MutatedPumpkinEntity mutatedPumpkinIn) {
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return this.mutatedPumpkin.getAttackTarget() == null && (this.mutatedPumpkin.onGround || this.mutatedPumpkin.isInWater() || this.mutatedPumpkin.isInLava() || this.mutatedPumpkin.isPotionActive(Effects.LEVITATION)) && this.mutatedPumpkin.getMoveHelper() instanceof MutatedPumpkinEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.mutatedPumpkin.getRNG().nextInt(60);
                this.chosenDegrees = (float)this.mutatedPumpkin.getRNG().nextInt(360);
            }

            ((MutatedPumpkinEntity.MoveHelperController)this.mutatedPumpkin.getMoveHelper()).setDirection(this.chosenDegrees, false);
        }
    }

    static class FloatGoal extends Goal {
        private final MutatedPumpkinEntity mutatedPumpkin;

        public FloatGoal(MutatedPumpkinEntity mutatedPumpkinIn) {
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            mutatedPumpkinIn.getNavigator().setCanSwim(true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return (this.mutatedPumpkin.isInWater() || this.mutatedPumpkin.isInLava()) && this.mutatedPumpkin.getMoveHelper() instanceof MutatedPumpkinEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.mutatedPumpkin.getRNG().nextFloat() < 0.8F) {
                this.mutatedPumpkin.getJumpController().setJumping();
            }

            ((MutatedPumpkinEntity.MoveHelperController)this.mutatedPumpkin.getMoveHelper()).setSpeed(1.2D);
        }
    }

    static class HopGoal extends Goal {
        private final MutatedPumpkinEntity mutatedPumpkin;

        public HopGoal(MutatedPumpkinEntity mutatedPumpkinIn) {
            this.mutatedPumpkin = mutatedPumpkinIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return !this.mutatedPumpkin.isPassenger();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            ((MutatedPumpkinEntity.MoveHelperController)this.mutatedPumpkin.getMoveHelper()).setSpeed(1.0D);
        }
    }

    static class MoveHelperController extends MovementController {
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
            this.action = MovementController.Action.MOVE_TO;
        }

        public void tick() {
            this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, this.yRot, 90.0F);
            this.mob.rotationYawHead = this.mob.rotationYaw;
            this.mob.renderYawOffset = this.mob.rotationYaw;
            if (this.action != MovementController.Action.MOVE_TO) {
                this.mob.setMoveForward(0.0F);
            } else {
                this.action = MovementController.Action.WAIT;
                if (this.mob.onGround) {
                    this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.mutatedPumpkin.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.mutatedPumpkin.getJumpController().setJumping();
                        this.mutatedPumpkin.playSound(this.mutatedPumpkin.getJumpSound(), this.mutatedPumpkin.getSoundVolume(), ((this.mutatedPumpkin.getRNG().nextFloat() - this.mutatedPumpkin.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                    } else {
                        this.mutatedPumpkin.moveStrafing = 0.0F;
                        this.mutatedPumpkin.moveForward = 0.0F;
                        this.mob.setAIMoveSpeed(0.0F);
                    }
                } else {
                    this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
                }

            }
        }
    }
}
