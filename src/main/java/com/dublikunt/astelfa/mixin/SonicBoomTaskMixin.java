package com.dublikunt.astelfa.mixin;

import com.dublikunt.astelfa.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SonicBoomTask.class)
public class SonicBoomTaskMixin {
    @Unique
    private static boolean entityHasSonicDeflect(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(ModEnchantments.SONIC_DEFLECT_ENCHANTMENT, entity) > 0;
    }

    @Inject(at = @At("HEAD"), method = "method_43265(Lnet/minecraft/entity/mob/WardenEntity;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V")
    private static void sonicDeflect$injectIntoAttack(WardenEntity warden, ServerWorld server, LivingEntity damagedEntity, CallbackInfo ci) {
        if (entityHasSonicDeflect(damagedEntity)) {
            Vec3d targetLookingVector = damagedEntity.getRotationVector();

            Vec3d startingPos = damagedEntity.getPos();
            Vec3d targetPos = damagedEntity.getPos().add(targetLookingVector.multiply(10));

            Vec3d distanceVector = targetPos.subtract(startingPos);
            int distance = MathHelper.floor(distanceVector.length()) + 3 * EnchantmentHelper.getEquipmentLevel(ModEnchantments.SONIC_DEFLECT_ENCHANTMENT, damagedEntity);
            for (int i = 1; i < distance; i++) {
                Vec3d particlePos = startingPos.add(targetLookingVector.multiply(i));
                server.spawnParticles(ParticleTypes.SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);

                List<Entity> entitiesToBeDamaged = server.getOtherEntities(damagedEntity, Box.of(particlePos, 1, 1, 1));
                for (Entity entity : entitiesToBeDamaged) {

                    if (entity instanceof LivingEntity) {
                        entity.damage(entity.getDamageSources().sonicBoom(warden), (float) warden.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) - 0.5f * i);
                    }
                }
            }
        }
    }

    @Inject(
            method = "method_43265(Lnet/minecraft/entity/mob/WardenEntity;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V",
            at = @At(value = "INVOKE", target = "net/minecraft/entity/LivingEntity.addVelocity(DDD)V", shift = At.Shift.BEFORE),
            cancellable = true
    )
    private static void sonicDeflect$cancelKnockback(WardenEntity wardenEntity, ServerWorld serverWorld, LivingEntity livingEntity, CallbackInfo ci) {
        if (entityHasSonicDeflect(livingEntity)) {
            ci.cancel();
        }
    }
}
