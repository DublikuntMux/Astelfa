package com.dublikunt.astelfa.mixin;

import com.dublikunt.astelfa.helper.chunkstorage.DataStorage;
import com.dublikunt.astelfa.helper.chunkstorage.LevelChunkAccess;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldChunk.class)
public class WorldChunkMixin implements LevelChunkAccess {
    @Unique
    private DataStorage dataStorage = new DataStorage();

    public DataStorage astelfa$getStorage() {
        return this.dataStorage;
    }
}
