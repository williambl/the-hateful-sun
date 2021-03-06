package com.williambl.thehatefulsun;

import com.williambl.thehatefulsun.client.render.AmalgamationRenderer;
import com.williambl.thehatefulsun.client.render.MutatedPumpkinRenderer;
import com.williambl.thehatefulsun.entity.AmalgamationEntity;
import com.williambl.thehatefulsun.entity.MutatedPumpkinEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TheHatefulSun.MODID)
public class TheHatefulSun
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "thehatefulsun";

    public static Config CONFIG;

    public TheHatefulSun() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        CONFIG = ConfigHelper.register(ModConfig.Type.SERVER, Config::new);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MutatedPumpkinEntity.class, MutatedPumpkinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AmalgamationEntity.class, AmalgamationRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    public static boolean isSunHateful(World world) {
        return world.getDimension().getType() == DimensionType.OVERWORLD
                && world.isDaytime()
                && (CONFIG.onlyOnFullMoon.get() || world.getCurrentMoonPhaseFactor() == 0f);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(
                    EntityType.Builder.create(MutatedPumpkinEntity::new, EntityClassification.MONSTER)
                            .setUpdateInterval(1)
                            .setTrackingRange(64)
                            .setShouldReceiveVelocityUpdates(true)
                            .size(2.04f, 2.04f)
                            .build("mutated_pumpkin").setRegistryName("mutated_pumpkin")
            );

            event.getRegistry().register(
                    EntityType.Builder.create(AmalgamationEntity::new, EntityClassification.MONSTER)
                    .setUpdateInterval(1)
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .size(1.1f, 1)
                    .build("amalgamation").setRegistryName("amalgamation")
            );
        }

    }
}
