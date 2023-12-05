package com.dublikunt.astelfa.helper.chunkstorage;

import net.minecraft.util.Identifier;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ChunkStorageApi {
    public static final String CHUNK_DATA_TAG = "Astelfa-Data";
    private static final Map<Identifier, DataObjectKey<?>> dataObjectKeys = new HashMap<>();

    public static synchronized <T extends DataObject> @NotNull DataObjectKey<T> registerObjectFactory(@NotNull Identifier id, @NotNull Supplier<T> factory) {
        DataObjectKey<T> key = new DataObjectKey<>(id, factory);
        dataObjectKeys.put(id, key);
        return key;
    }

    public static <T extends DataObject> T getFromChunk(@NotNull WorldChunk chunk, @NotNull DataObjectKey<T> key) {
        if (chunk.getWorld().isClient) {
            throw new IllegalStateException("Chunk Data accessed from client, Data ID: " + key.id());
        } else {
            return ((LevelChunkAccess) chunk).astelfa$getStorage().get(key);
        }
    }

    public static <T extends DataObject> T getOrCreateFromChunk(@NotNull WorldChunk chunk, @NotNull DataObjectKey<T> key) {
        if (chunk.getWorld().isClient) {
            throw new IllegalStateException("Chunk Data accessed from client, Data ID: " + key.id());
        } else {
            return ((LevelChunkAccess) chunk).astelfa$getStorage().getOrCreate(key);
        }
    }

    public static DataObjectKey<?> getKey(Identifier id) {
        return dataObjectKeys.get(id);
    }
}