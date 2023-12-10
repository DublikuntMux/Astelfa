package com.dublikunt.astelfa.mixin;

import com.dublikunt.astelfa.helper.chunkstorage.LevelChunkAccess;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.gen.chunk.BlendingData;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;setLightOn(Z)V", shift = At.Shift.BEFORE), method = "deserialize", locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onRead(ServerWorld world, PointOfInterestStorage poiStorage, ChunkPos chunkPos, NbtCompound nbt, CallbackInfoReturnable<ProtoChunk> cir, ChunkPos chunkPos2, UpgradeData upgradeData, boolean bl, NbtList nbtList, int i, ChunkSection[] chunkSections, boolean bl2, ChunkManager chunkManager, LightingProvider lightingProvider, Registry registry, Codec codec, boolean bl3, long m, ChunkStatus.ChunkType chunkType, BlendingData blendingData, Chunk chunk) {
        if (chunk instanceof LevelChunkAccess lc && nbt.contains("astelfa.chunk_data")) {
            lc.astelfa$getStorage().load(nbt.getCompound("astelfa.chunk_data"));
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ChunkSerializer;serializeTicks(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/nbt/NbtCompound;Lnet/minecraft/world/chunk/Chunk$TickSchedulers;)V", shift = At.Shift.BEFORE), method = "serialize", locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onWrite(ServerWorld world, @NotNull Chunk chunk, CallbackInfoReturnable<NbtCompound> cir, ChunkPos chunkPos, NbtCompound nbtCompound) {
        if (chunk.getStatus().getChunkType() != ChunkStatus.ChunkType.PROTOCHUNK) {
            LevelChunkAccess lc = (LevelChunkAccess) chunk;
            NbtCompound tag = lc.astelfa$getStorage().save();
            if (tag != null) {
                nbtCompound.put("astelfa.chunk_data", tag);
            }
        }
    }
}
