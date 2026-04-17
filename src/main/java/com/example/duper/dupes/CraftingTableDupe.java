package com.example.duper.dupes;

import com.example.duper.ExampleDuperMod;
import com.example.duper.anti_cheat.Humanizer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.screen.slot.SlotActionType;

public class CraftingTableDupe implements DupeMethod {
    private boolean running = false;
    private Thread worker;

    @Override
    public void activate() {
        if (running) return;
        running = true;
        worker = new Thread(() -> {
            while (running && ExampleDuperMod.dupeEnabled) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.currentScreen instanceof CraftingScreen screen) {
                    // Find a non-empty slot in the crafting grid (slots 0-8 usually)
                    int targetSlot = -1;
                    for (int i = 0; i < 9; i++) {
                        if (!screen.getScreenHandler().getSlot(i).getStack().isEmpty()) {
                            targetSlot = i;
                            break;
                        }
                    }
                    if (targetSlot != -1) {
                        Humanizer.randomDelay(50, 100);
                        // Shift‑click to move item to inventory (creates desync)
                        client.interactionManager.clickSlot(
                            screen.getScreenHandler().syncId,
                            targetSlot, 0, SlotActionType.QUICK_MOVE, client.player
                        );
                        Humanizer.randomDelay(100, 250);
                        // Immediately close the screen – glitch may duplicate
                        client.execute(() -> client.setScreen(null));
                        Humanizer.randomDelay(600, 1200);
                    } else {
                        Humanizer.randomDelay(200, 400);
                    }
                } else {
                    Humanizer.randomDelay(300, 800);
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
