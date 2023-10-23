package com.dublikunt.astelfa.screen;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.screen.handler.InfuseTableScreenHandler;
import com.dublikunt.astelfa.screen.handler.ManaFillerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static void registry() {
        Registry.register(Registries.SCREEN_HANDLER, Helpers.id("infuse_table"), INFUSE_TABLE_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, Helpers.id("mana_filler"), MANA_FILLER_SCREEN_HANDLER);
    }

    public static final ScreenHandlerType<InfuseTableScreenHandler> INFUSE_TABLE_SCREEN_HANDLER =
            new ExtendedScreenHandlerType<>(InfuseTableScreenHandler::new);
    public static final ScreenHandlerType<ManaFillerScreenHandler> MANA_FILLER_SCREEN_HANDLER =
            new ExtendedScreenHandlerType<>(ManaFillerScreenHandler::new);


}
