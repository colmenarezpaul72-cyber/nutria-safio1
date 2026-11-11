package com.silla;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class SillaMod implements ModInitializer {
    public static final String ID = "silla";

    public static Identifier id(String path) {
        return Identifier.of(ID, path);
    }

    @Override
    public void onInitialize() {
        SillaConfig.load();
        ModEntities.init();
        ModSounds.init();
        NutriaCommands.init();

        PayloadTypeRegistry.playC2S().register(SpeakC2S.ID, SpeakC2S.CODEC);
        PayloadTypeRegistry.playC2S().register(BusActionC2S.ID, BusActionC2S.CODEC);
        PayloadTypeRegistry.playS2C().register(VoiceS2C.ID, VoiceS2C.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SpeakC2S.ID,
                (payload, context) -> TtsService.hablar(context.player(), payload.text()));
        ServerPlayNetworking.registerGlobalReceiver(BusActionC2S.ID, (payload, context) -> {
            var player = context.player();
            // solo el CONDUCTOR del bus puede usar el panel
            if (!(player.getVehicle() instanceof BusEntity bus) || bus.getControllingPassenger() != player) return;
            switch (payload.action()) {
                case BusActionC2S.STOP_MUSIC -> bus.setMusic(0);
                case BusActionC2S.PLAY_DULCE_AZAR -> bus.setMusic(1);
                case BusActionC2S.TOGGLE_FLY -> bus.setFlying(!bus.isFlying());
                default -> { }
            }
        });
    }
}
