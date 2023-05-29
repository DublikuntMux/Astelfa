package com.dublikunt.astelfa.block.common;

import com.dublikunt.astelfa.particle.ModParticle;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class SparklePillarBlock extends PillarBlock {
    private static final double extraDistance = 0.25D;

    public SparklePillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull Random random) {
        double positionX = ((double) pos.getX() - extraDistance) + (random.nextDouble() + (2 * extraDistance));
        double positionY = ((double) pos.getY() - extraDistance) + (random.nextDouble() + (2 * extraDistance));
        double positionZ = ((double) pos.getZ() - extraDistance) + (random.nextDouble() + (2 * extraDistance));
        world.addParticle(ModParticle.SPARKLE_PARTICLE, positionX, positionY, positionZ, 0, 0, 0);
    }
}
