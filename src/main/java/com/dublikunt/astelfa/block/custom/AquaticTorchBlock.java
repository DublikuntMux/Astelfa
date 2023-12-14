package com.dublikunt.astelfa.block.custom;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.particle.ModParticle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AquaticTorchBlock extends TorchBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty FLOWING_WATER = IntProperty.of("water_level", 1, 8);

    public AquaticTorchBlock(Settings settings, ParticleEffect particle) {
        super(settings, particle);
        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER || fluidstate.getFluid() == Fluids.FLOWING_WATER;
        boolean is_flowing = fluidstate.getFluid() == Fluids.FLOWING_WATER;
        return getDefaultState().with(WATERLOGGED, flag).with(FLOWING_WATER, is_flowing ? fluidstate.getLevel() : 8);
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FLOWING_WATER, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return Helpers.WaterLoggable(state, WATERLOGGED, FLOWING_WATER);
    }

    public void randomDisplayTick(BlockState state, @NotNull World world, @NotNull BlockPos blockPos, Random random) {
        double x = blockPos.getX() + 0.5;
        double y = blockPos.getY() + 0.7;
        double z = blockPos.getZ() + 0.5;
        world.addParticle(ParticleTypes.UNDERWATER, x, y, z, 0.0, 0.0, 0.0);
        world.addParticle(ModParticle.AQUA_FIRE_FLAME, x, y, z, 0.0, 0.0, 0.0);
    }
}
