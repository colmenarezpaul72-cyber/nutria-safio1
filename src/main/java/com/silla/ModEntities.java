package com.silla;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec3d;

public final class ModEntities {
    private ModEntities() {}

    /** Mismos ids que la version anterior, asi los mundos existentes siguen funcionando. */
    public static final EntityType<WheelchairEntity> WHEELCHAIR = Registry.register(
            Registries.ENTITY_TYPE, SillaMod.id("silla_de_ruedas"),
            EntityType.Builder.<WheelchairEntity>create(WheelchairEntity::new, SpawnGroup.MISC)
                    .dimensions(0.8F, 1.1F)
                    .passengerAttachments(WheelchairEntity.SEAT_ATTACH)
                    .maxTrackingRange(10)
                    .build("silla_de_ruedas"));

    /**
     * Bici: 2 asientos (x, y, z en bloques; +z = hacia adelante). El segundo solo se usa en la roja.
     * Si el conductor se ve muy alto/bajo, cambia la "y" (0.8 y 0.6).
     */
    public static final EntityType<BikeEntity> BIKE = Registry.register(
            Registries.ENTITY_TYPE, SillaMod.id("bici"),
            EntityType.Builder.<BikeEntity>create(BikeEntity::new, SpawnGroup.MISC)
                    .dimensions(0.8F, 1.0F)
                    .passengerAttachments(new Vec3d(0.0, 0.8, 0.1), new Vec3d(0.0, 0.6, -0.5))
                    .maxTrackingRange(10)
                    .build("bici"));

    /** Autobus: 12 asientos. Orden: conductor (izq.), acompanante, luego 5 sofas dobles (+z = adelante). */
    public static final EntityType<BusEntity> BUS = Registry.register(
            Registries.ENTITY_TYPE, SillaMod.id("autobus"),
            EntityType.Builder.<BusEntity>create(BusEntity::new, SpawnGroup.MISC)
                    .dimensions(2.6F, 2.9F)
                    .passengerAttachments(busSeats())
                    .maxTrackingRange(16)
                    .build("autobus"));

    private static Vec3d[] busSeats() {
        double y = 0.6;
        double[] rowZ = {0.625, 0.0, -0.625, -1.25, -1.875};
        Vec3d[] seats = new Vec3d[2 + rowZ.length * 2];
        seats[0] = new Vec3d(0.56, y, 1.41);   // conductor
        seats[1] = new Vec3d(-0.56, y, 1.41);  // acompanante
        for (int i = 0; i < rowZ.length; i++) {
            seats[2 + i * 2] = new Vec3d(0.5, y, rowZ[i]);
            seats[3 + i * 2] = new Vec3d(-0.5, y, rowZ[i]);
        }
        return seats;
    }

    public static final Item WHEELCHAIR_EGG = Registry.register(
            Registries.ITEM, SillaMod.id("silla_de_ruedas_spawn_egg"),
            new SpawnEggItem(WHEELCHAIR, 12107460, 9116186, new Item.Settings()));

    public static final Item TABLET = Registry.register(
            Registries.ITEM, SillaMod.id("tablet"),
            new TabletItem(new Item.Settings().maxCount(1)));

    public static final Item BUS_ITEM = Registry.register(
            Registries.ITEM, SillaMod.id("autobus"), new BusItem(new Item.Settings().maxCount(1)));

    /** Colores disponibles (bicis y menu de letreros). */
    public static final DyeColor[] COLORES = {DyeColor.WHITE, DyeColor.ORANGE, DyeColor.LIGHT_BLUE, DyeColor.YELLOW,
            DyeColor.LIME, DyeColor.PINK, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE, DyeColor.RED, DyeColor.BLACK};

    /** Un item de bici por color (indice = DyeColor.getId(); los colores que no existen quedan en null). */
    public static final Item[] BIKE_ITEMS = new Item[16];
    static {
        for (DyeColor c : COLORES) {
            BIKE_ITEMS[c.getId()] = Registry.register(Registries.ITEM, SillaMod.id("bici_" + c.getName()),
                    new BikeItem(c.getId(), new Item.Settings().maxCount(1)));
        }
    }

    /** Item de la bici de ese color (si el color no existe, la roja). */
    public static Item bikeItem(int color) {
        Item i = color >= 0 && color < 16 ? BIKE_ITEMS[color] : null;
        return i != null ? i : BIKE_ITEMS[DyeColor.RED.getId()];
    }

    /** Categoria "Nutria Mods": aqui estan todos los objetos del mod. */
    public static final ItemGroup TAB = Registry.register(Registries.ITEM_GROUP, SillaMod.id("nutria_mods"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(TABLET))
                    .displayName(Text.translatable("itemGroup.silla.nutria_mods"))
                    .entries((ctx, entries) -> {
                        entries.add(TABLET);
                        entries.add(WHEELCHAIR_EGG);
                        entries.add(BUS_ITEM);
                        for (DyeColor c : COLORES) entries.add(BIKE_ITEMS[c.getId()]);
                    })
                    .build());

    public static void init() {
        FabricDefaultAttributeRegistry.register(WHEELCHAIR, WheelchairEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BIKE, BikeEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BUS, BusEntity.createAttributes());
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.add(WHEELCHAIR_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(e -> e.add(TABLET));
    }
}
