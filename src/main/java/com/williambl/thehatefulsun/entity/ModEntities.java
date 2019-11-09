package com.williambl.thehatefulsun.entity;

import com.williambl.thehatefulsun.TheHatefulSun;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(TheHatefulSun.MODID)
public class ModEntities {

    @GameRegistry.ObjectHolder("amalgamation")
    public static EntityEntry amalgamation;

    @GameRegistry.ObjectHolder("mutated_pumpkin")
    public static EntityEntry mutatedPumpkin;
}
