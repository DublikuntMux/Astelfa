package com.dublikunt.astelfa.networking.packet;

import com.dublikunt.astelfa.air_mana.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

public class PlayerManaSyncS2CPacket {
    public static void receive(@NotNull MinecraftClient client, ClientPlayNetworkHandler handler,
                               @NotNull PacketByteBuf buf, PacketSender responseSender) {
        if (client.player != null) {
            ((IPlayerDataSaver) client.player).astelfa$getPersistentData()
                    .putInt("mama_around", buf.readInt());
            ((IPlayerDataSaver) client.player).astelfa$getPersistentData()
                    .putInt("maximum_amount", buf.readInt());
        }
    }
}
