package com.dublikunt.astelfa.helper.chunkstorage;

import com.dublikunt.astelfa.air_mana.ManaDataObject;
import com.dublikunt.astelfa.helper.Helpers;

public class DataObjectRegistry {
    public static DataObjectKey<ManaDataObject> MANA_DATA_OBJECT_KEY;

    public static void initialize() {
        MANA_DATA_OBJECT_KEY = ChunkStorageApi.registerObjectFactory(Helpers.id("air_mana"), ManaDataObject::new);
    }
}
