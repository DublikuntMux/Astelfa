package com.dublikunt.astelfa.air_mana;

import net.minecraft.nbt.NbtCompound;

public interface IPlayerDataSaver {
    NbtCompound astelfa$getPersistentData();

    boolean astelfa$getShowManaInfo();

    void astelfa$setShowManaInfo(boolean value);
}
