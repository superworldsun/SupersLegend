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

public class DekuPlayerModel extends PlayerModel<AbstractClientPlayer> implements IHandRenderer {
    public DekuPlayerModel(ModelPart root) {
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

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(33, 0).addBox(-3.5F, 3.0F, -2.5F, 7.0F, 4.0F, 5.0F)
                .texOffs(20, 42).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 3.0F, 4.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(0, 14).addBox(0.0F, -1.0F, -3.5F, 0.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(3.8836F, 7.9235F, 1.0F, 0.0F, 0.0F, -0.3927F));
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(0, 16).addBox(0.0F, -1.0F, -3.5F, 0.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-3.8827F, 7.9239F, 1.0F, 0.0F, 0.0F, 0.3927F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create()
                .texOffs(16, 39).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 7.0F, 2.5F, 0.3927F, 0.0F, 0.0F));
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create()
                .texOffs(51, 10).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 6.9996F, -2.5009F, -0.3927F, 0.0F, 0.0F));

        PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(),
                PartPose.offset(2.0F, 16.0F, -0.5F));
        rightLeg.addOrReplaceChild("cube_r5", CubeListBuilder.create()
                .texOffs(54, 6).addBox(2.3695F, -1.125F, -11.8814F, 3.0F, 1.0F, 3.0F)
                .texOffs(20, 41).addBox(3.3695F, -5.125F, -10.8814F, 1.0F, 4.0F, 1.0F)
                .texOffs(36, 42).addBox(2.8695F, -0.125F, -11.3814F, 2.0F, 2.0F, 2.0F)
                .texOffs(15, 49).addBox(2.3695F, 1.875F, -13.8814F, 3.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-7.5F, 4.125F, 8.25F, 0.0F, -0.3927F, 0.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(),
                PartPose.offset(-2.5F, 16.0F, -0.5F));
        leftLeg.addOrReplaceChild("cube_r6", CubeListBuilder.create()
                .texOffs(52, 0).addBox(-1.5F, -1.125F, -1.25F, 3.0F, 1.0F, 3.0F)
                .texOffs(33, 0).addBox(-0.5F, -5.125F, -0.25F, 1.0F, 4.0F, 1.0F)
                .texOffs(0, 23).addBox(-1.0F, -0.125F, -0.75F, 2.0F, 2.0F, 2.0F)
                .texOffs(40, 42).addBox(-1.5F, 1.875F, -3.25F, 3.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 4.125F, 0.25F, 0.0F, 0.3927F, 0.0F));

        PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(),
                PartPose.offset(4.0F, 9.5F, 0.5F));
        leftArm.addOrReplaceChild("cube_r7", CubeListBuilder.create()
                .texOffs(0, 50).addBox(-1.5F, 6.5F, -1.5F, 3.0F, 3.0F, 3.0F)
                .texOffs(54, 47).addBox(-1.0F, 5.5F, -1.0F, 2.0F, 1.0F, 2.0F)
                .texOffs(51, 42).addBox(-1.5F, 4.5F, -1.5F, 3.0F, 1.0F, 3.0F)
                .texOffs(30, 24).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 4.0F, 1.0F)
                .texOffs(0, 4).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(),
                PartPose.offset(-4.0F, 9.5F, 0.5F));
        rightArm.addOrReplaceChild("cube_r8", CubeListBuilder.create()
                .texOffs(45, 49).addBox(-1.5F, 6.5F, -1.5F, 3.0F, 3.0F, 3.0F)
                .texOffs(9, 50).addBox(-1.0F, 5.5F, -1.0F, 2.0F, 1.0F, 2.0F)
                .texOffs(42, 9).addBox(-1.5F, 4.5F, -1.5F, 3.0F, 1.0F, 3.0F)
                .texOffs(30, 19).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 4.0F, 1.0F)
                .texOffs(0, 0).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 19).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F)
                .texOffs(0, 39).addBox(-3.0F, 2.305F, 7.7802F, 6.0F, 7.0F, 4.0F)
                .texOffs(31, 49).addBox(-2.5F, -5.0F, -7.0F, 5.0F, 5.0F, 2.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        head.addOrReplaceChild("cube_r9", CubeListBuilder.create()
                .texOffs(26, 49).addBox(-6.0F, 4.75F, -10.0F, 2.0F, 3.0F, 0.0F)
                .texOffs(6, 21).addBox(-5.5F, 4.75F, -9.0F, 0.0F, 2.0F, 2.0F)
                .texOffs(0, 25).addBox(5.5F, 4.75F, -9.0F, 0.0F, 2.0F, 2.0F)
                .texOffs(45, 55).addBox(4.0F, 4.75F, -10.0F, 2.0F, 3.0F, 0.0F)
                .texOffs(0, 39).addBox(-1.0F, 4.75F, -10.0F, 2.0F, 4.0F, 0.0F)
                .texOffs(4, 27).addBox(-3.5F, 4.75F, -10.0F, 2.0F, 2.0F, 0.0F)
                .texOffs(49, 55).addBox(1.5F, 4.75F, -10.0F, 2.0F, 3.0F, 0.0F)
                .texOffs(30, 29).addBox(-5.5F, 1.75F, -10.0F, 11.0F, 3.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -10.5F, 6.0F, -0.3927F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r10", CubeListBuilder.create()
                .texOffs(36, 13).addBox(-4.0F, -5.5F, -5.5F, 8.0F, 10.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -1.6612F, 10.5962F, 0.3927F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r11", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-6.0F, -5.0F, -8.5F, 12.0F, 10.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, -10.5F, 6.0F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
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

        this.body.yRot = 0.0F;
        this.rightArm.z = 0.5F;
        this.rightArm.x = -4.0F;
        this.leftArm.z = 0.5F;
        this.leftArm.x = 4.0F;
        float f = 1.0F;
        if (flag) {
            f = (float) player.getDeltaMovement().lengthSqr();
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
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
            this.rightLeg.y = 16.2F;
            this.leftLeg.y = 16.2F;
            this.head.y = 12.2F;
            this.body.y = 11.2F;
            this.leftArm.y = 12.7F;
            this.rightArm.y = 12.7F;
        } else {
            this.body.xRot = 0.0F;
            this.rightLeg.z = -0.4F;
            this.leftLeg.z = -0.4F;
            this.rightLeg.y = 16.0F;
            this.leftLeg.y = 16.0F;
            this.head.y = 8.0F;
            this.body.y = 8.0F;
            this.leftArm.y = 9.5F;
            this.rightArm.y = 9.5F;
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

            this.rightArm.z = Mth.sin(this.body.yRot) * 4.0F;
            this.rightArm.x = -Mth.cos(this.body.yRot) * 4.0F;
            this.leftArm.z = -Mth.sin(this.body.yRot) * 4.0F;
            this.leftArm.x = Mth.cos(this.body.yRot) * 4.0F;
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

    private float quadraticArmUpdate(float f) {
        return -65.0F * f + f * f;
    }

    private HumanoidArm getSwingingArm(AbstractClientPlayer player) {
        HumanoidArm humanoidarm = player.getMainArm();
        return player.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
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

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
    }

    @Override
    public void renderFirstPersonHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, ModelPart hand, ModelPart handOverlay,
            ResourceLocation texture) {
        hand.xRot = 0;
        hand.y = 7.5F;
        hand.render(poseStack, buffer.getBuffer(RenderType.entitySolid(texture)), packedLight, OverlayTexture.NO_OVERLAY);
        handOverlay.xRot = 0;
        handOverlay.y = 7.5F;
        handOverlay.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY);
    }
}
