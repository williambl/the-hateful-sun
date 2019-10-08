package com.williambl.thehatefulsun.entity;

import com.williambl.thehatefulsun.TheHatefulSun;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(TheHatefulSun.MODID)
public class ModEntities {

    @ObjectHolder("amalgamation")
    public static EntityType amalgamation;

    @ObjectHolder("mutated_pumpkin")
    public static EntityType mutatedPumpkin;
}
