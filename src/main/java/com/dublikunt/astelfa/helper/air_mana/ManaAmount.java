package com.dublikunt.astelfa.helper.air_mana;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.helper.chunkstorage.ChunkStorageApi;
import com.dublikunt.astelfa.helper.chunkstorage.DataObjectRegistry;
import com.dublikunt.astelfa.helper.notmy.FastNoiseLite;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;

public class ManaAmount {
    public static @NotNull ManaDataObject getOrCreateManaData(WorldChunk chunk) {
        ManaDataObject data = ChunkStorageApi.getOrCreateFromChunk(chunk, DataObjectRegistry.MANA_DATA_OBJECT_KEY);
        if (data.mana_amount == -1) {
            FastNoiseLite noise = new FastNoiseLite(Astelfa.RANDOM.nextInt());
            noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);

            data.mana_amount = (int) (300 + ((1000 - 300) / (1 - (-1))) * (noise.GetNoise(chunk.getPos().x, chunk.getPos().z) - (-1)));
        }
        return data;
    }
}
