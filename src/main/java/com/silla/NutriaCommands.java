package com.silla;

import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  /nutria letrero        -> modo letrero: TODO lo que escribas en el chat NO se envia y se vuelve un letrero.
 *  /nutria letrero parar  -> sale del modo letrero.
 *  /nutria menu     -> menu para elegir tipo de letrero y tinte (los letreros salen con texto brillante).
 */
public final class NutriaCommands {
    private NutriaCommands() {}

    /** Lo que el jugador eligio en el menu (se recuerda mientras el servidor este abierto). */
    public static final class Seleccion {
        public int tipo = 0;                       // indice en NutriaMenu.LETREROS
        public int tinte = DyeColor.BLACK.getId(); // DyeColor.getId()
        public String texto = "Nutria";
    }

    private static final Map<UUID, Seleccion> SELECCION = new ConcurrentHashMap<>();
    /** Jugadores con el modo letrero ACTIVO (sigue activo hasta /nutria letrero parar). */
    private static final Set<UUID> MODO_LETRERO = ConcurrentHashMap.newKeySet();
    private static final int MAX_TEXTO = 60;

    private static Seleccion sel(ServerPlayerEntity p) {
        return SELECCION.computeIfAbsent(p.getUuid(), k -> new Seleccion());
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("nutria")
                        .then(CommandManager.literal("letrero").executes(NutriaCommands::cmdLetrero)
                                .then(CommandManager.literal("parar").executes(NutriaCommands::cmdParar)))
                        .then(CommandManager.literal("menu").executes(NutriaCommands::cmdMenu))));

        // El mensaje del chat se "come": no lo ve nadie, se vuelve letrero.
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> {
            if (!MODO_LETRERO.contains(sender.getUuid())) return true; // modo apagado: chat normal
            String texto = message.getSignedContent();
            Seleccion s = sel(sender);
            s.texto = recortar(texto);
            darLetrero(sender, s);
            return false;
        });
    }

    private static int cmdLetrero(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity p = ctx.getSource().getPlayer();
        if (p == null) return 0;
        MODO_LETRERO.add(p.getUuid());
        p.sendMessage(Text.literal("Modo letrero ACTIVADO: todo lo que escribas se vuelve letrero. Para salir: /nutria letrero parar")
                .formatted(Formatting.GREEN), false);
        return 1;
    }

    private static int cmdParar(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity p = ctx.getSource().getPlayer();
        if (p == null) return 0;
        boolean estaba = MODO_LETRERO.remove(p.getUuid());
        p.sendMessage(Text.literal(estaba ? "Modo letrero DESACTIVADO: el chat vuelve a ser normal."
                : "El modo letrero ya estaba apagado.").formatted(Formatting.YELLOW), false);
        return 1;
    }

    private static int cmdMenu(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity p = ctx.getSource().getPlayer();
        if (p == null) return 0;
        Seleccion s = sel(p);
        p.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, inv, player) -> new NutriaMenu(syncId, inv, NutriaMenu.crearInventario(), s),
                Text.literal("Letreros de Nutria")));
        return 1;
    }

    private static String recortar(String t) {
        t = t == null ? "" : t.trim();
        return t.length() > MAX_TEXTO ? t.substring(0, MAX_TEXTO) : t;
    }

    /** Parte el texto en 4 lineas de ~15 caracteres sin cortar palabras. */
    static List<String> envolver(String texto) {
        List<String> lineas = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        for (String palabra : texto.split("\\s+")) {
            while (palabra.length() > 15) { // palabra muy larga: se corta
                if (actual.length() > 0) { lineas.add(actual.toString()); actual.setLength(0); }
                lineas.add(palabra.substring(0, 15));
                palabra = palabra.substring(15);
            }
            if (actual.length() > 0 && actual.length() + 1 + palabra.length() > 15) {
                lineas.add(actual.toString()); actual.setLength(0);
            }
            if (actual.length() > 0) actual.append(' ');
            actual.append(palabra);
        }
        if (actual.length() > 0) lineas.add(actual.toString());
        while (lineas.size() < 4) lineas.add("");
        return lineas.subList(0, 4);
    }

    public static void darLetrero(ServerPlayerEntity player, Seleccion s) {
        Item item = NutriaMenu.LETREROS[Math.max(0, Math.min(s.tipo, NutriaMenu.LETREROS.length - 1))];
        DyeColor color = DyeColor.byId(s.tinte);
        ItemStack stack = new ItemStack(item);

        NbtCompound be = new NbtCompound();
        be.putString("id", "minecraft:sign");
        NbtCompound front = new NbtCompound();
        NbtList msgs = new NbtList();
        for (String linea : envolver(s.texto)) {
            msgs.add(NbtString.of(new JsonPrimitive(linea).toString())); // cada linea es texto JSON
        }
        front.put("messages", msgs);
        front.putString("color", color.getName());
        front.putBoolean("has_glowing_text", true); // tinte brillante
        be.put("front_text", front);
        stack.set(DataComponentTypes.BLOCK_ENTITY_DATA, NbtComponent.of(be));
        stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Letrero: " + s.texto).formatted(Formatting.YELLOW));

        player.getInventory().offerOrDrop(stack);
        player.sendMessage(Text.literal("Letrero listo en tu inventario").formatted(Formatting.GREEN), true);
    }
}
