package com.dublikunt.astelfa.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class MagicalSuffocationEffect extends StatusEffect {

    protected MagicalSuffocationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x82d9d3);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);

        if (entity instanceof PlayerEntity playerEntity) {
            playerEntity.setAir(playerEntity.getAir() - (amplifier * 2));
        }
    }
}
