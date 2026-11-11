package com.silla;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/** Modelo de la silla de ruedas (generado por tools/gen_all.py). */
public class WheelchairModel extends EntityModel<WheelchairEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of("silla", "silla_de_ruedas"), "main");
    private final ModelPart root;
    private final ModelPart wheelA, wheelB, wheelC, wheelD;
    /** Giro de las ruedas (radianes). Lo fija el renderer antes de dibujar. */
    public float roll;

    public WheelchairModel(ModelPart root) {
        this.root = root;
        this.wheelA = root.getChild("rueda_izq");
        this.wheelB = root.getChild("rueda_der");
        this.wheelC = root.getChild("ruedita_izq");
        this.wheelD = root.getChild("ruedita_der");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("bb_main", ModelPartBuilder.create()
                .uv(0, 21).cuboid(-6F, -11F, -5F, 12F, 1F, 11F)
                .uv(14, 33).cuboid(-5.5F, -12F, -4.5F, 11F, 1F, 9F)
                .uv(16, 0).cuboid(-6F, -26F, 4.5F, 12F, 14F, 1.5F)
                .uv(46, 21).cuboid(-5F, -24F, 3.5F, 10F, 11F, 1F)
                .uv(0, 0).cuboid(-7.5F, -30F, 5F, 1.5F, 19F, 1.5F)
                .uv(102, 33).cuboid(-7.5F, -31F, 6.5F, 1.5F, 1.5F, 5F)
                .uv(8, 0).cuboid(6F, -30F, 5F, 1.5F, 19F, 1.5F)
                .uv(0, 44).cuboid(6F, -31F, 6.5F, 1.5F, 1.5F, 5F)
                .uv(68, 21).cuboid(-8F, -19F, -4F, 2F, 1.5F, 10F)
                .uv(54, 33).cuboid(-7.75F, -17.5F, -4F, 1.5F, 8F, 1.5F)
                .uv(92, 21).cuboid(6F, -19F, -4F, 2F, 1.5F, 10F)
                .uv(62, 33).cuboid(6.25F, -17.5F, -4F, 1.5F, 8F, 1.5F)
                .uv(44, 0).cuboid(-7.5F, -10F, -5.5F, 1.5F, 1.5F, 12F)
                .uv(116, 21).cuboid(-7.5F, -12F, 0.5F, 1.5F, 8F, 3F)
                .uv(72, 0).cuboid(6F, -10F, -5.5F, 1.5F, 1.5F, 12F)
                .uv(0, 33).cuboid(6F, -12F, 0.5F, 1.5F, 8F, 3F)
                .uv(0, 51).cuboid(-6F, -8.5F, -3F, 12F, 1F, 1.5F)
                .uv(28, 51).cuboid(-8F, -7.5F, 1.4F, 16F, 1F, 1.2F)
                .uv(70, 33).cuboid(-7.25F, -9F, -8.75F, 1.5F, 6.5F, 1.5F)
                .uv(78, 33).cuboid(5.75F, -9F, -8.75F, 1.5F, 6.5F, 1.5F)
                .uv(14, 44).cuboid(-7F, -3.5F, -12F, 5F, 1F, 5F)
                .uv(34, 44).cuboid(2F, -3.5F, -12F, 5F, 1F, 5F),
                ModelTransform.pivot(0F, 24F, 0F));
        ModelPartData rueda_izq = root.addChild("rueda_izq", ModelPartBuilder.create(), ModelTransform.pivot(-9F, 17F, 2F));
        rueda_izq.addChild("tire0", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_izq.addChild("tire1", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(0.3927F, 0F, 0F));
        rueda_izq.addChild("tire2", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_izq.addChild("tire3", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(1.1781F, 0F, 0F));
        rueda_izq.addChild("tire4", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_izq.addChild("tire5", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(1.9635F, 0F, 0F));
        rueda_izq.addChild("tire6", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_izq.addChild("tire7", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(2.7489F, 0F, 0F));
        rueda_izq.addChild("tire8", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(3.1416F, 0F, 0F));
        rueda_izq.addChild("tire9", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(3.5343F, 0F, 0F));
        rueda_izq.addChild("tire10", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(3.927F, 0F, 0F));
        rueda_izq.addChild("tire11", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(4.3197F, 0F, 0F));
        rueda_izq.addChild("tire12", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(4.7124F, 0F, 0F));
        rueda_izq.addChild("tire13", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(5.1051F, 0F, 0F));
        rueda_izq.addChild("tire14", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(5.4978F, 0F, 0F));
        rueda_izq.addChild("tire15", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(5.8905F, 0F, 0F));
        rueda_izq.addChild("rim0", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_izq.addChild("rim1", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(0.3927F, 0F, 0F));
        rueda_izq.addChild("rim2", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_izq.addChild("rim3", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(1.1781F, 0F, 0F));
        rueda_izq.addChild("rim4", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_izq.addChild("rim5", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(1.9635F, 0F, 0F));
        rueda_izq.addChild("rim6", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_izq.addChild("rim7", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(2.7489F, 0F, 0F));
        rueda_izq.addChild("rim8", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(3.1416F, 0F, 0F));
        rueda_izq.addChild("rim9", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(3.5343F, 0F, 0F));
        rueda_izq.addChild("rim10", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(3.927F, 0F, 0F));
        rueda_izq.addChild("rim11", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(4.3197F, 0F, 0F));
        rueda_izq.addChild("rim12", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(4.7124F, 0F, 0F));
        rueda_izq.addChild("rim13", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(5.1051F, 0F, 0F));
        rueda_izq.addChild("rim14", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(5.4978F, 0F, 0F));
        rueda_izq.addChild("rim15", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(5.8905F, 0F, 0F));
        rueda_izq.addChild("push0", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_izq.addChild("push1", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(0.3927F, 0F, 0F));
        rueda_izq.addChild("push2", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_izq.addChild("push3", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(1.1781F, 0F, 0F));
        rueda_izq.addChild("push4", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_izq.addChild("push5", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(1.9635F, 0F, 0F));
        rueda_izq.addChild("push6", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_izq.addChild("push7", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(2.7489F, 0F, 0F));
        rueda_izq.addChild("push8", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(3.1416F, 0F, 0F));
        rueda_izq.addChild("push9", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(3.5343F, 0F, 0F));
        rueda_izq.addChild("push10", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(3.927F, 0F, 0F));
        rueda_izq.addChild("push11", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(4.3197F, 0F, 0F));
        rueda_izq.addChild("push12", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(4.7124F, 0F, 0F));
        rueda_izq.addChild("push13", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(5.1051F, 0F, 0F));
        rueda_izq.addChild("push14", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(5.4978F, 0F, 0F));
        rueda_izq.addChild("push15", ModelPartBuilder.create().uv(80, 44).cuboid(-3.4F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(5.8905F, 0F, 0F));
        rueda_izq.addChild("spoke0", ModelPartBuilder.create().uv(10, 33).cuboid(-0.45F, -5F, -0.3F, 0.9F, 10F, 0.6F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_izq.addChild("spoke1", ModelPartBuilder.create().uv(10, 33).cuboid(-0.45F, -5F, -0.3F, 0.9F, 10F, 0.6F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_izq.addChild("spoke2", ModelPartBuilder.create().uv(10, 33).cuboid(-0.45F, -5F, -0.3F, 0.9F, 10F, 0.6F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_izq.addChild("spoke3", ModelPartBuilder.create().uv(10, 33).cuboid(-0.45F, -5F, -0.3F, 0.9F, 10F, 0.6F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_izq.addChild("cubo", ModelPartBuilder.create().uv(86, 33).cuboid(-1.8F, -1.8F, -1.8F, 3.6F, 3.6F, 3.6F), ModelTransform.NONE);
        ModelPartData rueda_der = root.addChild("rueda_der", ModelPartBuilder.create(), ModelTransform.pivot(9F, 17F, 2F));
        rueda_der.addChild("tire0", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_der.addChild("tire1", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(0.3927F, 0F, 0F));
        rueda_der.addChild("tire2", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_der.addChild("tire3", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(1.1781F, 0F, 0F));
        rueda_der.addChild("tire4", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_der.addChild("tire5", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(1.9635F, 0F, 0F));
        rueda_der.addChild("tire6", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_der.addChild("tire7", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(2.7489F, 0F, 0F));
        rueda_der.addChild("tire8", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(3.1416F, 0F, 0F));
        rueda_der.addChild("tire9", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(3.5343F, 0F, 0F));
        rueda_der.addChild("tire10", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(3.927F, 0F, 0F));
        rueda_der.addChild("tire11", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(4.3197F, 0F, 0F));
        rueda_der.addChild("tire12", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(4.7124F, 0F, 0F));
        rueda_der.addChild("tire13", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(5.1051F, 0F, 0F));
        rueda_der.addChild("tire14", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(5.4978F, 0F, 0F));
        rueda_der.addChild("tire15", ModelPartBuilder.create().uv(54, 44).cuboid(-1.3F, -7F, -1.4F, 2.6F, 1.7F, 2.9F), ModelTransform.rotation(5.8905F, 0F, 0F));
        rueda_der.addChild("rim0", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_der.addChild("rim1", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(0.3927F, 0F, 0F));
        rueda_der.addChild("rim2", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_der.addChild("rim3", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(1.1781F, 0F, 0F));
        rueda_der.addChild("rim4", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_der.addChild("rim5", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(1.9635F, 0F, 0F));
        rueda_der.addChild("rim6", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_der.addChild("rim7", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(2.7489F, 0F, 0F));
        rueda_der.addChild("rim8", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(3.1416F, 0F, 0F));
        rueda_der.addChild("rim9", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(3.5343F, 0F, 0F));
        rueda_der.addChild("rim10", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(3.927F, 0F, 0F));
        rueda_der.addChild("rim11", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(4.3197F, 0F, 0F));
        rueda_der.addChild("rim12", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(4.7124F, 0F, 0F));
        rueda_der.addChild("rim13", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(5.1051F, 0F, 0F));
        rueda_der.addChild("rim14", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(5.4978F, 0F, 0F));
        rueda_der.addChild("rim15", ModelPartBuilder.create().uv(66, 44).cuboid(-1.6F, -5.3F, -1F, 3.2F, 0.8F, 2.1F), ModelTransform.rotation(5.8905F, 0F, 0F));
        rueda_der.addChild("push0", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_der.addChild("push1", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(0.3927F, 0F, 0F));
        rueda_der.addChild("push2", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_der.addChild("push3", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(1.1781F, 0F, 0F));
        rueda_der.addChild("push4", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_der.addChild("push5", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(1.9635F, 0F, 0F));
        rueda_der.addChild("push6", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_der.addChild("push7", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(2.7489F, 0F, 0F));
        rueda_der.addChild("push8", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(3.1416F, 0F, 0F));
        rueda_der.addChild("push9", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(3.5343F, 0F, 0F));
        rueda_der.addChild("push10", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(3.927F, 0F, 0F));
        rueda_der.addChild("push11", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(4.3197F, 0F, 0F));
        rueda_der.addChild("push12", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(4.7124F, 0F, 0F));
        rueda_der.addChild("push13", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(5.1051F, 0F, 0F));
        rueda_der.addChild("push14", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(5.4978F, 0F, 0F));
        rueda_der.addChild("push15", ModelPartBuilder.create().uv(88, 44).cuboid(2.6F, -5.9F, -1.1F, 0.8F, 0.7F, 2.4F), ModelTransform.rotation(5.8905F, 0F, 0F));
        rueda_der.addChild("spoke0", ModelPartBuilder.create().uv(10, 33).cuboid(-0.45F, -5F, -0.3F, 0.9F, 10F, 0.6F), ModelTransform.rotation(0F, 0F, 0F));
        rueda_der.addChild("spoke1", ModelPartBuilder.create().uv(10, 33).cuboid(-0.45F, -5F, -0.3F, 0.9F, 10F, 0.6F), ModelTransform.rotation(0.7854F, 0F, 0F));
        rueda_der.addChild("spoke2", ModelPartBuilder.create().uv(10, 33).cuboid(-0.45F, -5F, -0.3F, 0.9F, 10F, 0.6F), ModelTransform.rotation(1.5708F, 0F, 0F));
        rueda_der.addChild("spoke3", ModelPartBuilder.create().uv(10, 33).cuboid(-0.45F, -5F, -0.3F, 0.9F, 10F, 0.6F), ModelTransform.rotation(2.3562F, 0F, 0F));
        rueda_der.addChild("cubo", ModelPartBuilder.create().uv(86, 33).cuboid(-1.8F, -1.8F, -1.8F, 3.6F, 3.6F, 3.6F), ModelTransform.NONE);
        ModelPartData ruedita_izq = root.addChild("ruedita_izq", ModelPartBuilder.create(), ModelTransform.pivot(-6.5F, 21.5F, -8F));
        ruedita_izq.addChild("t0", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(0F, 0F, 0F));
        ruedita_izq.addChild("t1", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(0.7854F, 0F, 0F));
        ruedita_izq.addChild("t2", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(1.5708F, 0F, 0F));
        ruedita_izq.addChild("t3", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(2.3562F, 0F, 0F));
        ruedita_izq.addChild("t4", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(3.1416F, 0F, 0F));
        ruedita_izq.addChild("t5", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(3.927F, 0F, 0F));
        ruedita_izq.addChild("t6", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(4.7124F, 0F, 0F));
        ruedita_izq.addChild("t7", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(5.4978F, 0F, 0F));
        ruedita_izq.addChild("cubo", ModelPartBuilder.create().uv(106, 44).cuboid(-0.9F, -0.9F, -0.9F, 1.8F, 1.8F, 1.8F), ModelTransform.NONE);
        ModelPartData ruedita_der = root.addChild("ruedita_der", ModelPartBuilder.create(), ModelTransform.pivot(6.5F, 21.5F, -8F));
        ruedita_der.addChild("t0", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(0F, 0F, 0F));
        ruedita_der.addChild("t1", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(0.7854F, 0F, 0F));
        ruedita_der.addChild("t2", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(1.5708F, 0F, 0F));
        ruedita_der.addChild("t3", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(2.3562F, 0F, 0F));
        ruedita_der.addChild("t4", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(3.1416F, 0F, 0F));
        ruedita_der.addChild("t5", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(3.927F, 0F, 0F));
        ruedita_der.addChild("t6", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(4.7124F, 0F, 0F));
        ruedita_der.addChild("t7", ModelPartBuilder.create().uv(96, 44).cuboid(-1F, -3F, -1F, 2F, 0.7F, 2.1F), ModelTransform.rotation(5.4978F, 0F, 0F));
        ruedita_der.addChild("cubo", ModelPartBuilder.create().uv(106, 44).cuboid(-0.9F, -0.9F, -0.9F, 1.8F, 1.8F, 1.8F), ModelTransform.NONE);

        return TexturedModelData.of(data, 128, 128);
    }

    @Override
    public void setAngles(WheelchairEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        wheelA.pitch = roll;
        wheelB.pitch = roll;
        wheelC.pitch = roll * 2.8F; // ruedas chicas: radio 2.5 vs 7
        wheelD.pitch = roll * 2.8F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay, color);
    }
}
