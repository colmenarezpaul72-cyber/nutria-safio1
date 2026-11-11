package com.silla;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BusRenderer extends MobEntityRenderer<BusEntity, BusModel> {
    private static final Identifier TEXTURE = SillaMod.id("textures/entity/autobus.png");

    public BusRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BusModel(ctx.getPart(BusModel.LAYER)), 1.6F);
    }

    @Override
    public Identifier getTexture(BusEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BusEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        this.model.roll = entity.wheelRoll(tickDelta);
        this.model.spin = entity.fanSpin(tickDelta);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
