package com.dublikunt.astelfa.helper.air_mana;

import com.dublikunt.astelfa.helper.chunkstorage.DataObject;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class ManaDataObject implements DataObject {
    public int mana_amount = -1;

    @Override
    public NbtCompound save() {
        NbtCompound compound = new NbtCompound();
        compound.putInt("mana_amount", mana_amount);

        return compound;
    }

    @Override
    public void load(@NotNull NbtCompound compound) {
        mana_amount = compound.getInt("mana_amount");
    }
}
