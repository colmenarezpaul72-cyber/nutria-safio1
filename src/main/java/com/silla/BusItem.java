package com.silla;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Item del autobus: click derecho en el suelo para ponerlo. */
public class BusItem extends Item {
    public BusItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        World world = ctx.getWorld();
        if (world.isClient) return ActionResult.SUCCESS;
        BlockPos pos = ctx.getBlockPos().offset(ctx.getSide());
        BusEntity bus = ModEntities.BUS.create(world);
        if (bus == null) return ActionResult.FAIL;
        PlayerEntity player = ctx.getPlayer();
        bus.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                player != null ? player.getYaw() : 0.0F, 0.0F);
        world.spawnEntity(bus);
        if (player == null || !player.isCreative()) ctx.getStack().decrement(1);
        return ActionResult.CONSUME;
    }
}
