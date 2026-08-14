package com.superworldsun.superslegend.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

/** 1.20 port of the original 1.16.5 Magic Armor biped model. */
public class MagicArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
    public MagicArmorModel(EquipmentSlot slot) {
        super(createRoot(slot));
    }

    private static ModelPart createRoot(EquipmentSlot slot) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeListBuilder empty = CubeListBuilder.create();

        PartDefinition head = root.addOrReplaceChild("head", slot == EquipmentSlot.HEAD ? headCubes() : empty,
                PartPose.ZERO);
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        addHeadCrest(head, slot == EquipmentSlot.HEAD);

        root.addOrReplaceChild("body", slot == EquipmentSlot.CHEST ? bodyCubes() : CubeListBuilder.create(),
                PartPose.ZERO);
        PartDefinition leftArm = root.addOrReplaceChild("left_arm",
                slot == EquipmentSlot.CHEST ? leftArmCubes() : CubeListBuilder.create(), PartPose.offset(5, 2, 0));
        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                slot == EquipmentSlot.CHEST ? rightArmCubes() : CubeListBuilder.create(), PartPose.offset(-5, 2, 0));
        addArmDetails(leftArm, rightArm, slot == EquipmentSlot.CHEST);

        PartDefinition leftLeg = root.addOrReplaceChild("left_leg", legCubes(slot, true), PartPose.offset(2, 12, 0));
        PartDefinition rightLeg = root.addOrReplaceChild("right_leg", legCubes(slot, false), PartPose.offset(-2, 12, 0));
        addLegDetails(leftLeg, rightLeg, slot);

        return LayerDefinition.create(mesh, 160, 160).bakeRoot();
    }

    private static CubeListBuilder headCubes() {
        return CubeListBuilder.create()
                .texOffs(22, 23).addBox(-4, -8, -4, 8, 2, 8, new CubeDeformation(0.1F))
                .texOffs(0, 0).addBox(-4, -8, -4, 8, 8, 8)
                .texOffs(0, 16).addBox(-4, -8.2F, -2, 8, 8, 7, new CubeDeformation(0.2F))
                .texOffs(0, 16).addBox(-1.5F, -9.9F, -4.2F, 3, 4, 0);
    }

    private static void addHeadCrest(PartDefinition head, boolean visible) {
        if (!visible) return;
        PartPose pivot = PartPose.offset(0, -4.2F, 2);
        head.addOrReplaceChild("crest_1", CubeListBuilder.create().texOffs(24, 4)
                .addBox(-1, -3, 8.8F, 2, 3, 1), rotate(pivot, -1.1781F, 0, 0));
        head.addOrReplaceChild("crest_2", CubeListBuilder.create().texOffs(60, 65)
                .addBox(-1.5F, -3.4F, 6.9F, 3, 4, 2), rotate(pivot, -1.1345F, 0, 0));
        head.addOrReplaceChild("crest_3", CubeListBuilder.create().texOffs(62, 58)
                .addBox(-2, -3.9F, 5.7F, 4, 5, 2), rotate(pivot, -1.0908F, 0, 0));
        head.addOrReplaceChild("crest_4", CubeListBuilder.create().texOffs(62, 0)
                .addBox(-2.5F, -4.4F, 4.3F, 5, 6, 2), rotate(pivot, -1.0036F, 0, 0));
        head.addOrReplaceChild("crest_5", CubeListBuilder.create().texOffs(32, 49)
                .addBox(-3, -4.9F, 1.9F, 6, 7, 3), rotate(pivot, -0.8727F, 0, 0));
        head.addOrReplaceChild("crest_6", CubeListBuilder.create().texOffs(32, 0)
                .addBox(-3.5F, -4.7F, -0.2F, 7, 7, 4), rotate(pivot, -0.6545F, 0, 0));
    }

    private static CubeListBuilder bodyCubes() {
        return CubeListBuilder.create()
                .texOffs(0, 31).addBox(-4, 0, -2, 8, 12, 4)
                .texOffs(46, 22).addBox(-4, 10, -2, 8, 2, 4, new CubeDeformation(0.3F))
                .texOffs(27, 11).addBox(-4.5F, 0, -2.7F, 9, 6, 5)
                .texOffs(6, 6).addBox(-0.5F, 1.2F, -2.8F, 1, 1, 0)
                .texOffs(0, 0).addBox(-1.5F, 4, -2.6F, 3, 8, 0);
    }

    private static CubeListBuilder leftArmCubes() {
        return CubeListBuilder.create()
                .texOffs(40, 33).addBox(-1, -2, -2, 4, 12, 4)
                .texOffs(16, 65).addBox(1.2F, 2, -2, 2, 1, 4, new CubeDeformation(0.1F))
                .texOffs(62, 45).addBox(1.2F, 5, -2, 2, 3, 4, new CubeDeformation(0.1F));
    }

    private static CubeListBuilder rightArmCubes() {
        return CubeListBuilder.create()
                .texOffs(24, 33).addBox(-3, -2, -2, 4, 12, 4)
                .texOffs(0, 63).addBox(-3.2F, 2, -2, 2, 1, 4, new CubeDeformation(0.1F))
                .texOffs(62, 12).addBox(-3.2F, 5, -2, 2, 3, 4, new CubeDeformation(0.1F));
    }

    private static void addArmDetails(PartDefinition left, PartDefinition right, boolean visible) {
        if (!visible) return;
        left.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(46, 23)
                .addBox(-0.2F, -3, -2.5F, 0, 2, 5, new CubeDeformation(0.2F)),
                PartPose.rotation(0, 0, -0.0873F));
        left.addOrReplaceChild("left_band", CubeListBuilder.create().texOffs(64, 28)
                .addBox(0.8F, 3.3F, -2, 2, 1, 4, new CubeDeformation(0.1F)),
                PartPose.rotation(0, 0, -0.1309F));
        left.addOrReplaceChild("left_pauldron", CubeListBuilder.create().texOffs(28, 61)
                .addBox(-0.3F, -2, -2, 4, 4, 4, new CubeDeformation(0.2F)),
                PartPose.rotation(0, 0, 0.0436F));
        right.addOrReplaceChild("right_band", CubeListBuilder.create().texOffs(8, 64)
                .addBox(-2.8F, 3.3F, -2, 2, 1, 4, new CubeDeformation(0.1F)),
                PartPose.rotation(0, 0, 0.1309F));
        right.addOrReplaceChild("right_pauldron", CubeListBuilder.create().texOffs(46, 58)
                .addBox(-3.7F, -2, -2, 4, 4, 4, new CubeDeformation(0.2F)),
                PartPose.rotation(0, 0, -0.0436F));
        right.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(12, 42)
                .addBox(0.2F, -3, -2.5F, 0, 2, 5, new CubeDeformation(0.2F)),
                PartPose.rotation(0, 0, 0.0873F));
    }

    private static CubeListBuilder legCubes(EquipmentSlot slot, boolean left) {
        CubeListBuilder cubes = CubeListBuilder.create();
        if (slot == EquipmentSlot.LEGS) {
            cubes.texOffs(left ? 23 : 6, left ? 16 : 0)
                    .addBox(left ? -1.8F : 0.8F, 0.2F, -2.3F, 1, 6, 0, new CubeDeformation(0.3F));
        } else if (slot == EquipmentSlot.FEET) {
            cubes.texOffs(left ? 16 : 0, left ? 49 : 47)
                    .addBox(-2, 0, -2, 4, 12, 4, new CubeDeformation(0.1F));
            if (left) {
                cubes.texOffs(24, 2).addBox(-2, 11, -3, 4, 1, 1)
                        .texOffs(28, 49).addBox(-1, 10, -3, 2, 1, 1)
                        .texOffs(12, 49).addBox(-1, 11, -4, 2, 1, 1);
            } else {
                cubes.texOffs(0, 20).addBox(-1, 11, -4, 2, 1, 1)
                        .texOffs(24, 0).addBox(-2, 11, -3, 4, 1, 1)
                        .texOffs(20, 33).addBox(-1, 10, -3, 2, 1, 1);
            }
        }
        return cubes;
    }

    private static void addLegDetails(PartDefinition left, PartDefinition right, EquipmentSlot slot) {
        if (slot == EquipmentSlot.LEGS) {
            left.addOrReplaceChild("left_guard", CubeListBuilder.create().texOffs(56, 37)
                            .addBox(-2.1F, 0, -2, 4, 4, 4, new CubeDeformation(0.3F))
                            .texOffs(50, 49).addBox(-2.1F, 0, -2, 4, 5, 4, new CubeDeformation(0.2F)),
                    PartPose.rotation(0, 0, -0.1309F));
            right.addOrReplaceChild("right_guard", CubeListBuilder.create().texOffs(52, 29)
                            .addBox(-1.9F, 0, -2, 4, 4, 4, new CubeDeformation(0.3F))
                            .texOffs(50, 7).addBox(-1.9F, 0, -2, 4, 5, 4, new CubeDeformation(0.2F)),
                    PartPose.rotation(0, 0, 0.1309F));
        } else if (slot == EquipmentSlot.FEET) {
            left.addOrReplaceChild("left_boot_fin", CubeListBuilder.create().texOffs(36, 35)
                    .addBox(-1.5F, 6, -3, 3, 2, 0, new CubeDeformation(0.2F)),
                    PartPose.offsetAndRotation(0, 0, -0.1F, 0.1309F, 0, 0));
            right.addOrReplaceChild("right_boot_fin", CubeListBuilder.create().texOffs(36, 33)
                    .addBox(-1.5F, 6, -3, 3, 2, 0, new CubeDeformation(0.2F)),
                    PartPose.offsetAndRotation(0, 0, -0.1F, 0.1309F, 0, 0));
        }
    }

    private static PartPose rotate(PartPose pose, float x, float y, float z) {
        return PartPose.offsetAndRotation(pose.x, pose.y, pose.z, x, y, z);
    }
}
