package com.example.duper.dupes;

import com.example.duper.ExampleDuperMod;
import com.example.duper.anti_cheat.Humanizer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.slot.SlotActionType;

public class ChestDoubleClickDupe implements DupeMethod {
    private boolean running = false;
    private Thread worker;

    @Override
    public void activate() {
        if (running) return;
        running = true;
        worker = new Thread(() -> {
            while (running && ExampleDuperMod.dupeEnabled) {
                MinecraftClient client = MinecraftClient.getInstance();
                // Only run if player is looking at a container screen
                if (client.currentScreen instanceof GenericContainerScreen screen) {
                    // Find the first non-empty slot in the container (exclude player inventory)
                    int targetSlot = -1;
                    int containerStart = 0;
                    int containerEnd = screen.getScreenHandler().slots.size() - 36; // exclude hotbar+inventory
                    for (int i = containerStart; i < containerEnd; i++) {
                        if (!screen.getScreenHandler().getSlot(i).getStack().isEmpty()) {
                            targetSlot = i;
                            break;
                        }
                    }
                    if (targetSlot != -1) {
                        // Human-like pre‑delay before starting the double-click
                        Humanizer.randomDelay(40, 120);
                        // First click: pick up item
                        client.interactionManager.clickSlot(
                            screen.getScreenHandler().syncId,
                            targetSlot, 0, SlotActionType.PICKUP, client.player
                        );
                        // Random delay between clicks (20–70ms)
                        Humanizer.randomDelay(20, 70);
                        // Second click: place it back, but glitch sometimes duplicates
                        client.interactionManager.clickSlot(
                            screen.getScreenHandler().syncId,
                            targetSlot, 0, SlotActionType.PICKUP, client.player
                        );
                        // Wait for the glitch to settle (anti‑cheat cooldown)
                        Humanizer.randomDelay(800, 1800);
                    } else {
                        // No items in chest, wait a bit and retry
                        Humanizer.randomDelay(300, 600);
                    }
                } else {
                    // Not in a container screen – wait before checking again
                    Humanizer.randomDelay(500, 1000);
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
