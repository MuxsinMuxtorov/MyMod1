package com.example.duper.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class DuperConfig {
    public static boolean stealthMode = false;
    public static int minDupeDelayMs = 500;
    public static int maxDupeDelayMs = 1500;
    public static boolean usePacketRandomizer = false;

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("example_duper.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        if (CONFIG_PATH.toFile().exists()) {
            try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
                DuperConfig cfg = GSON.fromJson(reader, DuperConfig.class);
                stealthMode = cfg.stealthMode;
                minDupeDelayMs = cfg.minDupeDelayMs;
                maxDupeDelayMs = cfg.maxDupeDelayMs;
                usePacketRandomizer = cfg.usePacketRandomizer;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            writer.write(GSON.toJson(new DuperConfig()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
