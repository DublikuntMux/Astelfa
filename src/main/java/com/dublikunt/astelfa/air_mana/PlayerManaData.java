package com.dublikunt.astelfa.air_mana;

import com.dublikunt.astelfa.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public class PlayerManaData {
    public static void setManaAround(@NotNull IPlayerDataSaver player, int around_amount, int maximum_amount) {
        NbtCompound nbt = player.astelfa$getPersistentData();
        nbt.putInt("mama_around", around_amount);
        nbt.putInt("maximum_amount", maximum_amount);
        syncMana(around_amount, maximum_amount, (ServerPlayerEntity) player);
    }

    public static void syncMana(int mana_around, int maximum_amount, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(mana_around);
        buffer.writeInt(maximum_amount);
        ServerPlayNetworking.send(player, ModMessages.PLAYER_MANA_SYNC_ID, buffer);
    }
}
