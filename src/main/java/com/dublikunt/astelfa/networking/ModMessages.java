package com.dublikunt.astelfa.networking;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.networking.packet.FluidSyncS2CPacket;
import com.dublikunt.astelfa.networking.packet.ItemStackSyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ITEM_SYNC = Helpers.id("item_sync");
    public static final Identifier FLUID_SYNC = Helpers.id("fluid_sync");

    public static void registerC2SPackets() {
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(FLUID_SYNC, FluidSyncS2CPacket::receive);
    }
}
