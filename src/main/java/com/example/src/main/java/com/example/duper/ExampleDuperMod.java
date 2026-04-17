package com.example.duper;

import com.example.duper.commands.DupeCommands;
import com.example.duper.config.DuperConfig;
import com.example.duper.anti_cheat.StealthManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ExampleDuperMod implements ClientModInitializer {
    // Global flag: is duping currently active?
    public static boolean dupeEnabled = false;
    // Currently selected duplication method
    public static DupeMethod currentMethod;

    // Secret hotkey to toggle dupe (default: NONE, user must set it)
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        // Load config from file
        DuperConfig.load();

        // Register client-side commands (/dupe_on, /dupe_off, /dupe_method)
        DupeCommands.register();

        // Initialise stealth features (hides mod from basic detection)
        StealthManager.init();

        // Set default dupe method (chest double-click)
        try {
            currentMethod = new ChestDoubleClickDupe();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Register a hotkey (not required, but adds convenience)
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.example_duper.toggle",      // translation key
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,           // no default key
            "category.example_duper"         // category in controls menu
        ));

        // Every tick, check if the hotkey was pressed
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleKey.wasPressed()) {
                dupeEnabled = !dupeEnabled;
                if (dupeEnabled) {
                    currentMethod.activate();
                    client.player.sendMessage(net.minecraft.text.Text.literal("§a[Duper] Duplication ENABLED"), false);
                } else {
                    currentMethod.deactivate();
                    client.player.sendMessage(net.minecraft.text.Text.literal("§c[Duper] Duplication DISABLED"), false);
                }
            }
        });
    }
}
