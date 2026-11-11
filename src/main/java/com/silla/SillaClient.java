package com.silla;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.WeakHashMap;

public class SillaClient implements ClientModInitializer {
    private static final Map<BusEntity, SoundInstance> MUSICA = new WeakHashMap<>();
    private static KeyBinding panelKey;

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(WheelchairModel.LAYER, WheelchairModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.WHEELCHAIR, WheelchairRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(BikeModel.LAYER, BikeModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BIKE, BikeRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(BusModel.LAYER, BusModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BUS, BusRenderer::new);

        // Tecla B (se cambia en Controles -> Nutria Mods): panel del conductor
        panelKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.silla.bus_panel", GLFW.GLFW_KEY_B, "key.categories.silla"));
        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (panelKey.wasPressed()) {
                if (mc.player != null && mc.currentScreen == null
                        && mc.player.getVehicle() instanceof BusEntity bus
                        && bus.getControllingPassenger() == mc.player) {
                    mc.setScreen(new BusPanelScreen(bus.isFlying()));
                }
            }
        });

        BusEntity.CLIENT_MUSIC_HOOK = SillaClient::syncMusic;

        TabletItem.OPEN_SCREEN = () -> MinecraftClient.getInstance().setScreen(new TabletScreen());

        ClientPlayNetworking.registerGlobalReceiver(VoiceS2C.ID, (payload, context) -> VoicePlayer.play(payload));
    }

    private static void syncMusic(BusEntity bus) {
        MinecraftClient mc = MinecraftClient.getInstance();
        SoundInstance old = MUSICA.remove(bus);
        if (old != null) mc.getSoundManager().stop(old);
        SoundEvent ev = ModSounds.track(bus.getMusic());
        if (ev != null) {
            BusMusicSound s = new BusMusicSound(bus, bus.getMusic(), ev);
            MUSICA.put(bus, s);
            mc.getSoundManager().play(s);
        }
    }
}
