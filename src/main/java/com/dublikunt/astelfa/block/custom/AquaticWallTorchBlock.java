package com.dublikunt.astelfa.block.custom;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.particle.ModParticle;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AquaticWallTorchBlock extends WallTorchBlock implements Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final IntProperty FLOWING_WATER = IntProperty.of("water_level", 1, 8);
    public static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
            ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(5.5, 3.0, 11.0, 10.5, 13.0, 16.0),
                    Direction.SOUTH, Block.createCuboidShape(5.5, 3.0, 0.0, 10.5, 13.0, 5.0),
                    Direction.WEST, Block.createCuboidShape(11.0, 3.0, 5.5, 16.0, 13.0, 10.5),
                    Direction.EAST, Block.createCuboidShape(0.0, 3.0, 5.5, 5.0, 13.0, 10.5))
    );
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;


    public AquaticWallTorchBlock(AbstractBlock.Settings settings, ParticleEffect particle) {
        super(settings, particle);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(FLOWING_WATER, 8));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        BlockState blockstate = this.getDefaultState();
        WorldView worldView = ctx.getWorld();
        BlockPos blockpos = ctx.getBlockPos();
        Direction[] adirection = ctx.getPlacementDirections();

        for (Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.with(FACING, direction1);
                if (blockstate.canPlaceAt(worldView, blockpos)) {
                    break;
                }
            }
        }

        FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER || fluidstate.getFluid() == Fluids.FLOWING_WATER;
        boolean is_flowing = fluidstate.getFluid() == Fluids.FLOWING_WATER;
        return blockstate.with(WATERLOGGED, flag).with(FLOWING_WATER, is_flowing ? fluidstate.getLevel() : 8);
    }

    @Override
    public BlockState getStateForNeighborUpdate(@NotNull BlockState state, Direction direction,
                                                BlockState neighborState, WorldAccess world, BlockPos pos,
                                                BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction.getOpposite() == state.get(FACING) && !state.isFullCube(world, pos) ?
                Blocks.AIR.getDefaultState() : state;
    }

    public void randomDisplayTick(@NotNull BlockState state, @NotNull World world, @NotNull BlockPos blockPos,
                                  Random random) {
        Direction direction = state.get(FACING);
        double x = blockPos.getX() + 0.5;
        double y = blockPos.getY() + 0.7;
        double z = blockPos.getZ() + 0.5;
        Direction opposite = direction.getOpposite();
        world.addParticle(ParticleTypes.UNDERWATER, x + 0.27 * opposite.getOffsetX(), y + 0.22,
                z + 0.27 * opposite.getOffsetZ(), 0.0, 0.0, 0.0);
        world.addParticle(ModParticle.AQUA_FIRE_FLAME, x + 0.27 * opposite.getOffsetX(), y + 0.22,
                z + 0.27 * opposite.getOffsetZ(), 0.0, 0.0, 0.0);
    }

    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING, FLOWING_WATER, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return Helpers.WaterLoggable(state, WATERLOGGED, FLOWING_WATER);
    }
}
