package com.dublikunt.astelfa.comand;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import static com.dublikunt.astelfa.comand.ChunkManaCommand.register;

public class ModCommands {
    public static void RegisterCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> register(dispatcher));
    }
}
