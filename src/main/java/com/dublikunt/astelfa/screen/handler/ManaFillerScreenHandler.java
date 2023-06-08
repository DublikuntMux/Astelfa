package com.dublikunt.astelfa.screen.handler;

import com.dublikunt.astelfa.block.entity.ManaFillerBlockEntity;
import com.dublikunt.astelfa.helper.FluidStack;
import com.dublikunt.astelfa.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class ManaFillerScreenHandler extends ScreenHandler {
    public final ManaFillerBlockEntity blockEntity;
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public FluidStack fluidStack;

    public ManaFillerScreenHandler(int syncId, PlayerInventory inventory, @NotNull PacketByteBuf packetByteBuf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(packetByteBuf.readBlockPos()),
                new ArrayPropertyDelegate(2));
    }

    public ManaFillerScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, BlockEntity entity, PropertyDelegate delegate) {
        super(ModScreenHandlers.MANA_FILLER_SCREEN_HANDLER, syncId);
        this.inventory = (Inventory) entity;
        inventory.onOpen(playerInventory.player);
        this.blockEntity = (ManaFillerBlockEntity) entity;
        this.fluidStack = new FluidStack(blockEntity.fluidStorage.variant, blockEntity.fluidStorage.amount);
        this.propertyDelegate = delegate;

        this.addSlot(new Slot(inventory, 0, 72, 13));
        this.addSlot(new Slot(inventory, 1, 72, 58));
        this.addSlot(new Slot(inventory, 2, 135, 35));
        this.addSlot(new Slot(inventory, 3, 8, 35));

        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInventory, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInventory, si, 8 + si * 18, 142));

        addProperties(delegate);
    }

    public void setFluid(FluidStack stack) {
        fluidStack = stack;
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 54;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < 3) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
