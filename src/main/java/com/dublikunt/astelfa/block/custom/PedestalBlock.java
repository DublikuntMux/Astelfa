package com.dublikunt.astelfa.block.custom;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.entity.PedestalBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class PedestalBlock extends BlockWithEntity implements BlockEntityProvider, Waterloggable {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(Stream.of(
                    Block.createCuboidShape(2, 0, 2, 14, 2, 14),
                    Block.createCuboidShape(4, 2, 4, 12, 12, 12),
                    Block.createCuboidShape(3, 12, 3, 13, 14, 13),
                    Block.createCuboidShape(4, 14, 4, 12, 16, 12)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get(),
            Block.createCuboidShape(2, 0, 2, 14, 16, 14), BooleanBiFunction.OR);

    public PedestalBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(@NotNull BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(@NotNull BlockState state, World world, BlockPos pos, @NotNull BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PedestalBlockEntity) {
                ItemScatterer.spawn(world, pos, (PedestalBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity) {
            ItemStack itemInHand = player.getStackInHand(Hand.MAIN_HAND);
            if (itemInHand.isEmpty()) {
                for (int i = 0; i < blockEntity.size(); i++) {
                    if (!blockEntity.getItems().get(i).isEmpty()) {
                        if (!world.isClient()) {
                            player.setStackInHand(Hand.MAIN_HAND, blockEntity.getItems().get(i));
                            blockEntity.setStack(i, ItemStack.EMPTY);
                        }
                        return ActionResult.success(world.isClient());
                    }
                }
            } else {
                for (int i = 0; i < blockEntity.size() - 1; i++) {
                    if (blockEntity.getItems().get(i).isEmpty()) {
                        if (!world.isClient()) {
                            if (player.getAbilities().creativeMode) {
                                itemInHand = itemInHand.copy();
                            }
                            blockEntity.getItems().set(i, itemInHand.split(1));
                        }
                        return ActionResult.success(world.isClient());
                    }
                }
                return ActionResult.CONSUME_PARTIAL;
            }
        }
        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.PEDESTAL_BLOCK_ENTITY_TYPE, PedestalBlockEntity::tick);
    }
}
