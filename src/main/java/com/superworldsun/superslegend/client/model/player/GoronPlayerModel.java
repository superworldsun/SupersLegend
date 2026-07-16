package com.superworldsun.superslegend.client.model.player;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.superworldsun.superslegend.interfaces.IHandRenderer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

public class GoronPlayerModel extends PlayerModel<AbstractClientPlayer> implements IHandRenderer {
    public GoronPlayerModel(ModelPart root) {
        super(root, false);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, false);
        PartDefinition root = mesh.getRoot();

        // Vanilla overlay parts are unused by this model, replace them with empty parts
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 48).addBox(-5.0814F, -7.9139F, -4.6716F, 10.0F, 10.0F, 11.0F),
                PartPose.offset(0.0814F, -15.0861F, -0.3284F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(84, 78).addBox(4.0F, -10.0F, 9.0F, 5.0F, 10.0F, 4.0F),
                PartPose.offsetAndRotation(-6.5814F, 11.0861F, 1.3284F, 0.0873F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(62, 0).addBox(3.0F, -10.0F, 11.0F, 7.0F, 10.0F, 6.0F),
                PartPose.offsetAndRotation(-6.5814F, 11.0861F, 1.3284F, 0.6981F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r3", CubeListBuilder.create()
                .texOffs(42, 36).addBox(1.0F, 5.0F, 6.25F, 11.0F, 8.0F, 12.0F),
                PartPose.offsetAndRotation(-6.5814F, 11.0861F, 1.3284F, 1.9635F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r4", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-14.5F, -17.0F, -12.5F, 5.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(8.4186F, 11.0861F, 1.3284F, -0.2954F, 0.3687F, -0.0904F));
        head.addOrReplaceChild("cube_r5", CubeListBuilder.create()
                .texOffs(0, 28).addBox(7.5F, -17.0F, -12.0F, 5.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(-6.5814F, 11.0861F, 1.3284F, -0.2954F, -0.3687F, 0.0904F));
        head.addOrReplaceChild("cube_r6", CubeListBuilder.create()
                .texOffs(7, 0).addBox(3.5F, -17.0F, 9.25F, 2.0F, 0.0F, 3.0F)
                .texOffs(7, 3).addBox(7.5F, -17.0F, 9.25F, 2.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(-6.5814F, 10.3394F, 3.8026F, 1.0908F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r7", CubeListBuilder.create()
                .texOffs(8, 6).addBox(7.5F, -17.0F, -11.75F, 2.0F, 0.0F, 2.0F)
                .texOffs(8, 8).addBox(3.5F, -17.0F, -11.75F, 2.0F, 0.0F, 2.0F),
                PartPose.offsetAndRotation(-6.5814F, 11.0861F, 1.3284F, -0.2618F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-7.9901F, -5.0313F, -7.1092F, 16.0F, 13.0F, 15.0F)
                .texOffs(0, 28).addBox(-6.9901F, -12.0313F, -5.1092F, 14.0F, 7.0F, 13.0F)
                .texOffs(47, 0).addBox(-4.9901F, 7.9688F, -3.1092F, 10.0F, 4.0F, 0.0F)
                .texOffs(0, 9).addBox(-1.4901F, -10.0313F, -8.1092F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(-0.0099F, -0.9688F, 0.1092F));
        body.addOrReplaceChild("cube_r8", CubeListBuilder.create().mirror()
                .texOffs(0, 9).addBox(1.0F, 0.75F, -16.75F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(3.3246F, -11.5313F, -4.7411F, -3.1416F, 0.3927F, -3.1416F));
        body.addOrReplaceChild("cube_r9", CubeListBuilder.create()
                .texOffs(0, 9).addBox(-2.0F, 0.75F, -16.0F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(-1.3047F, -11.5313F, -4.7411F, -3.1416F, -0.3927F, 3.1416F));
        body.addOrReplaceChild("cube_r10", CubeListBuilder.create().mirror()
                .texOffs(0, 9).addBox(-6.5F, 0.0F, -15.0F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(3.3246F, -11.5313F, -4.7411F, -3.1416F, 1.1345F, -3.1416F));
        body.addOrReplaceChild("cube_r11", CubeListBuilder.create()
                .texOffs(0, 9).addBox(4.5F, 0.0F, -13.25F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(-1.3047F, -11.5313F, -4.7411F, -3.1416F, -1.1345F, 3.1416F));
        body.addOrReplaceChild("cube_r12", CubeListBuilder.create().mirror()
                .texOffs(0, 9).addBox(-8.25F, -1.75F, -12.25F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(3.3246F, -11.5313F, -4.7411F, 0.0F, 1.5708F, 0.0F));
        body.addOrReplaceChild("cube_r13", CubeListBuilder.create()
                .texOffs(0, 9).addBox(5.25F, -1.75F, -10.25F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(-1.3047F, -11.5313F, -4.7411F, 0.0F, -1.5708F, 0.0F));
        body.addOrReplaceChild("cube_r14", CubeListBuilder.create().mirror()
                .texOffs(0, 9).addBox(-6.0F, -0.75F, -11.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(3.3246F, -11.5313F, -4.7411F, 0.0F, 1.4399F, 0.0F));
        body.addOrReplaceChild("cube_r15", CubeListBuilder.create()
                .texOffs(0, 9).addBox(2.75F, -0.75F, -9.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(-1.3047F, -11.5313F, -4.7411F, 0.0F, -1.4399F, 0.0F));
        body.addOrReplaceChild("cube_r16", CubeListBuilder.create().mirror()
                .texOffs(0, 9).addBox(-5.0F, -0.75F, -7.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.9216F, -10.7813F, -4.1085F, 0.0F, 1.0036F, 0.0F));
        body.addOrReplaceChild("cube_r17", CubeListBuilder.create()
                .texOffs(0, 9).addBox(2.75F, 0.0F, -7.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(-1.3047F, -11.5313F, -4.7411F, 0.0F, -1.0036F, 0.0F));
        body.addOrReplaceChild("cube_r18", CubeListBuilder.create().mirror()
                .texOffs(0, 9).addBox(-7.5F, 0.0F, -4.0F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(3.1024F, -10.7813F, -5.5086F, 0.0F, 0.3054F, 0.0F));
        body.addOrReplaceChild("cube_r19", CubeListBuilder.create()
                .texOffs(0, 9).addBox(2.5F, -2.25F, -3.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(-1.0247F, -8.5313F, -5.3841F, 0.0F, -0.3054F, 0.0F));

        PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(),
                PartPose.offset(6.0F, -13.0F, 1.0F));
        leftArm.addOrReplaceChild("cube_r20", CubeListBuilder.create()
                .texOffs(84, 12).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offsetAndRotation(2.8934F, 5.723F, 0.0F, 0.0F, 0.0F, -0.2182F));
        leftArm.addOrReplaceChild("cube_r21", CubeListBuilder.create()
                .texOffs(24, 69).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 10.0F, 6.0F),
                PartPose.offsetAndRotation(5.2742F, 16.4623F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(),
                PartPose.offset(-6.0F, -13.0F, 1.0F));
        rightArm.addOrReplaceChild("cube_r22", CubeListBuilder.create()
                .texOffs(68, 78).addBox(-2.0F, -17.0F, -2.0F, 4.0F, 12.0F, 4.0F)
                .texOffs(0, 69).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 10.0F, 6.0F),
                PartPose.offsetAndRotation(-5.2742F, 16.4623F, 0.0F, 0.0F, 0.0F, 0.2182F));

        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(48, 72).addBox(-2.75F, 2.0F, -1.75F, 5.0F, 9.0F, 5.0F)
                .texOffs(65, 56).addBox(-3.75F, 11.0F, -2.75F, 7.0F, 4.0F, 7.0F)
                .texOffs(51, 17).addBox(-3.25F, 14.0F, -7.0F, 6.0F, 5.0F, 11.0F),
                PartPose.offset(4.0F, 5.0F, 1.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(76, 33).addBox(-2.25F, 2.0F, -2.75F, 5.0F, 9.0F, 5.0F)
                .texOffs(69, 67).addBox(-3.25F, 11.0F, -3.75F, 7.0F, 4.0F, 7.0F)
                .texOffs(42, 56).addBox(-2.75F, 14.0F, -8.0F, 6.0F, 5.0F, 11.0F),
                PartPose.offset(-4.0F, 5.0F, 2.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    protected void setupAttackAnimation(AbstractClientPlayer player, float ageInTicks) {
        if (!(this.attackTime <= 0.0F)) {
            HumanoidArm handside = this.getSwingingArm(player);
            ModelPart modelrenderer = this.getArm(handside);
            float f = this.attackTime;
            this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F;
            if (handside == HumanoidArm.LEFT) {
                this.body.yRot *= -1.0F;
            }
            this.rightArm.yRot += this.body.yRot;
            this.leftArm.yRot += this.body.yRot;
            this.leftArm.xRot += this.body.yRot;
            f = 1.0F - this.attackTime;
            f = f * f;
            f = f * f;
            f = 1.0F - f;
            float f1 = Mth.sin(f * (float) Math.PI);
            float f2 = Mth.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
            modelrenderer.xRot = (float) ((double) modelrenderer.xRot - ((double) f1 * 1.2D + (double) f2));
            modelrenderer.yRot += this.body.yRot * 2.0F;
            modelrenderer.zRot += Mth.sin(this.attackTime * (float) Math.PI) * -0.4F;
        }
    }

    @Override
    public void setupAnim(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // The model instance is reused between frames, restore the default pose first
        this.head.resetPose();
        this.body.resetPose();
        this.leftArm.resetPose();
        this.rightArm.resetPose();
        this.leftLeg.resetPose();
        this.rightLeg.resetPose();

        boolean flag = player.getFallFlyingTicks() > 4;
        boolean flag1 = player.isVisuallySwimming();
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);

        if (flag) {
            this.head.xRot = (-(float) Math.PI / 4F);
        } else if (this.swimAmount > 0.0F) {
            if (flag1) {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float) Math.PI / 4F));
            } else {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, headPitch * ((float) Math.PI / 180F));
            }
        } else {
            this.head.xRot = headPitch * ((float) Math.PI / 180F);
        }

        this.body.yRot = 0F;
        this.rightArm.z = 1F;
        this.rightArm.x = -6F;
        this.leftArm.z = 1F;
        this.leftArm.x = 6F;
        float f = 1.0F;

        if (flag) {
            f = (float) player.getDeltaMovement().lengthSqr();
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        this.leftLeg.zRot = 0.0F;

        if (this.riding) {
            this.rightArm.xRot += (-(float) Math.PI / 5F);
            this.leftArm.xRot += (-(float) Math.PI / 5F);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float) Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float) Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
        }

        this.rightArm.yRot = 0.0F;
        this.leftArm.yRot = 0.0F;
        boolean flag2 = player.getMainArm() == HumanoidArm.RIGHT;
        boolean flag3 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();

        if (flag2 != flag3) {
            this.poseLeftArm(player);
            this.poseRightArm(player);
        } else {
            this.poseRightArm(player);
            this.poseLeftArm(player);
        }

        this.setupAttackAnimation(player, ageInTicks);

        if (this.crouching) {
            this.body.xRot = 0.5F;
            this.rightArm.xRot += 0.4F;
            this.leftArm.xRot += 0.4F;
            this.rightLeg.z = 3.5F;
            this.leftLeg.z = 3.5F;
            this.rightLeg.y = 2.5F;
            this.leftLeg.y = 2.5F;
            this.head.y = -15.0F;
            this.head.z = -5.0F;
            this.body.y = -3.0F;
            this.leftArm.y = -15.0F;
            this.rightArm.y = -15.0F;
            this.leftArm.z = -5.0F;
            this.rightArm.z = -5.0F;
        }

        AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);

        if (this.swimAmount > 0.0F) {
            float f1 = limbSwing % 26.0F;
            HumanoidArm handside = this.getSwingingArm(player);
            float f2 = handside == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            float f3 = handside == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;

            if (f1 < 14.0F) {
                this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, 0.0F);
                this.rightArm.xRot = Mth.lerp(f2, this.rightArm.xRot, 0.0F);
                this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float) Math.PI);
                this.rightArm.yRot = Mth.lerp(f2, this.rightArm.yRot, (float) Math.PI);
                this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, (float) Math.PI + 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
                this.rightArm.zRot = Mth.lerp(f2, this.rightArm.zRot, (float) Math.PI - 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
            } else if (f1 >= 14.0F && f1 < 22.0F) {
                float f6 = (f1 - 14.0F) / 8.0F;
                this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, ((float) Math.PI / 2F) * f6);
                this.rightArm.xRot = Mth.lerp(f2, this.rightArm.xRot, ((float) Math.PI / 2F) * f6);
                this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float) Math.PI);
                this.rightArm.yRot = Mth.lerp(f2, this.rightArm.yRot, (float) Math.PI);
                this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
                this.rightArm.zRot = Mth.lerp(f2, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
            } else if (f1 >= 22.0F && f1 < 26.0F) {
                float f4 = (f1 - 22.0F) / 4.0F;
                this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, ((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f4);
                this.rightArm.xRot = Mth.lerp(f2, this.rightArm.xRot, ((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f4);
                this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float) Math.PI);
                this.rightArm.yRot = Mth.lerp(f2, this.rightArm.yRot, (float) Math.PI);
                this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, (float) Math.PI);
                this.rightArm.zRot = Mth.lerp(f2, this.rightArm.zRot, (float) Math.PI);
            }

            this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F + (float) Math.PI));
            this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F));
        }

        this.hat.copyFrom(this.head);
    }

    private float quadraticArmUpdate(float f) {
        return -65.0F * f + f * f;
    }

    private HumanoidArm getSwingingArm(AbstractClientPlayer player) {
        HumanoidArm humanoidarm = player.getMainArm();
        return player.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
    }

    private void poseLeftArm(AbstractClientPlayer player) {
        switch (this.leftArmPose) {
            case EMPTY:
                this.leftArm.yRot = 0.0F;
                break;
            case BLOCK:
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
                this.leftArm.yRot = ((float) Math.PI / 6F);
                break;
            case ITEM:
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 10F);
                this.leftArm.yRot = 0.0F;
                break;
            case THROW_SPEAR:
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
                this.leftArm.yRot = 0.0F;
                break;
            case BOW_AND_ARROW:
                this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
                this.leftArm.yRot = 0.1F + this.head.yRot;
                this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
                this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
                break;
            case CROSSBOW_CHARGE:
                AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, player, false);
                break;
            case CROSSBOW_HOLD:
                AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
                break;
            default:
                break;
        }
    }

    private void poseRightArm(AbstractClientPlayer player) {
        switch (this.rightArmPose) {
            case EMPTY:
                this.rightArm.yRot = 0.0F;
                break;
            case BLOCK:
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.9424779F;
                this.rightArm.yRot = (-(float) Math.PI / 6F);
                break;
            case ITEM:
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 10F);
                this.rightArm.yRot = 0.0F;
                break;
            case THROW_SPEAR:
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
                this.rightArm.yRot = 0.0F;
                break;
            case BOW_AND_ARROW:
                this.rightArm.yRot = -0.1F + this.head.yRot;
                this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
                this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
                this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
                break;
            case CROSSBOW_CHARGE:
                AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, player, true);
                break;
            case CROSSBOW_HOLD:
                AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
    }

    @Override
    public void renderFirstPersonHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, ModelPart hand, ModelPart handOverlay,
            ResourceLocation texture) {
        hand.xRot = 0;
        hand.x = 0.0F;
        hand.y = -7.0F;
        hand.z = 0.0F;
        hand.render(poseStack, buffer.getBuffer(RenderType.entitySolid(texture)), packedLight, OverlayTexture.NO_OVERLAY);
        handOverlay.xRot = 0;
        handOverlay.y = 1.5F;
        handOverlay.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY);
    }
}
