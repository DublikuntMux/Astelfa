package com.dublikunt.astelfa.helper.chunkstorage;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DataObjectKey<T extends DataObject> {
    private final Identifier id;
    private final int hash;
    private final Supplier<T> factory;

    public DataObjectKey(@NotNull Identifier id, Supplier<T> factory) {
        this.id = id;
        this.hash = id.hashCode();
        this.factory = factory;
    }

    public int hashCode() {
        return this.hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            DataObjectKey<?> other = (DataObjectKey) obj;
            if (this.id == null) {
                return other.id == null;
            } else return this.id.equals(other.id);
        }
    }

    public String id() {
        return this.id.toString();
    }

    public T createNew() {
        return this.factory.get();
    }
}

