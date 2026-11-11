package com.silla;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class BikeRenderer extends MobEntityRenderer<BikeEntity, BikeModel> {
    private static final Identifier[] TEXTURES = new Identifier[16];

    static {
        for (DyeColor c : ModEntities.COLORES) {
            TEXTURES[c.getId()] = SillaMod.id("textures/entity/bici_" + c.getName() + ".png");
        }
    }

    public BikeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BikeModel(ctx.getPart(BikeModel.LAYER)), 0.45F);
    }

    @Override
    public Identifier getTexture(BikeEntity entity) {
        Identifier t = TEXTURES[entity.getColor()];
        return t != null ? t : TEXTURES[DyeColor.RED.getId()];
    }

    @Override
    public void render(BikeEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        this.model.roll = entity.wheelRoll(tickDelta);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
