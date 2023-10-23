package com.dublikunt.astelfa.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

public class IrritantEntity extends AmbientEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Nullable
    private BlockPos hangingPosition;

    public IrritantEntity(EntityType<? extends AmbientEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "idle", 5,
                state -> state.setAndContinue(DefaultAnimations.IDLE)));
        controllerRegistrar.add(new AnimationController<>(this, "fly", 5,
                state -> state.setAndContinue(DefaultAnimations.FLY)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void tick() {
        super.tick();
        this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        if (this.hangingPosition != null && (!this.getWorld().isAir(this.hangingPosition) || this.hangingPosition.getY() <= this.getWorld().getBottomY())) {
            this.hangingPosition = null;
        }

        if (this.hangingPosition == null || this.random.nextInt(30) == 0 || this.hangingPosition.isWithinDistance(this.getPos(), 2.0)) {
            this.hangingPosition = BlockPos.ofFloored(
                    this.getX() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7),
                    this.getY() + (double) this.random.nextInt(6) - 2.0,
                    this.getZ() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7)
            );
        }

        double x = this.hangingPosition.getX() + 0.5 - this.getX();
        double y = this.hangingPosition.getY() + 0.1 - this.getY();
        double z = this.hangingPosition.getZ() + 0.5 - this.getZ();
        Vec3d velocity = this.getVelocity();
        Vec3d newVelocity = velocity.add((Math.signum(x) * 0.5 - velocity.x) * 0.1F, (Math.signum(y) * 0.7F - velocity.y) * 0.1F, (Math.signum(z) * 0.5 - velocity.z) * 0.1F);
        this.setVelocity(newVelocity);
        float atan = (float) (MathHelper.atan2(newVelocity.z, newVelocity.x) * 180.0F / (float) Math.PI) - 90.0F;
        float degrees = MathHelper.wrapDegrees(atan - this.getYaw());
        this.forwardSpeed = 0.5F;
        this.setYaw(this.getYaw() + degrees);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height / 2.0F;
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.EVENTS;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            return super.damage(source, amount);
        }
    }
}
