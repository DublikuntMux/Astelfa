package com.dublikunt.astelfa.block.common;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.particle.ModParticle;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class SparkleLeavesBlock extends LeavesBlock {
    private static final double extraDistance = 0.25D;

    public SparkleLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull Random random) {
        Helpers.createParticles(world, pos, random, extraDistance, ModParticle.SPARKLE_PARTICLE);
    }
}
