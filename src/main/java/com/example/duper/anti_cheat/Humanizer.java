package com.example.duper.anti_cheat;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Humanizer {
    private static final Random random = new Random();

    /**
     * Sleeps for a random time between minMs and maxMs milliseconds.
     * Also adds occasional longer "distraction" pauses (10% chance).
     */
    public static void randomDelay(int minMs, int maxMs) {
        try {
            int baseDelay = minMs + random.nextInt(maxMs - minMs);
            // 10% chance to add a longer delay (like a human pausing)
            if (random.nextInt(100) < 10) {
                baseDelay += random.nextInt(150, 400);
            }
            TimeUnit.MILLISECONDS.sleep(baseDelay);
        } catch (InterruptedException ignored) {}
    }

    /**
     * Adds jitter to a value (e.g., mouse coordinate or click timing).
     */
    public static float jitter(float input, float maxOffset) {
        return input + (random.nextFloat() - 0.5f) * maxOffset;
    }

    /**
     * Returns a random integer between min and max inclusive.
     */
    public static int randInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
}
