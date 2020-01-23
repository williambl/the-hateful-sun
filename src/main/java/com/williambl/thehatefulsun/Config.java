package com.williambl.thehatefulsun;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraftforge.common.ForgeConfigSpec;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import java.nio.file.Path;

class Config {

    static ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    static ServerConfig SERVER_CONFIG = new ServerConfig(SERVER_BUILDER);
    static ForgeConfigSpec SERVER_SPEC = SERVER_BUILDER.build();

    static boolean onlyOnFullMoon = true;
    static int mutationFraction = 4;
    static boolean mutatePlayers = true;
    static boolean mutatePumpkins = true;
    static float sunDamagePerTick = 0.25f;
    static float sunDamagePerTickWithHelmet = 0.05f;

    static void refreshServer() {
        onlyOnFullMoon = SERVER_CONFIG.onlyOnFullMoon.get();
        mutationFraction = SERVER_CONFIG.mutationFraction.get();
        mutatePlayers = SERVER_CONFIG.mutatePlayers.get();
        mutatePumpkins = SERVER_CONFIG.mutatePumpkins.get();
        sunDamagePerTick = SERVER_CONFIG.sunDamagePerTick.get();
        sunDamagePerTickWithHelmet = SERVER_CONFIG.sunDamagePerTickWithHelmet.get();
    }

    static void loadConfig(ForgeConfigSpec spec, Path path) {
        CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }

    static class ServerConfig {
        ForgeConfigSpec.ConfigValue<Boolean> onlyOnFullMoon;
        ForgeConfigSpec.ConfigValue<Integer> mutationFraction;
        ForgeConfigSpec.ConfigValue<Boolean> mutatePlayers;
        ForgeConfigSpec.ConfigValue<Boolean> mutatePumpkins;
        ForgeConfigSpec.ConfigValue<Float> sunDamagePerTick;
        ForgeConfigSpec.ConfigValue<Float> sunDamagePerTickWithHelmet;

        ServerConfig(ForgeConfigSpec.Builder builder) {
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

}
