package com.dublikunt.astelfa.block.entity;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.custom.InfuseTableBlock;
import com.dublikunt.astelfa.recipe.InfuseTableRecipe;
import com.dublikunt.astelfa.screen.handler.InfuseTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class InfuseTableBlockEntity extends CraftingBlockEntity {
    public InfuseTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFUSE_TABLE_BLOCK_ENTITY_TYPE, pos, state, 10);
    }

    @Override
    protected <T extends CraftingBlockEntity> void craftItem(@NotNull T entity) {
            SimpleInventory inventory = new SimpleInventory(entity.size());
            for (int i = 0; i < entity.size(); i++) {
                inventory.setStack(i, entity.getStack(i));
            }

            Optional<RecipeEntry<InfuseTableRecipe>> recipe = getCurrentRecipe(entity);

            if (hasRecipe(entity)) {
                for (int i = 0; i < 9; i++) {
                    entity.removeStack(i, 1);
                }

                entity.setStack(9, new ItemStack(recipe.get().value().getResult().getItem(),
                        entity.getStack(9).getCount() + 1));

                entity.resetProgress();
            }
    }

    @Override
    protected <T extends CraftingBlockEntity> boolean hasRecipe(@NotNull T entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<RecipeEntry<InfuseTableRecipe>> match = getCurrentRecipe(entity);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().value().getResult().getItem());
    }

    private static boolean canInsertItemIntoOutputSlot(@NotNull SimpleInventory inventory, Item output) {
        return inventory.getStack(9).getItem() == output || inventory.getStack(9).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(@NotNull SimpleInventory inventory) {
        return inventory.getStack(9).getMaxCount() > inventory.getStack(9).getCount();
    }

    private static Optional<RecipeEntry<InfuseTableRecipe>> getCurrentRecipe(@NotNull CraftingBlockEntity entity) {
        SimpleInventory inv = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inv.setStack(i, entity.getStack(i));
        }

        return entity.getWorld().getRecipeManager().getFirstMatch(InfuseTableRecipe.Type.INSTANCE, inv, entity.getWorld());
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(InfuseTableBlock.FACING);

        if (side == Direction.UP) {
            return false;
        }

        if (side == Direction.DOWN) {
            return slot == 9;
        }

        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 9 ||
                    side.getOpposite() == Direction.EAST && slot == 9;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 9 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 9;
            case SOUTH -> side == Direction.SOUTH && slot == 9 ||
                    side == Direction.EAST && slot == 9;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 9 ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 9;
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.astelfa.infuse_table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new InfuseTableScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    public ItemStack getRenderStack() {
        if (this.getStack(9).isEmpty()) {
            return this.getStack(0);
        } else {
            return this.getStack(9);
        }
    }
}