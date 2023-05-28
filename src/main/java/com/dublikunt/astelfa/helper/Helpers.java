package com.dublikunt.astelfa.helper;

import com.dublikunt.astelfa.Astelfa;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Helpers {
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Identifier id(String key) {
        return new Identifier(Astelfa.MOD_ID, key);
    }
}
