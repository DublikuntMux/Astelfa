package com.dublikunt.astelfa.helper.chunkstorage;

import net.minecraft.nbt.NbtCompound;

public interface DataObject {
    NbtCompound save();

    void load(NbtCompound compound);
}
