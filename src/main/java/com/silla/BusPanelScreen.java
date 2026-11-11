package com.silla;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/** Panel del conductor (tecla B dentro del bus): musica y vuelo. */
public class BusPanelScreen extends Screen {
    private boolean volando;

    public BusPanelScreen(boolean volando) {
        super(Text.translatable("screen.silla.bus"));
        this.volando = volando;
    }

    @Override
    protected void init() {
        int w = 150, h = 20, x = this.width / 2 - w / 2, y = this.height / 2 - 40;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("\u266A Dulce azar"), b -> send(BusActionC2S.PLAY_DULCE_AZAR))
                .dimensions(x, y, w, h).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("\u25A0 Detener musica"), b -> send(BusActionC2S.STOP_MUSIC))
                .dimensions(x, y + 26, w, h).build());
        this.addDrawableChild(ButtonWidget.builder(flyLabel(), b -> {
                    volando = !volando;
                    send(BusActionC2S.TOGGLE_FLY);
                    b.setMessage(flyLabel());
                })
                .dimensions(x, y + 58, w, h).build());
    }

    private Text flyLabel() {
        return Text.literal("Volar: " + (volando ? "ACTIVADO" : "apagado"));
    }

    private void send(int action) {
        if (ClientPlayNetworking.canSend(BusActionC2S.ID)) {
            ClientPlayNetworking.send(new BusActionC2S(action));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 58, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
