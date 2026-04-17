package com.yourname.duper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class DuperMod implements ModInitializer {
    public static boolean dupeEnabled = false;

    @Override
    public void onInitialize() {
        // Register the commands when the mod starts
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            // /dupe_on command
            dispatcher.register(CommandManager.literal("dupe_on")
                    .executes(context -> {
                        dupeEnabled = true;
                        context.getSource().sendFeedback(() -> Text.literal("Duplication ENABLED"), false);
                        return 1;
                    })
            );

            // /dupe_off command
            dispatcher.register(CommandManager.literal("dupe_off")
                    .executes(context -> {
                        dupeEnabled = false;
                        context.getSource().sendFeedback(() -> Text.literal("Duplication DISABLED"), false);
                        return 1;
                    })
            );
        });
    }
}
