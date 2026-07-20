package com.superworldsun.superslegend.client.model.player;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.interfaces.IHandRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
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

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 19).addBox(-4.5F, -9.0F, -3.5F, 9.0F, 9.0F, 8.0F)
                .texOffs(14, 48).addBox(-2.0F, -4.1F, -5.25F, 4.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create(),
                PartPose.offset(0.0F, -10.5F, 7.4F));
        hair.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(34, 16).addBox(6.975F, -2.5F, -5.0F, 0.0F, 5.0F, 8.0F),
                PartPose.offsetAndRotation(-2.0F, 4.0918F, -6.6068F, -0.3927F, 0.0F, 0.0F));
        hair.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(25, 0).addBox(-1.0F, 0.25F, 3.0F, 10.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(-4.0F, -1.5103F, -12.9455F, -0.3927F, 0.0F, 0.0F));
        hair.addOrReplaceChild("cube_r3", CubeListBuilder.create()
                .texOffs(28, 3).addBox(-5.0F, -2.0F, 0.0F, 10.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 1.7164F, -11.0349F, -0.3927F, 0.0F, 0.0F));
        hair.addOrReplaceChild("cube_r4", CubeListBuilder.create()
                .texOffs(22, 35).addBox(-5.476F, 3.0F, -10.0F, 0.0F, 5.0F, 8.0F),
                PartPose.offsetAndRotation(0.501F, 0.9239F, 0.1173F, -0.3927F, 0.0F, 0.0F));

        PartDefinition dekuHat = head.addOrReplaceChild("deku_hat", CubeListBuilder.create(),
                PartPose.offset(0.5F, 9.0981F, 6.0812F));
        dekuHat.addOrReplaceChild("cube_r5", CubeListBuilder.create()
                .texOffs(30, 13).addBox(-4.0F, -9.5F, -7.5F, 8.0F, 5.0F, 6.0F),
                PartPose.offsetAndRotation(-0.5F, -4.7132F, 8.815F, 0.3927F, 0.0F, 0.0F));
        dekuHat.addOrReplaceChild("cube_r6", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-5.0F, -2.1F, -8.5F, 10.0F, 11.0F, 8.0F),
                PartPose.offsetAndRotation(-0.5F, -20.0981F, 1.3188F, 0.48F, 0.0F, 0.0F));
        dekuHat.addOrReplaceChild("cube_r7", CubeListBuilder.create()
                .texOffs(0, 46).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(-0.5F, 0.0766F, 2.8102F, -0.0873F, 0.0F, 0.0F));
        dekuHat.addOrReplaceChild("cube_r8", CubeListBuilder.create()
                .texOffs(0, 36).addBox(-3.0F, -0.2039F, -1.9567F, 6.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(-0.5F, -7.3896F, 1.8762F, 0.1745F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(30, 32).addBox(-3.5F, 0.0F, -1.5F, 7.0F, 7.0F, 4.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        body.addOrReplaceChild("cube_r9", CubeListBuilder.create()
                .texOffs(26, 20).addBox(4.0F, 0.0F, -2.0F, 0.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(-0.4658F, 7.5221F, 0.5F, 0.0F, 0.0F, -0.1309F));
        body.addOrReplaceChild("cube_r10", CubeListBuilder.create()
                .texOffs(34, 25).addBox(0.001F, 0.0F, -2.0F, 0.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(-3.5F, 7.0F, 0.5F, 0.0F, 0.0F, 0.1309F));
        body.addOrReplaceChild("cube_r11", CubeListBuilder.create()
                .texOffs(36, 10).addBox(-3.0F, 0.0F, 0.0F, 7.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(-0.5F, 7.0004F, 2.4991F, 0.2618F, 0.0F, 0.0F));
        body.addOrReplaceChild("cube_r12", CubeListBuilder.create()
                .texOffs(48, 0).addBox(-3.0F, 0.0F, 0.0F, 7.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(-0.5F, 7.0F, -1.5F, -0.2618F, 0.0F, 0.0F));

        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(),
                PartPose.offset(-7.0F, 9.0F, 0.5F));
        rightArm.addOrReplaceChild("arm", CubeListBuilder.create()
                .texOffs(47, 26).addBox(-2.8007F, 5.5463F, 0.5F, 3.0F, 3.0F, 3.0F)
                .texOffs(22, 40).addBox(-2.3007F, 4.5463F, 1.0F, 2.0F, 1.0F, 2.0F)
                .texOffs(17, 36).addBox(-2.8007F, 3.5463F, 0.5F, 3.0F, 1.0F, 3.0F)
                .texOffs(0, 0).addBox(-2.3007F, -1.4537F, 1.0F, 2.0F, 5.0F, 2.0F),
                PartPose.offset(3.8008F, 0.4536F, -2.0F));

        PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(),
                PartPose.offset(6.3F, 9.0F, 0.5F));
        leftArm.addOrReplaceChild("arm", CubeListBuilder.create()
                .texOffs(46, 47).addBox(-1.1993F, 5.5463F, 0.5F, 3.0F, 3.0F, 3.0F)
                .texOffs(52, 36).addBox(-0.6993F, 4.5463F, 1.0F, 2.0F, 1.0F, 2.0F)
                .texOffs(44, 43).addBox(-1.1993F, 3.5463F, 0.5F, 3.0F, 1.0F, 3.0F)
                .texOffs(0, 19).addBox(-0.6993F, -1.4537F, 1.0F, 2.0F, 5.0F, 2.0F),
                PartPose.offset(-2.1008F, 0.4537F, -2.0F));

        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(48, 32).addBox(-1.75F, 4.0F, -0.75F, 3.0F, 1.0F, 3.0F)
                .texOffs(34, 50).addBox(-1.25F, 0.0F, -0.25F, 2.0F, 4.0F, 2.0F)
                .texOffs(52, 14).addBox(-1.25F, 5.0F, -0.25F, 2.0F, 2.0F, 2.0F)
                .texOffs(33, 43).addBox(-1.75F, 7.0F, -2.75F, 3.0F, 2.0F, 5.0F),
                PartPose.offset(-2.0F, 15.0F, -0.25F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(17, 36).addBox(-2.25F, 4.0F, -0.75F, 3.0F, 1.0F, 3.0F)
                .texOffs(26, 48).addBox(-1.75F, 0.0F, -0.25F, 2.0F, 4.0F, 2.0F)
                .texOffs(52, 10).addBox(-1.75F, 5.0F, -0.25F, 2.0F, 2.0F, 2.0F)
                .texOffs(43, 3).addBox(-2.25F, 7.0F, -2.75F, 3.0F, 2.0F, 5.0F),
                PartPose.offset(3.0F, 15.0F, -0.25F));

        return LayerDefinition.create(mesh, 64, 64);
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
        this.rightArm.x = -7.0F;
        this.leftArm.z = 0.5F;
        this.leftArm.x = 6.3F;
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
            this.rightLeg.z = 3.65F;
            this.leftLeg.z = 3.65F;
            this.rightLeg.y = 15.2F;
            this.leftLeg.y = 15.2F;
            this.head.y = 12.2F;
            this.body.y = 11.2F;
            this.leftArm.y = 12.2F;
            this.rightArm.y = 12.2F;
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

            this.rightArm.z = Mth.sin(this.body.yRot) * 7.0F;
            this.rightArm.x = -Mth.cos(this.body.yRot) * 7.0F;
            this.leftArm.z = -Mth.sin(this.body.yRot) * 6.3F;
            this.leftArm.x = Mth.cos(this.body.yRot) * 6.3F;
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
        hand.y = 7.0F;
        hand.render(poseStack, buffer.getBuffer(RenderType.entitySolid(texture)), packedLight, OverlayTexture.NO_OVERLAY);
        handOverlay.xRot = 0;
        handOverlay.y = 7.0F;
        handOverlay.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public void renderThirdPersonItem(ItemInHandRenderer itemInHandRenderer, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm,
            PoseStack poseStack, MultiBufferSource buffer, int light) {
        boolean leftHand = arm == HumanoidArm.LEFT;
        poseStack.mulPose(Axis.XP.rotationDegrees(-90F));
        poseStack.mulPose(Axis.YP.rotationDegrees(170F));
        poseStack.translate(leftHand ? 0.05D : -0.25D, 0.125D, -0.625D);
        itemInHandRenderer.renderItem(livingEntity, itemStack, displayContext, leftHand, poseStack, buffer, light);
    }
}
