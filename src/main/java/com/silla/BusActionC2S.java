package com.silla;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

/** Cliente -> servidor: boton del panel del conductor. */
public record BusActionC2S(int action) implements CustomPayload {
    public static final int STOP_MUSIC = 0, PLAY_DULCE_AZAR = 1, TOGGLE_FLY = 2;
    public static final Id<BusActionC2S> ID = new Id<>(SillaMod.id("bus_accion"));
    public static final PacketCodec<RegistryByteBuf, BusActionC2S> CODEC =
            PacketCodec.tuple(PacketCodecs.INTEGER, BusActionC2S::action, BusActionC2S::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
