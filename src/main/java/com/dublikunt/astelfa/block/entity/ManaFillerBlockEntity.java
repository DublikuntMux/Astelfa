package com.dublikunt.astelfa.block.entity;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.custom.ManaFillerBlock;
import com.dublikunt.astelfa.fluid.ModFluids;
import com.dublikunt.astelfa.helper.FluidStack;
import com.dublikunt.astelfa.helper.block.CraftingBlockEntity;
import com.dublikunt.astelfa.item.ModItems;
import com.dublikunt.astelfa.networking.ModMessages;
import com.dublikunt.astelfa.recipe.ManaFillerRecipe;
import com.dublikunt.astelfa.screen.handler.ManaFillerScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ManaFillerBlockEntity extends CraftingBlockEntity implements ExtendedScreenHandlerFactory {
    public ManaFillerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MANA_FILLER_BLOCK_ENTITY_TYPE, pos, state, 4, 100);
    }

    public static void tick(@NotNull World world, BlockPos blockPos, BlockState state, ManaFillerBlockEntity entity) {
        CraftingBlockEntity.tick(world, blockPos, state, entity);

        if (hasFluidSourceInSlot(entity)) {
            transferFluidToFluidStorage(entity);
        }
    }

    private static boolean hasFluidSourceInSlot(@NotNull ManaFillerBlockEntity entity) {
        return entity.getStack(3).getItem() == ModItems.MANA_BUCKET;
    }

    private static void transferFluidToFluidStorage(@NotNull ManaFillerBlockEntity entity) {
        try (Transaction transaction = Transaction.openOuter()) {
            entity.fluidStorage.insert(FluidVariant.of(ModFluids.STILL_MANA_FLUID),
                    FluidStack.convertDropletsToMb(FluidConstants.BUCKET), transaction);
            transaction.commit();
            entity.setStack(3, new ItemStack(Items.BUCKET));
        }
    }

    private static boolean canInsertItemIntoOutputSlot(@NotNull SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(@NotNull SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }

    private static Optional<RecipeEntry<ManaFillerRecipe>> getCurrentRecipe(@NotNull ManaFillerBlockEntity entity) {
        SimpleInventory inv = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inv.setStack(i, entity.getStack(i));
        }

        return entity.getWorld().getRecipeManager().getFirstMatch(ManaFillerRecipe.Type.INSTANCE, inv,
                entity.getWorld());
    }

    public void setFluidLevel(FluidVariant fluidVariant, long fluidLevel) {
        this.fluidStorage.variant = fluidVariant;
        this.fluidStorage.amount = fluidLevel;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(ManaFillerBlock.FACING);

        if (side == Direction.UP) {
            return false;
        }

        if (side == Direction.DOWN) {
            return slot == 2;
        }

        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 2 ||
                    side.getOpposite() == Direction.EAST && slot == 2;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 2 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 2;
            case SOUTH -> side == Direction.SOUTH && slot == 2 ||
                    side == Direction.EAST && slot == 2;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 2 ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 2;
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.astelfa.mana_filler");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        sendFluidPacket();

        return new ManaFillerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    private void sendFluidPacket() {
        PacketByteBuf data = PacketByteBufs.create();
        this.fluidStorage.variant.toPacket(data);
        data.writeLong(this.fluidStorage.amount);
        data.writeBlockPos(getPos());

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) this.world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.FLUID_SYNC, data);
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    protected <T extends CraftingBlockEntity> boolean hasRecipe(@NotNull T entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<RecipeEntry<ManaFillerRecipe>> match = getCurrentRecipe(((ManaFillerBlockEntity) entity));

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().value().getResult().getItem())
                && ((ManaFillerBlockEntity) entity).fluidStorage.amount >= match.get().value().getManaAmount();
    }

    @Override
    protected <T extends CraftingBlockEntity> void craftItem(@NotNull T entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<RecipeEntry<ManaFillerRecipe>> recipe = getCurrentRecipe((ManaFillerBlockEntity) entity);

        if (hasRecipe(entity)) {
            entity.removeStack(0, 1);
            entity.setStack(2, new ItemStack(recipe.get().value().getResult().getItem(),
                    entity.getStack(2).getCount() + 1));

            entity.resetProgress();

            try (Transaction transaction = Transaction.openOuter()) {
                ((ManaFillerBlockEntity) entity).fluidStorage.extract(FluidVariant.of(ModFluids.STILL_MANA_FLUID),
                        recipe.get().value().getManaAmount(),
                        transaction);
                transaction.commit();
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.put("variant", this.fluidStorage.variant.toNbt());
        nbt.putLong("fluid", this.fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, this.inventory);
        super.readNbt(nbt);
        this.fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("variant"));
        this.fluidStorage.amount = nbt.getLong("fluid");
    }

    public List<ItemStack> getRenderStacks() {
        if (this.getStack(2).isEmpty()) {
            return Collections.singletonList(this.getStack(0));
        } else {
            return Collections.singletonList(this.getStack(2));
        }
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 20;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            if (!ManaFillerBlockEntity.this.world.isClient()) {
                sendFluidPacket();
            }
        }
    };
}