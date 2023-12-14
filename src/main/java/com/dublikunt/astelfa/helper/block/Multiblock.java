package com.dublikunt.astelfa.helper.block;

import com.dublikunt.astelfa.helper.common.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class Multiblock {
    private static final ArrayList<BlockState> checkedCache = new ArrayList<>();
    private final HashMap<Character, Predicate<BlockState>> predicates;
    private final int width;
    private final int height;
    private final int length;
    private char[][][] pattern;

    @Contract(pure = true)
    public Multiblock(char @NotNull [] @NotNull [] @NotNull [] pattern, HashMap<Character, Predicate<BlockState>> predicates) {
        this.pattern = pattern;
        this.predicates = predicates;
        this.width = pattern[0].length;
        this.height = pattern.length;
        this.length = pattern[0][0].length;
    }

    public static @Nullable BlockState getBlockStateFromPredicate(Predicate<BlockState> predicate) {
        if (predicate == BlockStatePredicate.ANY) {
            return Blocks.AIR.getDefaultState();
        }

        for (BlockState state : checkedCache) {
            if (predicate.test(state)) {
                return state;
            }
        }

        for (Block block : Registries.BLOCK) {
            for (BlockState state : block.getStateManager().getStates()) {
                if (predicate.test(state)) {
                    checkedCache.add(state);
                    return state;
                }
            }
        }
        return null;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getLength() {
        return this.length;
    }

    public boolean check(BlockPos mainBlockPos, World world) {
        BlockPos corner = findOffset(mainBlockPos);
        if (corner == null) {
            Logger.debug("Multiblock pattern does not contain $");
            return false;
        }

        boolean result = true;

        for (int i = 0; i < this.pattern.length; i++) {
            for (int j = 0; j < this.pattern[i].length; j++) {
                for (int k = 0; k < this.pattern[i][j].length; k++) {
                    BlockPos blockPos = corner.add(j, i, k);
                    Predicate<BlockState> predicate = this.predicates.get(this.pattern[i][j][k]);
                    boolean isRightBlock = predicate.test(world.getBlockState(blockPos));
                    if (!isRightBlock) result = false;
                }
            }
        }
        return result;
    }

    public void rotate() {
        int height = getHeight();
        int width = getWidth();
        int length = getLength();

        char[][][] rotated = new char[height][width][length];

        for (int c = 0; c < height; c++) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    rotated[c][i][j] = this.pattern[c][width - 1 - j][i];
                }
            }
        }
        this.pattern = rotated;
    }

    public BlockPos findOffset(BlockPos pos) {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                for (int k = 0; k < this.length; k++) {
                    if (this.pattern[i][j][k] == '$') {
                        return pos.add(-j, -i, -k);
                    }
                }
            }
        }
        return null;
    }
}
