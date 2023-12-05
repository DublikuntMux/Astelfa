package com.dublikunt.astelfa.helper.chunkstorage;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;
import java.util.function.Supplier;

public class FallbackObject implements DataObject {
    private static final Supplier<FallbackObject> FACTORY = FallbackObject::new;
    private static final Function<Identifier, DataObjectKey<FallbackObject>> CREATE_KEY = Util.memoize(k -> new DataObjectKey<>(k, FACTORY));
    private NbtCompound compound;

    public FallbackObject() {
    }

    public static DataObjectKey<?> key(Identifier rl) {
        return CREATE_KEY.apply(rl);
    }

    public NbtCompound save() {
        return this.compound;
    }

    public void load(NbtCompound compound) {
        this.compound = compound;
    }
}
