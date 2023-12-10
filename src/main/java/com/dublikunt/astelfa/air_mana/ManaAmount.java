package com.dublikunt.astelfa.air_mana;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.helper.chunkstorage.ChunkStorageApi;
import com.dublikunt.astelfa.helper.chunkstorage.DataObjectRegistry;
import com.dublikunt.astelfa.helper.notmy.FastNoiseLite;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;

public class ManaAmount {
    private static final FastNoiseLite noise = new FastNoiseLite(Astelfa.RANDOM.nextInt(), FastNoiseLite.NoiseType.OpenSimplex2);

    public static @NotNull ManaDataObject getOrCreateManaData(WorldChunk chunk) {
        ManaDataObject data = ChunkStorageApi.getOrCreateFromChunk(chunk, DataObjectRegistry.MANA_DATA_OBJECT_KEY);
        if (data.mana_amount == -1) {
            data.mana_amount = (int) Helpers.convertRange(noise.GetNoise(chunk.getPos().x, chunk.getPos().z), -1, 1, 300, 900);
            data.local_maximum = (int) Helpers.convertRange(noise.GetNoise(chunk.getPos().x + 1, chunk.getPos().z - 1), -1, 1, 900, 1800);
            data.tick_to_regen = (int) Helpers.convertRange(noise.GetNoise(chunk.getPos().x + 2, chunk.getPos().z - 2), -1, 1, 18000, 20571);
            data.per_regen_amount = (int) Helpers.convertRange(noise.GetNoise(chunk.getPos().x + 3, chunk.getPos().z - 3), -1, 1, 1, 20);
        }
        return data;
    }
}
