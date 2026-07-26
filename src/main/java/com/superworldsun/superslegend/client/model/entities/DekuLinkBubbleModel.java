// Made with Blockbench 5.1.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
package com.superworldsun.superslegend.client.model.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class DekuLinkBubbleModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart bb_main;

	public DekuLinkBubbleModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -2.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-2.0F, -1.0F, -2.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-1.0F, -5.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(-1.0F, -1.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 2).addBox(-1.0F, -5.0F, 1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-2.0F, -4.0F, 1.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 4).addBox(-1.0F, -1.0F, 1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-2.0F, -4.0F, -3.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 8).addBox(-2.0F, -4.0F, -2.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 14).addBox(2.0F, -4.0F, -2.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 3.0F, 0.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
