package com.dublikunt.astelfa.block;

import com.dublikunt.astelfa.block.entity.InfuseTableBlockEntity;
import com.dublikunt.astelfa.block.entity.ManaFillerBlockEntity;
import com.dublikunt.astelfa.block.entity.PedestalBlockEntity;
import com.dublikunt.astelfa.helper.Helpers;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<InfuseTableBlockEntity> INFUSE_TABLE_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<ManaFillerBlockEntity> MANA_FILLER_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<PedestalBlockEntity> PEDESTAL_BLOCK_ENTITY_TYPE;

    public static void register() {
        INFUSE_TABLE_BLOCK_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Helpers.id("infuse_table"),
                FabricBlockEntityTypeBuilder.create(InfuseTableBlockEntity::new, ModBlocks.INFUSING_TABLE_BLOCK).build());
        MANA_FILLER_BLOCK_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Helpers.id("mana_filler"),
                FabricBlockEntityTypeBuilder.create(ManaFillerBlockEntity::new, ModBlocks.MANA_FILLER_BLOCK).build());
        PEDESTAL_BLOCK_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Helpers.id("pedestal"),
                FabricBlockEntityTypeBuilder.create(PedestalBlockEntity::new, ModBlocks.PEDESTAL_BLOCK).build());

        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage,
                MANA_FILLER_BLOCK_ENTITY_TYPE);
    }
}
