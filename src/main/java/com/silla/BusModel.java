package com.silla;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/** Modelo del autobus volador (generado por tools/gen_all.py). */
public class BusModel extends EntityModel<BusEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of("silla", "autobus"), "main");
    private final ModelPart root;
    private final ModelPart wheelA, wheelB, wheelC, wheelD;
    /** Giro de las ruedas (radianes). Lo fija el renderer antes de dibujar. */
    public float roll;
    /** Giro de las helices del techo (radianes). */
    public float spin;

    public BusModel(ModelPart root) {
        this.root = root;
        this.wheelA = root.getChild("rueda_a");
        this.wheelB = root.getChild("rueda_b");
        this.wheelC = root.getChild("rueda_c");
        this.wheelD = root.getChild("rueda_d");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("bb_main", ModelPartBuilder.create()
                .uv(0, 158).cuboid(-20F, -7F, -36F, 40F, 2F, 72F)
                .uv(0, 0).cuboid(-20F, -17F, -36F, 1.5F, 10F, 72F)
                .uv(0, 0).cuboid(18.5F, -17F, -36F, 1.5F, 10F, 72F)
                .uv(168, 232).cuboid(-18.5F, -17F, -36F, 37F, 10F, 1.5F)
                .uv(0, 247).cuboid(-18.5F, -17F, 34.5F, 37F, 10F, 1.5F)
                .uv(0, 82).cuboid(-21F, -44F, -37F, 42F, 2F, 74F)
                .uv(224, 158).cuboid(-20F, -42F, -35.5F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(-20F, -42F, -24F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(-20F, -42F, -12F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(-20F, -42F, 0F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(-20F, -42F, 12F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(-20F, -42F, 24F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(-20F, -42F, 34.5F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(18.5F, -42F, -35.5F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(18.5F, -42F, -24F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(18.5F, -42F, -12F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(18.5F, -42F, 0F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(18.5F, -42F, 12F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(18.5F, -42F, 24F, 1.5F, 25F, 1.5F)
                .uv(224, 158).cuboid(18.5F, -42F, 34.5F, 1.5F, 25F, 1.5F)
                .uv(76, 259).cuboid(-18F, -19F, -34.5F, 36F, 4F, 5F)
                .uv(222, 259).cuboid(-6F, -24F, -33F, 12F, 6F, 1F)
                .uv(84, 269).cuboid(-13F, -21F, -33F, 3F, 3F, 1F)
                .uv(92, 269).cuboid(-9.5F, -21F, -33F, 3F, 3F, 1F)
                .uv(100, 269).cuboid(6.5F, -21F, -33F, 3F, 3F, 1F)
                .uv(108, 269).cuboid(10F, -21F, -33F, 3F, 3F, 1F)
                .uv(158, 259).cuboid(-13F, -12F, -26F, 8F, 2F, 7F)
                .uv(174, 247).cuboid(-13F, -10F, -26F, 8F, 3F, 7F)
                .uv(56, 232).cuboid(-13F, -24F, -21F, 8F, 12F, 2F)
                .uv(158, 259).cuboid(5F, -12F, -26F, 8F, 2F, 7F)
                .uv(174, 247).cuboid(5F, -10F, -26F, 8F, 3F, 7F)
                .uv(56, 232).cuboid(5F, -24F, -21F, 8F, 12F, 2F)
                .uv(158, 269).cuboid(5F, -23F, -28F, 8F, 1F, 1F)
                .uv(176, 269).cuboid(5F, -16F, -28F, 8F, 1F, 1F)
                .uv(188, 259).cuboid(5F, -23F, -28F, 1F, 8F, 1F)
                .uv(192, 259).cuboid(12F, -23F, -28F, 1F, 8F, 1F)
                .uv(116, 269).cuboid(8.5F, -19F, -30F, 1F, 1F, 2.5F)
                .uv(0, 259).cuboid(-15F, -12F, -14F, 30F, 2F, 8F)
                .uv(98, 247).cuboid(-15F, -10F, -14F, 30F, 3F, 8F)
                .uv(76, 232).cuboid(-15F, -24F, -8F, 30F, 12F, 2F)
                .uv(78, 247).cuboid(-1F, -14F, -14F, 2F, 4F, 8F)
                .uv(0, 259).cuboid(-15F, -12F, -4F, 30F, 2F, 8F)
                .uv(98, 247).cuboid(-15F, -10F, -4F, 30F, 3F, 8F)
                .uv(76, 232).cuboid(-15F, -24F, 2F, 30F, 12F, 2F)
                .uv(78, 247).cuboid(-1F, -14F, -4F, 2F, 4F, 8F)
                .uv(0, 259).cuboid(-15F, -12F, 6F, 30F, 2F, 8F)
                .uv(98, 247).cuboid(-15F, -10F, 6F, 30F, 3F, 8F)
                .uv(76, 232).cuboid(-15F, -24F, 12F, 30F, 12F, 2F)
                .uv(78, 247).cuboid(-1F, -14F, 6F, 2F, 4F, 8F)
                .uv(0, 259).cuboid(-15F, -12F, 16F, 30F, 2F, 8F)
                .uv(98, 247).cuboid(-15F, -10F, 16F, 30F, 3F, 8F)
                .uv(76, 232).cuboid(-15F, -24F, 22F, 30F, 12F, 2F)
                .uv(78, 247).cuboid(-1F, -14F, 16F, 2F, 4F, 8F)
                .uv(0, 259).cuboid(-15F, -12F, 26F, 30F, 2F, 8F)
                .uv(98, 247).cuboid(-15F, -10F, 26F, 30F, 3F, 8F)
                .uv(76, 232).cuboid(-15F, -24F, 32F, 30F, 12F, 2F)
                .uv(78, 247).cuboid(-1F, -14F, 26F, 2F, 4F, 8F)
                .uv(0, 269).cuboid(-17F, -48F, -7F, 14F, 4F, 1.5F)
                .uv(0, 269).cuboid(-17F, -48F, 5.5F, 14F, 4F, 1.5F)
                .uv(0, 232).cuboid(-17F, -48F, -5.5F, 1.5F, 4F, 11F)
                .uv(0, 232).cuboid(-4.5F, -48F, -5.5F, 1.5F, 4F, 11F)
                .uv(0, 269).cuboid(3F, -48F, -7F, 14F, 4F, 1.5F)
                .uv(0, 269).cuboid(3F, -48F, 5.5F, 14F, 4F, 1.5F)
                .uv(0, 232).cuboid(3F, -48F, -5.5F, 1.5F, 4F, 11F)
                .uv(0, 232).cuboid(15.5F, -48F, -5.5F, 1.5F, 4F, 11F)
                .uv(26, 232).cuboid(-14.5F, -22.5F, 37F, 5F, 5F, 10F)
                .uv(32, 269).cuboid(-13.5F, -21.5F, 47F, 3F, 3F, 2.5F)
                .uv(196, 259).cuboid(-12.4F, -26.5F, 39F, 0.8F, 4F, 4F)
                .uv(44, 269).cuboid(-17F, -20.4F, 39F, 10F, 0.8F, 4F)
                .uv(124, 269).cuboid(-13F, -21.5F, 36F, 2F, 3F, 1F)
                .uv(26, 232).cuboid(9.5F, -22.5F, 37F, 5F, 5F, 10F)
                .uv(32, 269).cuboid(10.5F, -21.5F, 47F, 3F, 3F, 2.5F)
                .uv(196, 259).cuboid(11.6F, -26.5F, 39F, 0.8F, 4F, 4F)
                .uv(44, 269).cuboid(7F, -20.4F, 39F, 10F, 0.8F, 4F)
                .uv(124, 269).cuboid(11F, -21.5F, 36F, 2F, 3F, 1F),
                ModelTransform.pivot(0F, 24F, 0F));
        ModelPartData rueda_a = root.addChild("rueda_a", ModelPartBuilder.create(), ModelTransform.pivot(-20.5F, 18F, -24F));
        rueda_a.addChild("disc0", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_a.addChild("disc1", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_a.addChild("disc2", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_a.addChild("disc3", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_a.addChild("cubo", ModelPartBuilder.create().uv(206, 259).cuboid(-1.8F, -2F, -2F, 3.6F, 4F, 4F), ModelTransform.NONE);
        ModelPartData rueda_b = root.addChild("rueda_b", ModelPartBuilder.create(), ModelTransform.pivot(20.5F, 18F, -24F));
        rueda_b.addChild("disc0", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_b.addChild("disc1", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_b.addChild("disc2", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_b.addChild("disc3", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_b.addChild("cubo", ModelPartBuilder.create().uv(206, 259).cuboid(-1.8F, -2F, -2F, 3.6F, 4F, 4F), ModelTransform.NONE);
        ModelPartData rueda_c = root.addChild("rueda_c", ModelPartBuilder.create(), ModelTransform.pivot(-20.5F, 18F, 22F));
        rueda_c.addChild("disc0", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_c.addChild("disc1", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_c.addChild("disc2", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_c.addChild("disc3", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_c.addChild("cubo", ModelPartBuilder.create().uv(206, 259).cuboid(-1.8F, -2F, -2F, 3.6F, 4F, 4F), ModelTransform.NONE);
        ModelPartData rueda_d = root.addChild("rueda_d", ModelPartBuilder.create(), ModelTransform.pivot(20.5F, 18F, 22F));
        rueda_d.addChild("disc0", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_d.addChild("disc1", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_d.addChild("disc2", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_d.addChild("disc3", ModelPartBuilder.create().uv(232, 158).cuboid(3F, 12F, 4.97F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_d.addChild("cubo", ModelPartBuilder.create().uv(206, 259).cuboid(-1.8F, -2F, -2F, 3.6F, 4F, 4F), ModelTransform.NONE);
        ModelPartData helice_izq = root.addChild("helice_izq", ModelPartBuilder.create(), ModelTransform.pivot(-10F, -22F, 0F));
        helice_izq.addChild("hojas", ModelPartBuilder.create().uv(130, 269).cuboid(-6F, -0.5F, -1F, 12F, 1F, 2F).uv(140, 232).cuboid(-1F, -0.5F, -6F, 2F, 1F, 12F).uv(72, 269).cuboid(-1.5F, -1F, -1.5F, 3F, 2F, 3F), ModelTransform.NONE);
        ModelPartData helice_der = root.addChild("helice_der", ModelPartBuilder.create(), ModelTransform.pivot(10F, -22F, 0F));
        helice_der.addChild("hojas", ModelPartBuilder.create().uv(130, 269).cuboid(-6F, -0.5F, -1F, 12F, 1F, 2F).uv(140, 232).cuboid(-1F, -0.5F, -6F, 2F, 1F, 12F).uv(72, 269).cuboid(-1.5F, -1F, -1.5F, 3F, 2F, 3F), ModelTransform.NONE);

        return TexturedModelData.of(data, 256, 300);
    }

    @Override
    public void setAngles(BusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        wheelA.pitch = roll;
        wheelB.pitch = roll;
        wheelC.pitch = roll;
        wheelD.pitch = roll;
        root.getChild("helice_izq").yaw = spin;
        root.getChild("helice_der").yaw = -spin;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay, color);
    }
}
