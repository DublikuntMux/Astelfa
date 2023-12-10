package com.dublikunt.astelfa.networking;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.networking.packet.FluidSyncS2CPacket;
import com.dublikunt.astelfa.networking.packet.ItemStackSyncS2CPacket;
import com.dublikunt.astelfa.networking.packet.PlayerManaSyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ITEM_SYNC = Helpers.id("item_sync");
    public static final Identifier FLUID_SYNC = Helpers.id("fluid_sync");
    public static final Identifier PLAYER_MANA_SYNC_ID = Helpers.id("player_mana_sync");

    public static void registerC2SPackets() {
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(FLUID_SYNC, FluidSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PLAYER_MANA_SYNC_ID, PlayerManaSyncS2CPacket::receive);
    }
}
