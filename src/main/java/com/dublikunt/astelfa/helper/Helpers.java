package com.dublikunt.astelfa.helper;

import com.dublikunt.astelfa.Astelfa;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Helpers {
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Identifier id(String key) {
        return new Identifier(Astelfa.MOD_ID, key);
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
                delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many "
                        + "ingredients!") :
                        DataResult.success(list)
        ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
    }

    public static void createParticles(@NotNull World world, @NotNull BlockPos pos, @NotNull Random random,
                                       double extraDistance, @NotNull DefaultParticleType particle) {
        double positionX = ((double) pos.getX() - extraDistance) + (random.nextDouble() + (2 * extraDistance));
        double positionY = ((double) pos.getY() - extraDistance) + (random.nextDouble() + (2 * extraDistance));
        double positionZ = ((double) pos.getZ() - extraDistance) + (random.nextDouble() + (2 * extraDistance));
        world.addParticle(particle, positionX, positionY, positionZ, 0, 0, 0);
    }

    public static FluidState WaterLoggable(@NotNull BlockState state, BooleanProperty waterlogged,
                                           IntProperty flowingWater) {
        if (state.get(waterlogged) && state.get(flowingWater) == 8) {
            return Fluids.WATER.getStill(false);
        } else {
            return state.get(waterlogged) && state.get(flowingWater) != 8 ?
                    Fluids.WATER.getFlowing(state.get(flowingWater), false) : Fluids.EMPTY.getDefaultState();
        }
    }

    public static double convertRange(double value, double oldMin, double oldMax, double newMin, double newMax) {
        value = Math.max(oldMin, Math.min(value, oldMax));
        double normalizedValue = (value - oldMin) / (oldMax - oldMin);

        return newMin + normalizedValue * (newMax - newMin);
    }

    public static int getLightLevel(@NotNull World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

    public static float toRadians(float degrees) {
        return (float) (degrees / 180 * Math.PI);
    }
}
