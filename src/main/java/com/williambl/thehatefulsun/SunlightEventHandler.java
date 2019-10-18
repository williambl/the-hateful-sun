package com.williambl.thehatefulsun;

import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import com.williambl.thehatefulsun.entity.ModEntities;
import com.williambl.thehatefulsun.entity.MutatedPumpkinEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraftforge.event.TickEvent;
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
        if (!event.player.world.isDaytime()) return;

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
        if (!event.getEntity().world.isDaytime()) return;
        if (!event.getEntity().world.canBlockSeeSky(event.getEntity().getPosition())) return;

        if (event.getEntity() instanceof PigEntity || event.getEntity() instanceof CowEntity) {
            event.getEntity().remove();
            AmalgamationEntity newEntity = new AmalgamationEntity(ModEntities.amalgamation, event.getEntity().world);
            newEntity.setPositionAndRotation(
                    event.getEntity().posX,
                    event.getEntity().posY,
                    event.getEntity().posZ,
                    event.getEntity().rotationYaw,
                    event.getEntity().rotationPitch
            );

            event.getEntity().world.addEntity(newEntity);

            for (int i = 0; i < 10; i++) {
                event.getEntity().world.addParticle(
                        ParticleTypes.CLOUD,
                        event.getEntity().posX+event.getEntity().world.rand.nextDouble()-0.5,
                        event.getEntity().posY,
                        event.getEntity().posZ+event.getEntity().world.rand.nextDouble()-0.5,
                        0.0,
                        0.1,
                        0.0
                );
            }
        }
    }

    @SubscribeEvent
    public static void mutatePumpkin(BlockEvent.CropGrowEvent.Post event) {
        if (event.getWorld().isRemote()) return;
        if (!event.getWorld().getWorld().isDaytime()) return;
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
}