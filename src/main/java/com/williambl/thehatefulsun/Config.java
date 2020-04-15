package com.williambl.thehatefulsun;

import net.minecraftforge.common.ForgeConfigSpec;

class Config {

    final public ForgeConfigSpec.ConfigValue<Boolean> onlyOnFullMoon;
    final public ForgeConfigSpec.ConfigValue<Integer> mutationFraction;
    final public ForgeConfigSpec.ConfigValue<Boolean> mutatePlayers;
    final public ForgeConfigSpec.ConfigValue<Boolean> mutatePumpkins;
    final public ForgeConfigSpec.ConfigValue<Float> sunDamagePerTick;
    final public ForgeConfigSpec.ConfigValue<Float> sunDamagePerTickWithHelmet;

    Config(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        builder.push("main");
        onlyOnFullMoon = builder
                .comment("Do things only on a full moon [default:true]")
                .translation("config.main.only_on_full_moon")
                .define("only_on_full_moon", true);
        builder.pop();
        builder.push("mutation");
        mutationFraction = builder
                .comment("Fraction of mobs mutated (approx. 1/n mobs will mutate) [default:true]")
                .translation("config.mutation.fraction")
                .define("fraction", 4);
        mutatePlayers = builder
                .comment("Mutate players on death [default:true]")
                .translation("config.mutation.players")
                .define("players", true);
        mutatePumpkins = builder
                .comment("Mutate pumpkins [default:true]")
                .translation("config.mutation.pumpkins")
                .define("pumpkins", true);
        builder.pop();
        builder.push("playerdamage");
        sunDamagePerTick = builder
                .comment("Damage per tick for players in sun [default:0.25]")
                .translation("config.playerdamage.sun_damage_per_tick")
                .define("sun_damage_per_tick", 0.25f);
        sunDamagePerTickWithHelmet = builder
                .comment("Damage per tick for players in sun wearing helmet [default:0.05]")
                .translation("config.playerdamage.sun_damage_per_tick_with_helmet")
                .define("sun_damage_per_tick_with_helmet", 0.05f);
        builder.pop();
    }

}
