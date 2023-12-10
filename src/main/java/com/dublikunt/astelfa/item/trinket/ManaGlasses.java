package com.dublikunt.astelfa.item.trinket;

import com.dublikunt.astelfa.air_mana.IPlayerDataSaver;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ManaGlasses extends TrinketItem {
    public ManaGlasses(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, @NotNull List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.astelfa.mana_glasses.tooltip"));
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            ((IPlayerDataSaver) playerEntity).astelfa$setShowManaInfo(true);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            ((IPlayerDataSaver) playerEntity).astelfa$setShowManaInfo(false);
        }
    }
}
