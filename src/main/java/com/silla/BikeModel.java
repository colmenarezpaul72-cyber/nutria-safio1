package com.silla;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/** Modelo de la bicicleta (generado por tools/gen_all.py). */
public class BikeModel extends EntityModel<BikeEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of("silla", "bici"), "main");
    private final ModelPart root;
    private final ModelPart wheelA, wheelB;
    /** Giro de las ruedas (radianes). Lo fija el renderer antes de dibujar. */
    public float roll;

    public BikeModel(ModelPart root) {
        this.root = root;
        this.wheelA = root.getChild("rueda_del");
        this.wheelB = root.getChild("rueda_tras");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("bb_main", ModelPartBuilder.create()
                .uv(64, 0).cuboid(-0.75F, -15F, -1.75F, 1.5F, 9F, 1.5F)
                .uv(72, 0).cuboid(-0.75F, -16F, -8.75F, 1.5F, 9F, 1.5F)
                .uv(80, 0).cuboid(-0.75F, -13.5F, -8.5F, 1.5F, 1.5F, 8.5F)
                .uv(102, 0).cuboid(-0.75F, -8.5F, -8F, 1.5F, 1.5F, 7.5F)
                .uv(4, 0).cuboid(-1.6F, -7F, -1F, 0.8F, 1F, 13F)
                .uv(32, 0).cuboid(0.8F, -7F, -1F, 0.8F, 1F, 13F)
                .uv(44, 16).cuboid(-1.9F, -10F, -9.6F, 0.8F, 5F, 1F)
                .uv(48, 16).cuboid(1.1F, -10F, -9.6F, 0.8F, 5F, 1F)
                .uv(8, 24).cuboid(-0.5F, -18F, -8.75F, 1F, 2F, 1F)
                .uv(16, 24).cuboid(-5F, -18.5F, -9F, 10F, 1F, 1F)
                .uv(72, 16).cuboid(-6.2F, -19F, -9.5F, 1.7F, 1.8F, 2F)
                .uv(80, 16).cuboid(4.5F, -19F, -9.5F, 1.7F, 1.8F, 2F)
                .uv(12, 24).cuboid(-0.4F, -16.5F, -1.4F, 0.8F, 2F, 0.8F)
                .uv(0, 16).cuboid(-1.8F, -17.2F, -4F, 3.6F, 1.2F, 6F)
                .uv(20, 16).cuboid(-2.5F, -12F, 5F, 5F, 0.8F, 6.5F)
                .uv(52, 16).cuboid(-0.4F, -11.2F, 6.5F, 0.8F, 5F, 0.8F)
                .uv(88, 16).cuboid(-3.6F, -9.4F, -2.1F, 2F, 0.7F, 2.2F)
                .uv(98, 16).cuboid(1.6F, -3.6F, -2.1F, 2F, 0.7F, 2.2F)
                .uv(108, 16).cuboid(-2F, -9F, -1.3F, 0.7F, 3F, 0.8F)
                .uv(112, 16).cuboid(1.3F, -6F, -1.3F, 0.7F, 3F, 0.8F)
                .uv(56, 16).cuboid(-1.2F, -13F, -13.5F, 2.4F, 0.8F, 5F),
                ModelTransform.pivot(0F, 24F, 0F));
        ModelPartData rueda_del = root.addChild("rueda_del", ModelPartBuilder.create(), ModelTransform.pivot(0F, 18F, -12F));
        rueda_del.addChild("tire0", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_del.addChild("tire1", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(0.3927F, 0F, 0F));
        rueda_del.addChild("tire2", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_del.addChild("tire3", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(1.1781F, 0F, 0F));
        rueda_del.addChild("tire4", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_del.addChild("tire5", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(1.9635F, 0F, 0F));
        rueda_del.addChild("tire6", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_del.addChild("tire7", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(2.7489F, 0F, 0F));
        rueda_del.addChild("tire8", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(3.1416F, 0F, 0F));
        rueda_del.addChild("tire9", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(3.5343F, 0F, 0F));
        rueda_del.addChild("tire10", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(3.927F, 0F, 0F));
        rueda_del.addChild("tire11", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(4.3197F, 0F, 0F));
        rueda_del.addChild("tire12", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(4.7124F, 0F, 0F));
        rueda_del.addChild("tire13", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(5.1051F, 0F, 0F));
        rueda_del.addChild("tire14", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(5.4978F, 0F, 0F));
        rueda_del.addChild("tire15", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(5.8905F, 0F, 0F));
        rueda_del.addChild("spoke0", ModelPartBuilder.create().uv(60, 0).cuboid(-0.25F, -5.1F, -0.2F, 0.5F, 10.2F, 0.4F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_del.addChild("spoke1", ModelPartBuilder.create().uv(60, 0).cuboid(-0.25F, -5.1F, -0.2F, 0.5F, 10.2F, 0.4F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_del.addChild("spoke2", ModelPartBuilder.create().uv(60, 0).cuboid(-0.25F, -5.1F, -0.2F, 0.5F, 10.2F, 0.4F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_del.addChild("spoke3", ModelPartBuilder.create().uv(60, 0).cuboid(-0.25F, -5.1F, -0.2F, 0.5F, 10.2F, 0.4F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_del.addChild("cubo", ModelPartBuilder.create().uv(0, 24).cuboid(-1F, -1F, -1F, 2F, 2F, 2F), ModelTransform.NONE);
        ModelPartData rueda_tras = root.addChild("rueda_tras", ModelPartBuilder.create(), ModelTransform.pivot(0F, 18F, 12F));
        rueda_tras.addChild("tire0", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_tras.addChild("tire1", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(0.3927F, 0F, 0F));
        rueda_tras.addChild("tire2", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_tras.addChild("tire3", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(1.1781F, 0F, 0F));
        rueda_tras.addChild("tire4", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_tras.addChild("tire5", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(1.9635F, 0F, 0F));
        rueda_tras.addChild("tire6", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_tras.addChild("tire7", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(2.7489F, 0F, 0F));
        rueda_tras.addChild("tire8", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(3.1416F, 0F, 0F));
        rueda_tras.addChild("tire9", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(3.5343F, 0F, 0F));
        rueda_tras.addChild("tire10", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(3.927F, 0F, 0F));
        rueda_tras.addChild("tire11", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(4.3197F, 0F, 0F));
        rueda_tras.addChild("tire12", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(4.7124F, 0F, 0F));
        rueda_tras.addChild("tire13", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(5.1051F, 0F, 0F));
        rueda_tras.addChild("tire14", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(5.4978F, 0F, 0F));
        rueda_tras.addChild("tire15", ModelPartBuilder.create().uv(116, 16).cuboid(-0.8F, -6F, -1.2F, 1.6F, 0.9F, 2.5F), ModelTransform.rotation(5.8905F, 0F, 0F));
        rueda_tras.addChild("spoke0", ModelPartBuilder.create().uv(60, 0).cuboid(-0.25F, -5.1F, -0.2F, 0.5F, 10.2F, 0.4F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_tras.addChild("spoke1", ModelPartBuilder.create().uv(60, 0).cuboid(-0.25F, -5.1F, -0.2F, 0.5F, 10.2F, 0.4F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_tras.addChild("spoke2", ModelPartBuilder.create().uv(60, 0).cuboid(-0.25F, -5.1F, -0.2F, 0.5F, 10.2F, 0.4F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_tras.addChild("spoke3", ModelPartBuilder.create().uv(60, 0).cuboid(-0.25F, -5.1F, -0.2F, 0.5F, 10.2F, 0.4F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_tras.addChild("cubo", ModelPartBuilder.create().uv(0, 24).cuboid(-1F, -1F, -1F, 2F, 2F, 2F), ModelTransform.NONE);
        root.addChild("tirante_izq", ModelPartBuilder.create().uv(0, 0).cuboid(-0.4F, 0F, -0.4F, 0.8F, 14.8F, 0.8F), ModelTransform.of(-1.3F, 11F, -1F, 1.0769F, 0F, 0F));
        root.addChild("tirante_der", ModelPartBuilder.create().uv(0, 0).cuboid(-0.4F, 0F, -0.4F, 0.8F, 14.8F, 0.8F), ModelTransform.of(1.3F, 11F, -1F, 1.0769F, 0F, 0F));

        return TexturedModelData.of(data, 128, 128);
    }

    @Override
    public void setAngles(BikeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        wheelA.pitch = roll;
        wheelB.pitch = roll;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay, color);
    }
}
