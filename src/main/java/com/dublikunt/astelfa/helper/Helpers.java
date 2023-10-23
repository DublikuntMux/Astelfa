package com.dublikunt.astelfa.helper;

import com.dublikunt.astelfa.Astelfa;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class Helpers {
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Identifier id(String key) {
        return new Identifier(Astelfa.MOD_ID, key);
    }

    public static <T> @Nullable T getMemory(@NotNull LivingEntity entity, MemoryModuleType<T> memory) {
        return getMemory(entity.getBrain(), memory);
    }

    public static <T> @Nullable T getMemory(Brain<?> brain, MemoryModuleType<T> memory) {
        return memoryOrDefault(brain, memory, () -> {
            return null;
        });
    }

    public static <T> void setMemory(@NotNull LivingEntity entity, MemoryModuleType<T> memoryType, T memory) {
        setMemory(entity.getBrain(), memoryType, memory);
    }

    public static <T> void setMemory(@NotNull Brain<?> brain, MemoryModuleType<T> memoryType, T memory) {
        brain.remember(memoryType, memory);
    }

    public static <T> T memoryOrDefault(@NotNull Brain<?> brain, MemoryModuleType<T> memory, Supplier<T> fallback) {
        return brain.getOptionalRegisteredMemory(memory).orElseGet(fallback);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y) {
        return isMouseOver(mouseX, mouseY, x, y, 16);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int size) {
        return isMouseOver(mouseX, mouseY, x, y, size, size);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY) {
        return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
    }

    public static Codec<List<Ingredient>> validateAmount(@NotNull Codec<Ingredient> delegate, int max) {
        return Codecs.validate(Codecs.validate(
                delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
        ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
    }
}
