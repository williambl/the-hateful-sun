package com.williambl.thehatefulsun;

import com.williambl.thehatefulsun.client.render.AmalgamationRenderer;
import com.williambl.thehatefulsun.client.render.MutatedPumpkinRenderer;
import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import com.williambl.thehatefulsun.entity.MutatedPumpkinEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(modid=TheHatefulSun.MODID)
public class TheHatefulSun
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "thehatefulsun";

    @Mod.EventHandler
    public static void preInit(final FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MutatedPumpkinEntity.class, MutatedPumpkinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AmalgamationEntity.class, AmalgamationRenderer::new);
    }

    public static boolean isSunHateful(World world) {
        return world.provider.getDimensionType() == DimensionType.OVERWORLD && world.isDaytime() && world.getCurrentMoonPhaseFactor() == 0f;
    }

    @Mod.EventBusSubscriber()
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
            int id = 0;
            event.getRegistry().register(
                    EntityEntryBuilder.create().entity(MutatedPumpkinEntity.class)
                            .tracker(64, 1, true)
                            .id("mutated_pumpkin", id++)
                            .name("mutated_pumpkin")
                            .build()
            );

            event.getRegistry().register(
                    EntityEntryBuilder.create().entity(AmalgamationEntity.class)
                    .tracker(64, 1, true)
                    .id("amalgamation", id++)
                    .name("amalgamation")
                    .build()
            );
        }

    }
}
