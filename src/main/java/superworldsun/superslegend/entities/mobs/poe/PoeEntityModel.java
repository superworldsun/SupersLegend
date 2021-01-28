package superworldsun.superslegend.entities.mobs.poe;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import superworldsun.superslegend.ModelingAPI.helpers.AdvancedEntityModel;
import superworldsun.superslegend.ModelingAPI.helpers.AdvancedModelBox;

public class PoeEntityModel extends AdvancedEntityModel<PoeEntity> {

    private final AdvancedModelBox WholeBody;
    private final AdvancedModelBox Head;
    private final AdvancedModelBox LeftEye_r1;
    private final AdvancedModelBox RightEye_r1;
    private final AdvancedModelBox nose_r1;
    private final AdvancedModelBox Hood;
    private final AdvancedModelBox cloak;
    private final AdvancedModelBox LeftArm;
    private final AdvancedModelBox LeftInnerArm;
    private final AdvancedModelBox LeftOuterArm;
    private final AdvancedModelBox LeftHand;
    private final AdvancedModelBox thirdfinger_r1;
    private final AdvancedModelBox firstfinger_r1;
    private final AdvancedModelBox RightArm;
    private final AdvancedModelBox RightInnerArm;
    private final AdvancedModelBox RightOuterArm;
    private final AdvancedModelBox RightHand;
    private final AdvancedModelBox firstfinger_r2;
    private final AdvancedModelBox thirdfinger_r2;
    private final AdvancedModelBox Lantern;


    public AdvancedModelBox body;

    public PoeEntityModel() {
        body = new AdvancedModelBox(this, 0, 0);
        body.addBox(-10, 22, -10, 20, 20, 20);

        textureWidth = 64;
        textureHeight = 64;

        WholeBody = new AdvancedModelBox(this);
        WholeBody.setRotationPoint(0.0F, 24.0F, 0.0F);


        Head = new AdvancedModelBox(this);
        Head.setRotationPoint(0.0F, -17.0F, 0.0F);
        WholeBody.addChild(Head);
        Head.setTextureOffset(0, 10).addBox(-4.0F, -4.025F, -4.25F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        Head.setTextureOffset(16, 10).addBox(-4.0F, 4.0F, -4.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);

        LeftEye_r1 = new AdvancedModelBox(this);
        LeftEye_r1.setRotationPoint(1.75F, -0.225F, -4.275F);
        Head.addChild(LeftEye_r1);
        setRotationAngle(LeftEye_r1, 0.0F, 0.0F, -0.3927F);
        LeftEye_r1.setTextureOffset(0, 44).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        RightEye_r1 = new AdvancedModelBox(this);
        RightEye_r1.setRotationPoint(-1.75F, -0.225F, -4.275F);
        Head.addChild(RightEye_r1);
        setRotationAngle(RightEye_r1, 0.0F, 0.0F, 0.3927F);
        RightEye_r1.setTextureOffset(6, 44).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        nose_r1 = new AdvancedModelBox(this);
        nose_r1.setRotationPoint(0.0F, 1.6869F, -4.2036F);
        Head.addChild(nose_r1);
        setRotationAngle(nose_r1, -0.3927F, 0.0F, 0.0F);
        nose_r1.setTextureOffset(16, 35).addBox(-0.5F, -0.5381F, -0.3087F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        Hood = new AdvancedModelBox(this);
        Hood.setRotationPoint(0.0F, -17.1875F, -0.125F);
        WholeBody.addChild(Hood);
        Hood.setTextureOffset(20, 27).addBox(-4.5F, 2.1875F, -5.125F, 9.0F, 2.0F, 0.0F, 0.0F, false);
        Hood.setTextureOffset(12, 42).addBox(3.5F, -2.8125F, -5.125F, 1.0F, 5.0F, 0.0F, 0.0F, false);
        Hood.setTextureOffset(20, 20).addBox(4.5F, -4.8125F, -5.125F, 0.0F, 9.0F, 10.0F, 0.0F, false);
        Hood.setTextureOffset(38, 27).addBox(-4.5F, -4.8125F, -5.125F, 9.0F, 2.0F, 0.0F, 0.0F, false);
        Hood.setTextureOffset(14, 42).addBox(-4.5F, -2.8125F, -5.125F, 1.0F, 5.0F, 0.0F, 0.0F, false);
        Hood.setTextureOffset(28, 0).addBox(-4.5F, -4.8125F, 4.875F, 9.0F, 9.0F, 0.0F, 0.0F, false);
        Hood.setTextureOffset(0, 0).addBox(-4.5F, -4.8125F, -5.125F, 9.0F, 0.0F, 10.0F, 0.0F, false);
        Hood.setTextureOffset(0, 16).addBox(-4.5F, -4.8125F, -5.125F, 0.0F, 9.0F, 10.0F, 0.0F, false);

        cloak = new AdvancedModelBox(this);
        cloak.setRotationPoint(-0.85F, -9.0F, 8.0F);
        WholeBody.addChild(cloak);
        cloak.setTextureOffset(16, 32).addBox(5.0F, -4.0F, -11.95F, 0.0F, 7.0F, 8.0F, 0.0F, false);
        cloak.setTextureOffset(16, 32).addBox(-3.0F, -4.0F, -12.0F, 0.0F, 7.0F, 8.0F, 0.0F, false);
        cloak.setTextureOffset(40, 9).addBox(-3.0F, -4.0F, -11.975F, 8.0F, 7.0F, 0.0F, 0.0F, false);
        cloak.setTextureOffset(32, 39).addBox(-3.0F, -4.0F, -4.0F, 8.0F, 7.0F, 0.0F, 0.0F, false);

        LeftArm = new AdvancedModelBox(this);
        LeftArm.setRotationPoint(3.75F, -12.5F, 0.25F);
        WholeBody.addChild(LeftArm);


        LeftInnerArm = new AdvancedModelBox(this);
        LeftInnerArm.setRotationPoint(0.25F, 0.0F, -0.25F);
        LeftArm.addChild(LeftInnerArm);
        LeftInnerArm.setTextureOffset(0, 6).addBox(-0.25F, -0.5F, -0.25F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        LeftOuterArm = new AdvancedModelBox(this);
        LeftOuterArm.setRotationPoint(3.875F, 0.0F, 0.125F);
        LeftInnerArm.addChild(LeftOuterArm);
        LeftOuterArm.setTextureOffset(0, 4).addBox(-0.125F, -0.5F, -0.375F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        LeftHand = new AdvancedModelBox(this);
        LeftHand.setRotationPoint(3.375F, 0.25F, 0.125F);
        LeftOuterArm.addChild(LeftHand);
        LeftHand.setTextureOffset(6, 42).addBox(0.25F, -0.25F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        thirdfinger_r1 = new AdvancedModelBox(this);
        thirdfinger_r1.setRotationPoint(0.75F, 0.25F, 0.75F);
        LeftHand.addChild(thirdfinger_r1);
        setRotationAngle(thirdfinger_r1, 0.0F, -0.6545F, 0.0F);
        thirdfinger_r1.setTextureOffset(0, 42).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        firstfinger_r1 = new AdvancedModelBox(this);
        firstfinger_r1.setRotationPoint(1.5F, 0.25F, -1.35F);
        LeftHand.addChild(firstfinger_r1);
        setRotationAngle(firstfinger_r1, 0.0F, 0.7854F, 0.0F);
        firstfinger_r1.setTextureOffset(40, 37).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        RightArm = new AdvancedModelBox(this);
        RightArm.setRotationPoint(-3.25F, -12.575F, 0.0F);
        WholeBody.addChild(RightArm);
        setRotationAngle(RightArm, 0.0F, 3.1416F, 0.0F);


        RightInnerArm = new AdvancedModelBox(this);
        RightInnerArm.setRotationPoint(0.0F, 0.0375F, 0.0F);
        RightArm.addChild(RightInnerArm);
        RightInnerArm.setTextureOffset(0, 2).addBox(0.25F, -0.5375F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        RightOuterArm = new AdvancedModelBox(this);
        RightOuterArm.setRotationPoint(4.25F, 0.0F, 0.0F);
        RightInnerArm.addChild(RightOuterArm);
        RightOuterArm.setTextureOffset(0, 0).addBox(0.0F, -0.5375F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        RightHand = new AdvancedModelBox(this);
        RightHand.setRotationPoint(3.5F, 0.2125F, 0.0F);
        RightOuterArm.addChild(RightHand);
        RightHand.setTextureOffset(0, 15).addBox(0.25F, -0.25F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        firstfinger_r2 = new AdvancedModelBox(this);
        firstfinger_r2.setRotationPoint(1.0F, 0.25F, 0.5F);
        RightHand.addChild(firstfinger_r2);
        setRotationAngle(firstfinger_r2, 0.0F, -0.6545F, 0.0F);
        firstfinger_r2.setTextureOffset(0, 13).addBox(-1.0461F, -0.5F, -0.1495F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        thirdfinger_r2 = new AdvancedModelBox(this);
        thirdfinger_r2.setRotationPoint(1.75F, 0.25F, -1.6F);
        RightHand.addChild(thirdfinger_r2);
        setRotationAngle(thirdfinger_r2, 0.0F, 0.7854F, 0.0F);
        thirdfinger_r2.setTextureOffset(40, 16).addBox(-2.3536F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        Lantern = new AdvancedModelBox(this);
        Lantern.setRotationPoint(-8.0F, 12.4583F, -5.0F);
        Lantern.setTextureOffset(32, 18).addBox(-2.0F, 2.5417F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        Lantern.setTextureOffset(40, 33).addBox(-1.5F, 2.0417F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
        Lantern.setTextureOffset(40, 29).addBox(-1.5F, 6.7917F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
        Lantern.setTextureOffset(0, 8).addBox(-1.0F, 7.0417F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        Lantern.setTextureOffset(32, 18).addBox(-0.5F, 0.5417F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Lantern.setTextureOffset(0, 11).addBox(-1.5F, -0.4583F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);
    }
    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(body, WholeBody, Head, LeftEye_r1, RightEye_r1, nose_r1, Hood, cloak, LeftArm, LeftInnerArm, LeftOuterArm, LeftHand, thirdfinger_r1, firstfinger_r1, RightArm, RightInnerArm, RightOuterArm, RightHand, firstfinger_r2, thirdfinger_r2, Lantern);
    }


    @Override
    public void setRotationAngles(PoeEntity poeEntity, float v, float v1, float v2, float v3, float v4) {

    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        WholeBody.render(matrixStack, buffer, packedLight, packedOverlay);
        Lantern.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(body);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
