package com.example.duper.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.example.duper.ExampleDuperMod;
import com.example.duper.dupes.*;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

public class DupeCommands {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            // /dupe_on - enables duplication
            dispatcher.register(ClientCommandManager.literal("dupe_on")
                .executes(context -> {
                    ExampleDuperMod.dupeEnabled = true;
                    ExampleDuperMod.currentMethod.activate();
                    context.getSource().sendFeedback(Text.literal("§aDuplication ENABLED"));
                    return 1;
                })
            );

            // /dupe_off - disables duplication
            dispatcher.register(ClientCommandManager.literal("dupe_off")
                .executes(context -> {
                    ExampleDuperMod.dupeEnabled = false;
                    ExampleDuperMod.currentMethod.deactivate();
                    context.getSource().sendFeedback(Text.literal("§cDuplication DISABLED"));
                    return 1;
                })
            );

            // /dupe_method <chest|crafting|anvil> - switch dupe technique
            dispatcher.register(ClientCommandManager.literal("dupe_method")
                .then(ClientCommandManager.argument("method", StringArgumentType.word())
                    .suggests((ctx, builder) -> {
                        builder.suggest("chest");
                        builder.suggest("crafting");
                        builder.suggest("anvil");
                        return builder.buildFuture();
                    })
                    .executes(context -> {
                        String method = StringArgumentType.getString(context, "method");
                        switch (method) {
                            case "chest" -> ExampleDuperMod.currentMethod = new ChestDoubleClickDupe();
                            case "crafting" -> ExampleDuperMod.currentMethod = new CraftingTableDupe();
                            case "anvil" -> ExampleDuperMod.currentMethod = new AnvilDupe();
                            default -> {
                                context.getSource().sendError(Text.literal("§cUnknown method. Use: chest, crafting, anvil"));
                                return 0;
                            }
                        }
                        context.getSource().sendFeedback(Text.literal("§eDuplication method set to " + method));
                        return 1;
                    })
                )
            );
        });
    }
}
