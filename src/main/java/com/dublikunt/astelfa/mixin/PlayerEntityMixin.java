package com.dublikunt.astelfa.mixin;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.enchantment.ModEnchantments;
import com.dublikunt.astelfa.enchantment.curse.IllusionCurse;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerEntity.class})
public class PlayerEntityMixin {
    @Inject(at = @At("RETURN"), method = "tick")
    public void onTick(CallbackInfo ci) {
        CurseTick((PlayerEntity) (Object) this);
    }

    private void CurseTick(@NotNull PlayerEntity player) {
        if (player.getWorld().isClient) {
            if (Astelfa.RANDOM.nextInt(Astelfa.config.illusionChange) == 0) {
                for (ItemStack armor : player.getArmorItems()) {
                    if (!armor.isEmpty() && EnchantmentHelper.getLevel(ModEnchantments.ILLUSION_CURSE, armor) > 0) {
                        player.getWorld()
                                .playSound(
                                        player.getX() - 1.0, player.getY(), player.getZ(),
                                        IllusionCurse.getRandomSound(), SoundCategory.AMBIENT, 0.8F, 1.0F,
                                        false
                                );
                        break;
                    }
                }
            }
            if (Astelfa.RANDOM.nextInt(Astelfa.config.climbingChange) == 0) {
                ItemStack armor = player.getEquippedStack(EquipmentSlot.FEET);
                if (!armor.isEmpty() && EnchantmentHelper.getLevel(ModEnchantments.CLIMBING_CURSE, armor) > 0) {
                    BlockState state = player.getBlockStateAtPos();
                    if (state.getBlock() instanceof LadderBlock) {
                        Direction facing = state.get(HorizontalFacingBlock.FACING).getOpposite();
                        player.velocityModified = true;
                        player.takeKnockback(4.0, facing.getOffsetX(), facing.getOffsetZ());
                    }
                }
            }
        }
    }


}
