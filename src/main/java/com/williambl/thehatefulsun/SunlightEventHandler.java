package com.williambl.thehatefulsun;

import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import com.williambl.thehatefulsun.entity.MutatedPumpkinEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber
public class SunlightEventHandler {

    @SubscribeEvent
    public static void killPlayer(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT) return;
        if (event.player.isCreative()) return;
        if (!TheHatefulSun.isSunHateful(event.player.world)) return;

        if (event.player.world.canBlockSeeSky(event.player.getPosition().offset(EnumFacing.UP))) {
            event.player.attackEntityFrom(
                    new DamageSource("sunlight").setDamageBypassesArmor(),
                    event.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() ? 0.25f : 0.05f
            );
        }
    }

    @SubscribeEvent
    public static void mutateMobs(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().world.isRemote) return;
        if (!TheHatefulSun.isSunHateful(event.getEntity().world)) return;
        if (!event.getEntity().world.canBlockSeeSky(event.getEntity().getPosition())) return;

        if (event.getEntity() instanceof EntityLivingBase && !(event.getEntity() instanceof EntityPlayer) && !(event.getEntity() instanceof AmalgamationEntity) && !(event.getEntity() instanceof MutatedPumpkinEntity)) {
            if (event.getEntity().getEntityId() % 4 != 0)
                return;
            event.getEntity().setDead();
            AmalgamationEntity newEntity = new AmalgamationEntity(event.getEntity().world);
            newEntity.setPositionAndRotation(
                    event.getEntity().posX,
                    event.getEntity().posY,
                    event.getEntity().posZ,
                    event.getEntity().rotationYaw,
                    event.getEntity().rotationPitch
            );

            if (event.getEntity() instanceof EntityAnimal)
                newEntity.setAmalgamationType(AmalgamationEntity.AmalgamationType.QUADRUPED.ordinal());
            else
                newEntity.setAmalgamationType(AmalgamationEntity.AmalgamationType.BLOB.ordinal());

            event.getEntity().world.spawnEntity(newEntity);

            for (int i = 0; i < 10; i++) {
                event.getEntity().world.spawnParticle(
                        EnumParticleTypes.CLOUD,
                        event.getEntity().posX + event.getEntity().world.rand.nextDouble() - 0.5,
                        event.getEntity().posY,
                        event.getEntity().posZ + event.getEntity().world.rand.nextDouble() - 0.5,
                        0.0,
                        0.1,
                        0.0
                );
            }
        }

        if (event.getEntity() instanceof AmalgamationEntity) {
            if (((AmalgamationEntity)event.getEntity()).ticksExisted > 12000 && ((AmalgamationEntity)event.getEntity()).getAmalgamationType() != 2) {
                ((AmalgamationEntity)event.getEntity()).setAmalgamationType(AmalgamationEntity.AmalgamationType.BIG.ordinal());
            }
        }
    }

    @SubscribeEvent
    public static void mutatePumpkin(BlockEvent.CropGrowEvent.Post event) {
        if (event.getWorld().isRemote) return;
        if (!TheHatefulSun.isSunHateful(event.getWorld())) return;
        if (!event.getWorld().canBlockSeeSky(event.getPos())) return;

        if (event.getState().getBlock() == Blocks.PUMPKIN_STEM) {
            for (int i = 0; i < event.getWorld().rand.nextInt(3)+1; i++) {
                MutatedPumpkinEntity entity = new MutatedPumpkinEntity(event.getWorld());
                entity.setPosition(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());

                event.getWorld().spawnEntity(entity);

                for (int j = 0; j < 10; j++) {
                    event.getWorld().spawnParticle(
                            EnumParticleTypes.CLOUD,
                            event.getPos().getX() + event.getWorld().rand.nextDouble() - 0.5,
                            event.getPos().getY(),
                            event.getPos().getZ() + event.getWorld().rand.nextDouble() - 0.5,
                            0.0,
                            0.1,
                            0.0
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void mutatePlayer(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (event.getSource().damageType.equals("sunlight")) {
                AmalgamationEntity newEntity = new AmalgamationEntity(event.getEntity().world);
                newEntity.setPositionAndRotation(
                        event.getEntity().posX,
                        event.getEntity().posY,
                        event.getEntity().posZ,
                        event.getEntity().rotationYaw,
                        event.getEntity().rotationPitch
                );
                newEntity.setAmalgamationType(AmalgamationEntity.AmalgamationType.BLOB.ordinal());
                event.getEntity().world.spawnEntity(newEntity);

                for (int i = 0; i < 10; i++) {
                    event.getEntity().world.spawnParticle(
                            EnumParticleTypes.CLOUD,
                            event.getEntity().posX + event.getEntity().world.rand.nextDouble() - 0.5,
                            event.getEntity().posY,
                            event.getEntity().posZ + event.getEntity().world.rand.nextDouble() - 0.5,
                            0.0,
                            0.1,
                            0.0
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world.getTotalWorldTime() % 24000 == 0) {
            if (event.world.getCurrentMoonPhaseFactor() == 0f) {
                event.world.getPlayers(EntityPlayerMP.class, player -> true).forEach(it -> {
                    it.sendMessage(new TextComponentString("The sun sets its gaze on you."));
                    it.playSound(SoundEvents.AMBIENT_CAVE, 1f, 2f);
                });
            }
        }
    }
}
