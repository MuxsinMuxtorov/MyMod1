package com.example.duper.dupes;

import com.example.duper.ExampleDuperMod;
import com.example.duper.anti_cheat.Humanizer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.screen.slot.SlotActionType;

public class AnvilDupe implements DupeMethod {
    private boolean running = false;
    private Thread worker;

    @Override
    public void activate() {
        if (running) return;
        running = true;
        worker = new Thread(() -> {
            while (running && ExampleDuperMod.dupeEnabled) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.currentScreen instanceof AnvilScreen screen) {
                    // Slot 0: left input, slot 1: right input (usually rename), slot 2: result
                    // Check if result slot has item
                    if (!screen.getScreenHandler().getSlot(2).getStack().isEmpty()) {
                        Humanizer.randomDelay(30, 80);
                        // Take the result item
                        client.interactionManager.clickSlot(
                            screen.getScreenHandler().syncId,
                            2, 0, SlotActionType.PICKUP, client.player
                        );
                        Humanizer.randomDelay(100, 200);
                        // Quickly close to cause race condition
                        client.execute(() -> client.setScreen(null));
                        Humanizer.randomDelay(700, 1500);
                    } else {
                        Humanizer.randomDelay(200, 500);
                    }
                } else {
                    Humanizer.randomDelay(400, 900);
                }
            }
        });
        worker.setDaemon(true);
        worker.start();
    }

    @Override
    public void deactivate() {
        running = false;
        if (worker != null) worker.interrupt();
    }
}
