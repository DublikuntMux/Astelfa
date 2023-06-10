package com.dublikunt.astelfa.block.entity;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.custom.ManaFillerBlock;
import com.dublikunt.astelfa.fluid.ModFluids;
import com.dublikunt.astelfa.helper.FluidStack;
import com.dublikunt.astelfa.helper.notmy.InventoryImpl;
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
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ManaFillerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, InventoryImpl {
    protected final PropertyDelegate propertyDelegate;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 100;

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
            if (!world.isClient()) {
                sendFluidPacket();
            }
        }
    };

    public ManaFillerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MANA_FILLER_BLOCK_ENTITY_TYPE, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> ManaFillerBlockEntity.this.progress;
                    case 1 -> ManaFillerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ManaFillerBlockEntity.this.progress = value;
                    case 1 -> ManaFillerBlockEntity.this.maxProgress = value;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    public static void tick(@NotNull World world, BlockPos blockPos, BlockState state, ManaFillerBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if (hasRecipe(entity)) {
            entity.progress++;
            markDirty(world, blockPos, state);
            if (entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, state);
        }
        if (hasFluidSourceInSlot(entity)) {
            transferFluidToFluidStorage(entity);
        }
    }

    private static void craftItem(@NotNull ManaFillerBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<ManaFillerRecipe> recipe = entity.getWorld().getRecipeManager()
                .getFirstMatch(ManaFillerRecipe.Type.INSTANCE, inventory, entity.getWorld());

        if (hasRecipe(entity)) {
            entity.removeStack(0, 1);
            entity.setStack(2, new ItemStack(recipe.get().getOutput().getItem(),
                    entity.getStack(2).getCount() + 1));

            entity.resetProgress();

            try(Transaction transaction = Transaction.openOuter()) {
                entity.fluidStorage.extract(FluidVariant.of(ModFluids.STILL_MANA_FLUID),
                        recipe.get().getManaAmount(), transaction);
                transaction.commit();
            }
        }
    }

    private static boolean hasRecipe(@NotNull ManaFillerBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<ManaFillerRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(ManaFillerRecipe.Type.INSTANCE, inventory, entity.getWorld());

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem())
                && entity.fluidStorage.amount >= match.get().getManaAmount();
    }

    private static boolean canInsertItemIntoOutputSlot(@NotNull SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(@NotNull SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }

    private static void transferFluidToFluidStorage(@NotNull ManaFillerBlockEntity entity) {
        try(Transaction transaction = Transaction.openOuter()) {
            entity.fluidStorage.insert(FluidVariant.of(ModFluids.STILL_MANA_FLUID),
                    FluidStack.convertDropletsToMb(FluidConstants.BUCKET), transaction);
            transaction.commit();
            entity.setStack(3, new ItemStack(Items.BUCKET));
        }
    }

    private static boolean hasFluidSourceInSlot(@NotNull ManaFillerBlockEntity entity) {
        return entity.getStack(3).getItem() == ModFluids.MANA_BUCKET;
    }

    private void sendFluidPacket() {
        PacketByteBuf data = PacketByteBufs.create();
        fluidStorage.variant.toPacket(data);
        data.writeLong(fluidStorage.amount);
        data.writeBlockPos(getPos());

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.FLUID_SYNC, data);
        }
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
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.astelfa.mana_filler");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("mana_filler.progress", progress);
        nbt.put("mana_filler.variant", fluidStorage.variant.toNbt());
        nbt.putLong("mana_filler.fluid", fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("mana_filler.progress");
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("mana_filler.variant"));
        fluidStorage.amount = nbt.getLong("mana_filler.fluid");
    }

    private void resetProgress() {
        this.progress = 0;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        sendFluidPacket();

        return new ManaFillerScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    public ItemStack getRenderStack() {
        if (this.getStack(2).isEmpty()) {
            return this.getStack(0);
        } else {
            return this.getStack(2);
        }
    }
}