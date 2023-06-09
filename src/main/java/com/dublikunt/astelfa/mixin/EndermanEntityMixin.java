package com.dublikunt.astelfa.mixin;

import com.dublikunt.astelfa.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends HostileEntity {
    protected EndermanEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
    }

    private static boolean hasEnderEyesEnchantment(@NotNull ItemStack item) {
        if (item.getItem() instanceof ArmorItem) {
            Map<Enchantment, Integer> itemEnchantments = EnchantmentHelper.get(item);
            return itemEnchantments.get(ModEnchantments.ENDER_EYES_ENCHANTMENT) != null;
        }
        return false;
    }

    @Shadow
    public abstract void setAngryAt(@Nullable UUID angryAt);

    @Shadow
    public abstract void setTarget(@Nullable LivingEntity target);

    @Shadow
    public abstract boolean isAngry();

    @Inject(at = @At("RETURN"), method = "isPlayerStaring", cancellable = true)
    void isPlayerStaring(@NotNull PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (hasEnderEyesEnchantment(player.getInventory().getArmorStack(3)) && !this.isAngry()) {
            this.setTarget(null);
            this.setAngryAt(null);
            this.setAttacking(null);
            cir.setReturnValue(false);
        }
    }
}
