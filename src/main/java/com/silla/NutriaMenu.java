package com.silla;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;

/**
 * Menu de /nutria menu (solo servidor: el cliente lo ve como un cofre normal).
 * Fila 1-2: tipos de letrero. Fila 3-4: los 11 tintes. Boton verde: dar el letrero.
 */
public class NutriaMenu extends GenericContainerScreenHandler {
    static final Item[] LETREROS = {Items.OAK_SIGN, Items.SPRUCE_SIGN, Items.BIRCH_SIGN, Items.JUNGLE_SIGN,
            Items.ACACIA_SIGN, Items.DARK_OAK_SIGN, Items.MANGROVE_SIGN, Items.CHERRY_SIGN, Items.BAMBOO_SIGN,
            Items.CRIMSON_SIGN, Items.WARPED_SIGN};
    private static final int DYE_START = 18;
    private static final int BOTON = 40;

    private final SimpleInventory menu;
    private final NutriaCommands.Seleccion sel;

    public NutriaMenu(int syncId, PlayerInventory playerInv, SimpleInventory menu, NutriaCommands.Seleccion sel) {
        super(ScreenHandlerType.GENERIC_9X5, syncId, playerInv, menu, 5);
        this.menu = menu;
        this.sel = sel;
        refrescar();
    }

    public static SimpleInventory crearInventario() {
        return new SimpleInventory(45);
    }

    private void refrescar() {
        for (int i = 0; i < LETREROS.length; i++) {
            ItemStack s = new ItemStack(LETREROS[i]);
            if (i == sel.tipo) s.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            menu.setStack(i, s);
        }
        DyeColor[] cols = ModEntities.COLORES;
        for (int i = 0; i < cols.length; i++) {
            ItemStack s = new ItemStack(DyeItem.byColor(cols[i]));
            if (cols[i].getId() == sel.tinte) s.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            menu.setStack(DYE_START + i, s);
        }
        ItemStack b = new ItemStack(Items.EMERALD);
        b.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Dar letrero (brillante)").formatted(Formatting.GREEN, Formatting.BOLD));
        menu.setStack(BOTON, b);
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        if (actionType == SlotActionType.QUICK_CRAFT) return;
        if (slotIndex >= 0 && slotIndex < 45) {
            if (actionType == SlotActionType.PICKUP || actionType == SlotActionType.QUICK_MOVE) {
                if (slotIndex < LETREROS.length) {
                    sel.tipo = slotIndex;
                } else if (slotIndex >= DYE_START && slotIndex < DYE_START + ModEntities.COLORES.length) {
                    sel.tinte = ModEntities.COLORES[slotIndex - DYE_START].getId();
                } else if (slotIndex == BOTON && player instanceof ServerPlayerEntity sp) {
                    NutriaCommands.darLetrero(sp, sel);
                }
                refrescar();
            }
            return; // nunca se puede sacar nada del menu
        }
        super.onSlotClick(slotIndex, button, actionType, player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
