package com.silla;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/** Pistas del autobus. El archivo de audio lo pones tu: assets/silla/sounds/dulce_azar.ogg (ver README). */
public final class ModSounds {
    private ModSounds() {}

    public static final SoundEvent DULCE_AZAR = register("bus_dulce_azar");

    private static SoundEvent register(String name) {
        Identifier id = SillaMod.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    /** Pista 1 = Dulce Azar. (Para agregar mas: nueva constante + entrada en sounds.json + boton.) */
    public static SoundEvent track(int n) {
        return n == 1 ? DULCE_AZAR : null;
    }

    public static void init() {}
}
