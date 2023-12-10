package com.dublikunt.astelfa.air_mana;

import com.dublikunt.astelfa.helper.chunkstorage.DataObject;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class ManaDataObject implements DataObject {
    public int mana_amount = -1;
    public int local_maximum = -1;
    public int tick_to_regen = -1;
    public int per_regen_amount = -1;
    public int tick_left = 0;

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
