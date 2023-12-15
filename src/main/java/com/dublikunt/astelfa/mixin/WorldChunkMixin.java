package com.dublikunt.astelfa.mixin;

import com.dublikunt.astelfa.air_mana.ManaAmount;
import com.dublikunt.astelfa.air_mana.ManaDataObject;
import com.dublikunt.astelfa.helper.chunkstorage.DataStorage;
import com.dublikunt.astelfa.helper.chunkstorage.LevelChunkAccess;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldChunk.class)
public class WorldChunkMixin implements LevelChunkAccess {
    @Unique
    private final DataStorage dataStorage = new DataStorage();

    public DataStorage astelfa$getStorage() {
        return this.dataStorage;
    }

    @Inject(method = "runPostProcessing", at = @At("HEAD"))
    public void ProcessChunkMana(CallbackInfo ci) {
        ManaDataObject mana_object = ManaAmount.getOrCreateManaData((WorldChunk) (Object) this);
        if (mana_object.tick_left >= mana_object.tick_to_regen) {
            mana_object.mana_amount += mana_object.per_regen_amount;
            mana_object.tick_left = 0;
        }
        if (mana_object.mana_amount >= mana_object.local_maximum) {
            mana_object.mana_amount = mana_object.local_maximum;
        }
        mana_object.tick_left++;
    }
}
