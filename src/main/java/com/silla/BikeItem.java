package com.silla;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Item de bicicleta: click derecho en el suelo para ponerla. */
public class BikeItem extends Item {
    private final int color;

    public BikeItem(int color, Settings settings) {
        super(settings);
        this.color = color;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        World world = ctx.getWorld();
        if (world.isClient) return ActionResult.SUCCESS;
        BlockPos pos = ctx.getBlockPos().offset(ctx.getSide());
        BikeEntity bike = ModEntities.BIKE.create(world);
        if (bike == null) return ActionResult.FAIL;
        PlayerEntity player = ctx.getPlayer();
        bike.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                player != null ? player.getYaw() : 0.0F, 0.0F);
        bike.setColor(color);
        world.spawnEntity(bike);
        if (player == null || !player.isCreative()) ctx.getStack().decrement(1);
        return ActionResult.CONSUME;
    }
}
