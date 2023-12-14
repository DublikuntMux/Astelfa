package com.dublikunt.astelfa.helper.block;

import com.dublikunt.astelfa.helper.notmy.InventoryImpl;
import com.dublikunt.astelfa.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class CraftingBlockEntity extends BlockEntity implements InventoryImpl {
    protected final PropertyDelegate propertyDelegate;
    protected final DefaultedList<ItemStack> inventory;
    protected int progress = 0;
    protected int maxProgress;

    public CraftingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, int maxProgress) {
        super(type, pos, state);
        this.maxProgress = maxProgress;
        this.inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> CraftingBlockEntity.this.progress;
                    case 1 -> CraftingBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CraftingBlockEntity.this.progress = value;
                    case 1 -> CraftingBlockEntity.this.maxProgress = value;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    public static void tick(@NotNull World world, BlockPos blockPos, BlockState state, CraftingBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if (entity.hasRecipe(entity)) {
            entity.progress++;
            markDirty(world, blockPos, state);
            if (entity.progress >= entity.maxProgress) {
                entity.craftItem(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, state);
        }
    }

    protected abstract <T extends CraftingBlockEntity> boolean hasRecipe(@NotNull T entity);

    protected abstract <T extends CraftingBlockEntity> void craftItem(@NotNull T entity);

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("progress", this.progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, this.inventory);
        super.readNbt(nbt);
        this.progress = nbt.getInt("progress");
    }

    public void resetProgress() {
        this.progress = 0;
    }

    public abstract List<ItemStack> getRenderStacks();

    @Override
    public void markDirty() {
        if (!this.world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(this.inventory.size());
            for (ItemStack itemStack : this.inventory) {
                data.writeItemStack(itemStack);
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) this.world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.ITEM_SYNC, data);
            }
        }

        super.markDirty();
    }

    public void setInventory(@NotNull DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }
}
