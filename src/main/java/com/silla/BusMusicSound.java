package com.silla;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

/** Musica que sigue al autobus (se oye dentro y cerca). Se detiene sola si cambias de pista o el bus desaparece. */
public class BusMusicSound extends MovingSoundInstance {
    private final BusEntity bus;
    private final int track;

    public BusMusicSound(BusEntity bus, int track, SoundEvent event) {
        super(event, SoundCategory.RECORDS, SoundInstance.createRandom());
        this.bus = bus;
        this.track = track;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 3.0F;
        follow();
    }

    private void follow() {
        this.x = bus.getX();
        this.y = bus.getY() + 1.0;
        this.z = bus.getZ();
    }

    @Override
    public void tick() {
        if (bus.isRemoved() || bus.getMusic() != track) {
            this.setDone();
            return;
        }
        follow();
    }
}
