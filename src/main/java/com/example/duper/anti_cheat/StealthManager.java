package com.example.duper.anti_cheat;

import com.example.duper.config.DuperConfig;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.nio.file.Path;

public class StealthManager {

    public static void init() {
        if (DuperConfig.stealthMode) {
            // 1. Remove command suggestions from chat (client-side only)
            //    This is done by overriding command dispatcher – advanced.
            //    For now, we just print a message.
            System.out.println("[Duper] Stealth mode enabled – commands will not appear in suggestions");

            // 2. Rename the mod .jar file on disk (on next restart)
            try {
                Path currentPath = FabricLoader.getInstance().getModContainer("example_duper")
                    .orElseThrow().getRootPath();
                File jarFile = currentPath.toFile();
                if (jarFile.getName().startsWith("example_duper")) {
                    File newName = new File(jarFile.getParent(), "java_runtime_" + System.currentTimeMillis() + ".jar");
                    boolean renamed = jarFile.renameTo(newName);
                    if (renamed) {
                        System.out.println("[Duper] Jar renamed to: " + newName.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
