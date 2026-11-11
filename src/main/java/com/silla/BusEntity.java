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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Autobus: 12 asientos (conductor + acompanante adelante, 5 sofas dobles atras).
 * El conductor maneja con WASD; con "Volar" activado vuela hacia donde mira. Panel con la tecla B.
 */
public class BusEntity extends MobEntity {
    public static final int SEATS = 12;
    private static final float WHEEL_RADIUS = 0.375F;
    private static final double FLY_SPEED = 0.55;

    private static final TrackedData<Boolean> FLYING =
            DataTracker.registerData(BusEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> MUSIC =
            DataTracker.registerData(BusEntity.class, TrackedDataHandlerRegistry.INTEGER);

    /** Lo asigna el cliente para arrancar/parar el sonido (el servidor lo deja en null). */
    public static Consumer<BusEntity> CLIENT_MUSIC_HOOK = null;

    private float wheel, prevWheel, spin, prevSpin;
    private double lastX, lastZ;
    private boolean posInit;
    private int lastMusic = 0;

    public BusEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(FLYING, false);
        builder.add(MUSIC, 0);
    }

    public boolean isFlying() { return this.dataTracker.get(FLYING); }
    public int getMusic() { return this.dataTracker.get(MUSIC); }

    public void setFlying(boolean v) {
        this.dataTracker.set(FLYING, v);
        this.setNoGravity(v);
    }

    public void setMusic(int track) {
        this.dataTracker.set(MUSIC, track);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Flying", isFlying());
        nbt.putInt("Music", getMusic());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setFlying(nbt.getBoolean("Flying"));
        setMusic(nbt.getInt("Music"));
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < SEATS;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.isSneaking() && !this.hasPassengers()) {
            if (!this.getWorld().isClient) {
                this.dropStack(new ItemStack(ModEntities.BUS_ITEM));
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
        this.setRotation(player.getYaw(), isFlying() ? 0.0F : player.getPitch() * 0.5F);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
        if (isFlying()) {
            // Vuela hacia donde mira el conductor (mirar arriba = sube). Sin W se queda flotando.
            Vec3d dir = Vec3d.fromPolar(player.getPitch(), player.getYaw());
            double f = MathHelper.clamp(player.forwardSpeed, -0.5F, 1.0F);
            this.setVelocity(dir.multiply(f * FLY_SPEED));
        }
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity player, Vec3d movementInput) {
        if (isFlying()) return Vec3d.ZERO; // en vuelo la velocidad se fija en tickControlled
        float forward = player.forwardSpeed;
        if (forward <= 0.0F) forward *= 0.3F;
        return new Vec3d(player.sidewaysSpeed * 0.3F, 0.0, forward);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity player) {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        // sin dano por caida (ni para el bus ni para los pasajeros)
        this.fallDistance = 0.0F;
        for (Entity p : this.getPassengerList()) p.fallDistance = 0.0F;

        if (!world.isClient && isFlying() && this.getControllingPassenger() == null) {
            setFlying(false); // sin conductor: aterriza
        }

        this.prevWheel = this.wheel;
        this.prevSpin = this.spin;
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
        lastX = x; lastZ = z; posInit = true;

        if (world.isClient) {
            if (isFlying()) {
                this.spin += 1.4F;
                cohetes();
            } else {
                this.spin += 0.05F;
            }
            int m = getMusic();
            if (m != lastMusic) {
                lastMusic = m;
                if (CLIENT_MUSIC_HOOK != null) CLIENT_MUSIC_HOOK.accept(this);
            }
        }
    }

    /** Llamas de los 2 cohetes traseros (solo visual, cliente). */
    private void cohetes() {
        float rad = this.getYaw() * MathHelper.RADIANS_PER_DEGREE;
        double lx = -MathHelper.sin(rad), lz = MathHelper.cos(rad);   // adelante
        double sx = MathHelper.cos(rad), sz = MathHelper.sin(rad);    // izquierda
        for (int side = -1; side <= 1; side += 2) {
            double off = side * 0.75, back = -3.0;
            double px = getX() + sx * off + lx * back;
            double pz = getZ() + sz * off + lz * back;
            double py = getY() + 1.3;
            this.getWorld().addParticle(ParticleTypes.FLAME, px, py, pz, -lx * 0.12, -0.02, -lz * 0.12);
            if (this.random.nextInt(3) == 0) {
                this.getWorld().addParticle(ParticleTypes.SMOKE, px, py, pz, -lx * 0.05, 0.0, -lz * 0.05);
            }
        }
    }

    public float wheelRoll(float td) { return MathHelper.lerp(td, prevWheel, wheel); }
    public float fanSpin(float td) { return MathHelper.lerp(td, prevSpin, spin); }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_CHAIN_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_CHAIN_BREAK;
    }
}
