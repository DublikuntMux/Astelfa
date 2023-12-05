package com.dublikunt.astelfa.helper.chunkstorage;

import com.dublikunt.astelfa.helper.common.Logger;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private Map<DataObjectKey<?>, DataObject> data;

    public NbtCompound save() {
        if (this.data != null && !this.data.isEmpty()) {
            NbtCompound tag = new NbtCompound();
            this.data.forEach((r, e) -> {
                try {
                    tag.put(r.id(), e.save());
                } catch (Exception var4) {
                    Logger.debug("Data object failed to save, please report it to the mod author. Object ID: " + r);
                }
            });
            return tag;
        } else {
            return null;
        }
    }

    public void load(@NotNull NbtCompound compound) {
        if (!compound.isEmpty()) {
            this.data = new HashMap();
            compound.getKeys().forEach(key -> {
                try {
                    Identifier rl = new Identifier(key);
                    NbtCompound tag = compound.getCompound(key);
                    DataObjectKey<?> k = ChunkStorageApi.getKey(rl);
                    if (k == null) {
                        k = FallbackObject.key(rl);
                    }

                    DataObject obj = k.createNew();
                    obj.load(tag);
                    this.data.put(k, obj);
                } catch (Exception var7) {
                    Logger.debug("Data object failed to load, please report it to the mod author. Object ID: " + key);
                }
            });
        }
    }

    public <T extends DataObject> T get(DataObjectKey<T> key) {
        if (this.data == null) {
            return null;
        } else {
            DataObject d = this.data.get(key);
            return (T) (d instanceof FallbackObject ? null : d);
        }
    }

    public <T extends DataObject> T getOrCreate(DataObjectKey<T> key) {
        if (this.data == null) {
            this.data = new HashMap<>();
        }

        DataObject d = this.data.computeIfAbsent(key, DataObjectKey::createNew);
        if (d instanceof FallbackObject) {
            throw new IllegalStateException("Attempting to access an unregistered type, please report it to the mod author! Object ID: " + key.id());
        } else {
            return (T) d;
        }
    }
}
