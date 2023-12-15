package com.dublikunt.astelfa.block.common;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.particle.ModParticle;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class SparkleSaplingBlock extends SaplingBlock {
    private static final double extraDistance = 0.25;

    public SparkleSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, @NotNull World world, @NotNull BlockPos pos,
                                  @NotNull Random random) {
        Helpers.createParticles(world, pos, random, extraDistance, ModParticle.SPARKLE_PARTICLE);
    }
}
