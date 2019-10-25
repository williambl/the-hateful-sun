package com.williambl.thehatefulsun;

import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import com.williambl.thehatefulsun.entity.ModEntities;
import com.williambl.thehatefulsun.entity.MutatedPumpkinEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SunlightEventHandler {

    @SubscribeEvent
    public static void killPlayer(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT) return;
        if (event.player.isCreative()) return;
        if (!TheHatefulSun.isSunHateful(event.player.world)) return;

        if (event.player.world.canBlockSeeSky(event.player.getPosition().offset(Direction.UP))) {
            event.player.attackEntityFrom(
                    new DamageSource("sunlight").setDamageBypassesArmor(),
                    event.player.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() ? 5.0f : 1.0f
            );
        }
    }

    @SubscribeEvent
    public static void mutateMobs(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().world.isRemote) return;
        if (!TheHatefulSun.isSunHateful(event.getEntity().world)) return;
        if (!event.getEntity().world.canBlockSeeSky(event.getEntity().getPosition())) return;

        if (event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof PlayerEntity) && !(event.getEntity() instanceof AmalgamationEntity) && !(event.getEntity() instanceof AbstractFishEntity) && !(event.getEntity() instanceof MutatedPumpkinEntity)) {
            event.getEntity().remove();
            AmalgamationEntity newEntity = new AmalgamationEntity(ModEntities.amalgamation, event.getEntity().world);
            newEntity.setPositionAndRotation(
                    event.getEntity().posX,
                    event.getEntity().posY,
                    event.getEntity().posZ,
                    event.getEntity().rotationYaw,
                    event.getEntity().rotationPitch
            );

            if (event.getEntity() instanceof AnimalEntity)
                newEntity.setAmalgamationType(AmalgamationEntity.AmalgamationType.QUADRUPED.ordinal());
            else
                newEntity.setAmalgamationType(AmalgamationEntity.AmalgamationType.BLOB.ordinal());

            event.getEntity().world.addEntity(newEntity);

            for (int i = 0; i < 10; i++) {
                event.getEntity().world.addParticle(
                        ParticleTypes.CLOUD,
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
        if (event.getWorld().isRemote()) return;
        if (!TheHatefulSun.isSunHateful(event.getWorld().getWorld())) return;
        if (!event.getWorld().canBlockSeeSky(event.getPos())) return;

        if (event.getState().getBlock() == Blocks.PUMPKIN_STEM) {
            for (int i = 0; i < event.getWorld().getRandom().nextInt(3)+1; i++) {
                MutatedPumpkinEntity entity = new MutatedPumpkinEntity(ModEntities.mutatedPumpkin, event.getWorld().getWorld());
                entity.setPosition(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());

                event.getWorld().addEntity(entity);

                for (int j = 0; j < 10; j++) {
                    event.getWorld().addParticle(
                            ParticleTypes.CLOUD,
                            event.getPos().getX() + event.getWorld().getWorld().rand.nextDouble() - 0.5,
                            event.getPos().getY(),
                            event.getPos().getZ() + event.getWorld().getWorld().rand.nextDouble() - 0.5,
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
        if (event.getEntity() instanceof PlayerEntity) {
            if (event.getSource().damageType.equals("sunlight")) {
                AmalgamationEntity newEntity = new AmalgamationEntity(ModEntities.amalgamation, event.getEntity().world);
                newEntity.setPositionAndRotation(
                        event.getEntity().posX,
                        event.getEntity().posY,
                        event.getEntity().posZ,
                        event.getEntity().rotationYaw,
                        event.getEntity().rotationPitch
                );
                newEntity.setAmalgamationType(AmalgamationEntity.AmalgamationType.BLOB.ordinal());
                event.getEntity().world.addEntity(newEntity);

                for (int i = 0; i < 10; i++) {
                    event.getEntity().world.addParticle(
                            ParticleTypes.CLOUD,
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
}
