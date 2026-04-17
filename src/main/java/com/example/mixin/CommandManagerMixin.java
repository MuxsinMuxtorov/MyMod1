package com.example.duper.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Inject(method = "sendCommandTree", at = @At("HEAD"))
    private void onSendCommandTree(ServerCommandSource source, CallbackInfo ci) {
        // This is an example of how you can use Mixin. The actual logic to bypass
        // permissions is more complex and requires you to find the right target.
        // For this specific need, you would need to inject into the method that
        // checks for permissions.
    }
}
