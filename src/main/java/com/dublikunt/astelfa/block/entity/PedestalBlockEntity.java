package com.dublikunt.astelfa.block.entity;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.custom.PedestalBlock;
import com.dublikunt.astelfa.helper.block.CraftingBlockEntity;
import com.dublikunt.astelfa.recipe.PedestalRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedestalBlockEntity extends CraftingBlockEntity {
    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PEDESTAL_BLOCK_ENTITY_TYPE, pos, state, 29, 20 * 8);
    }

    private static boolean canInsertItemIntoOutputSlot(@NotNull SimpleInventory inventory, Item output) {
        return inventory.getStack(29).getItem() == output || inventory.getStack(29).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(@NotNull SimpleInventory inventory) {
        return inventory.getStack(29).getMaxCount() > inventory.getStack(29).getCount();
    }

    private static Optional<RecipeEntry<PedestalRecipe>> getCurrentRecipe(@NotNull CraftingBlockEntity entity) {
        SimpleInventory inv = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inv.setStack(i, entity.getStack(i));
        }

        return entity.getWorld().getRecipeManager().getFirstMatch(PedestalRecipe.Type.INSTANCE, inv, entity.getWorld());
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(PedestalBlock.FACING);

        if (side == Direction.UP) {
            return false;
        }

        if (side == Direction.DOWN) {
            return slot == 29;
        }

        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 29 ||
                    side.getOpposite() == Direction.EAST && slot == 29;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 29 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 29;
            case SOUTH -> side == Direction.SOUTH && slot == 29 ||
                    side == Direction.EAST && slot == 29;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 29 ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 29;
        };
    }

    @Override
    protected <T extends CraftingBlockEntity> void craftItem(@NotNull T entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<RecipeEntry<PedestalRecipe>> recipe = getCurrentRecipe(entity);

        if (hasRecipe(entity)) {
            for (int i = 0; i < 29; i++) {
                entity.removeStack(i, 1);
            }

            entity.setStack(28, new ItemStack(recipe.get().value().getResult().getItem(),
                    entity.getStack(28).getCount() + 1));

            entity.resetProgress();
        }
    }

    @Override
    protected <T extends CraftingBlockEntity> boolean hasRecipe(@NotNull T entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<RecipeEntry<PedestalRecipe>> match = getCurrentRecipe(entity);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().value().getResult().getItem());
    }

    public List<ItemStack> getRenderStacks() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            ItemStack itemStack = this.getStack(i);
            if (itemStack != ItemStack.EMPTY) {
                items.add(itemStack);
            }
        }
        return items;
    }
}
