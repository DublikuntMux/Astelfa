package com.dublikunt.astelfa.mixin;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.air_mana.IPlayerDataSaver;
import com.dublikunt.astelfa.air_mana.ManaAmount;
import com.dublikunt.astelfa.air_mana.PlayerManaData;
import com.dublikunt.astelfa.effects.ModEffects;
import com.dublikunt.astelfa.enchantment.ModEnchantments;
import com.dublikunt.astelfa.enchantment.curse.IllusionCurse;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayerDataSaver {
    @Unique
    private NbtCompound persistentData;

    @Unique
    private boolean isShowManaInfo;

    @Inject(at = @At("RETURN"), method = "tick")
    public void onTick(CallbackInfo ci) {
        CurseTick((PlayerEntity) (Object) this);
        AirManaTick((PlayerEntity) (Object) this);
    }

    @Unique
    private void CurseTick(@NotNull PlayerEntity player) {
        if (player.getWorld().isClient) {
            if (Astelfa.RANDOM.nextInt(1_000) == 0) {
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
            if (Astelfa.RANDOM.nextInt(100) == 0) {
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

    @Unique
    private void AirManaTick(@NotNull PlayerEntity player) {
        if (!player.getWorld().isClient) {
            WorldChunk chunk = player.getWorld().getWorldChunk(player.getBlockPos());

            int mana_around = ManaAmount.getOrCreateManaData(chunk).mana_amount;

            PlayerManaData.setManaAround((IPlayerDataSaver) player, mana_around, ManaAmount.getOrCreateManaData(chunk).local_maximum);

            StatusEffectInstance effect = null;

            if (mana_around <= 150) {
                effect = new StatusEffectInstance(ModEffects.MAGICAL_SUFFOCATION_EFFECT, 40);
            } else if (mana_around >= 900) {
                effect = new StatusEffectInstance(ModEffects.ASCENSION_EFFECT, 40);
            }

            if (effect != null)
                player.addStatusEffect(effect);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfo ci) {
        if (this.persistentData != null) {
            nbt.put("astelfa.player_data", this.persistentData);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    protected void injectReadMethod(@NotNull NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("astelfa.player_data", 10)) {
            this.persistentData = nbt.getCompound("astelfa.player_data");
        }
    }

    @Override
    public NbtCompound astelfa$getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }

        return this.persistentData;
    }

    @Override
    public boolean astelfa$getShowManaInfo() {
        return this.isShowManaInfo;
    }

    @Override
    public void astelfa$setShowManaInfo(boolean value) {
        this.isShowManaInfo = value;
    }
}
