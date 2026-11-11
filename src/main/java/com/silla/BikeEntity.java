package com.silla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/** Bicicleta montable. 16 colores; la ROJA lleva 2 jugadores (conductor + pasajero atras). */
public class BikeEntity extends MobEntity {
    private static final TrackedData<Integer> COLOR =
            DataTracker.registerData(BikeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    /** Radio de la rueda en bloques (6 px). */
    private static final float WHEEL_RADIUS = 0.375F;

    private float wheel, prevWheel;
    private double lastX, lastZ;
    private boolean posInit;

    public BikeEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(COLOR, DyeColor.RED.getId());
    }

    public int getColor() {
        return MathHelper.clamp(this.dataTracker.get(COLOR), 0, 15);
    }

    public void setColor(int color) {
        this.dataTracker.set(COLOR, MathHelper.clamp(color, 0, 15));
    }

    /** Solo la bici roja lleva 2 personas. */
    public int capacity() {
        return getColor() == DyeColor.RED.getId() ? 2 : 1;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < capacity();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Color", getColor());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Color")) setColor(nbt.getInt("Color"));
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        // Agachado + click derecho en bici vacia: la guardas como item.
        if (player.isSneaking() && !this.hasPassengers()) {
            if (!this.getWorld().isClient) {
                this.dropStack(new ItemStack(ModEntities.bikeItem(getColor())));
                this.discard();
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        if (this.canAddPassenger(player)) {
            if (!this.getWorld().isClient) player.startRiding(this);
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof PlayerEntity player ? player : null;
    }

    @Override
    protected void tickControlled(PlayerEntity player, Vec3d movementInput) {
        super.tickControlled(player, movementInput);
        this.setRotation(player.getYaw(), player.getPitch() * 0.5F);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity player, Vec3d movementInput) {
        float forward = player.forwardSpeed;
        if (forward <= 0.0F) forward *= 0.3F;
        return new Vec3d(0.0, 0.0, forward); // la bici no va de lado
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity player) {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }

    @Override
    public void tick() {
        super.tick();
        this.prevWheel = this.wheel;
        double x = this.getX(), z = this.getZ();
        if (posInit) {
            double dx = x - lastX, dz = z - lastZ;
            float dist = (float) Math.sqrt(dx * dx + dz * dz);
            if (dist > 1.0E-4F) {
                float rad = this.getYaw() * MathHelper.RADIANS_PER_DEGREE;
                double forward = dx * -MathHelper.sin(rad) + dz * MathHelper.cos(rad);
                this.wheel += (forward >= 0 ? 1 : -1) * dist / WHEEL_RADIUS;
            }
        }
        lastX = x;
        lastZ = z;
        posInit = true;
    }

    public float wheelRoll(float tickDelta) {
        return MathHelper.lerp(tickDelta, prevWheel, wheel);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_CHAIN_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_CHAIN_BREAK;
    }
}
