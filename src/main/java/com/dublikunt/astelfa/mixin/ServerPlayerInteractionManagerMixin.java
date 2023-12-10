package com.dublikunt.astelfa.mixin;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow
    @Final
    protected ServerPlayerEntity player;

    @Inject(at = @At("RETURN"), method = "tryBreakBlock")
    public void onBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (Astelfa.RANDOM.nextInt(30) == 0 &&
                EnchantmentHelper.getLevel(ModEnchantments.LOOSE_CURSE, player.getMainHandStack()) > 0) {
            player.dropItem(player.getMainHandStack(), true);
            player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        }
    }
}
