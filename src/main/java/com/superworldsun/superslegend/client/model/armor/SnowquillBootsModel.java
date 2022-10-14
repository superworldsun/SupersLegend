package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SnowquillBootsModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer LeftFoot_r1;
	private final ModelRenderer RightFoot_r1;
	
	public SnowquillBootsModel()
	{
		super(0.0F);
		
		texWidth = 128;
		texHeight = 128;
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(53, 46).addBox(-2.0F, 5.75F, -2.0F, 4.0F, 1.0F, 4.0F, 0.7F, false);
		leftLeg.texOffs(0, 60).addBox(-1.5F, 6.75F, -2.5F, 3.0F, 6.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(46, 49).addBox(-2.5F, 6.75F, -2.5F, 1.0F, 6.0F, 5.0F, 0.0F, false);
		leftLeg.texOffs(23, 59).addBox(-1.5F, 6.75F, 1.5F, 3.0F, 6.0F, 1.0F, 0.0F, false);
		leftLeg.texOffs(52, 17).addBox(1.5F, 6.75F, -2.5F, 1.0F, 6.0F, 5.0F, 0.0F, false);
		leftLeg.texOffs(58, 12).addBox(-1.5F, 11.75F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
		
		LeftFoot_r1 = new ModelRenderer(this);
		leftLeg.addChild(LeftFoot_r1);
		setRotationAngle(LeftFoot_r1, 0.2182F, 0.0F, 0.0F);
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(0, 36).addBox(-1.5F, 6.75F, -2.5F, 3.0F, 6.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(0, 43).addBox(-2.5F, 6.75F, -2.5F, 1.0F, 6.0F, 5.0F, 0.0F, false);
		rightLeg.texOffs(12, 48).addBox(1.5F, 6.75F, -2.5F, 1.0F, 6.0F, 5.0F, 0.0F, false);
		rightLeg.texOffs(15, 59).addBox(-1.5F, 6.75F, 1.5F, 3.0F, 6.0F, 1.0F, 0.0F, false);
		rightLeg.texOffs(23, 0).addBox(-1.5F, 11.75F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
		rightLeg.texOffs(51, 28).addBox(-2.0F, 5.75F, -2.0F, 4.0F, 1.0F, 4.0F, 0.7F, false);
		
		RightFoot_r1 = new ModelRenderer(this);
		rightLeg.addChild(RightFoot_r1);
		setRotationAngle(RightFoot_r1, 0.2182F, 0.0F, 0.0F);
	}
	
	@Override
	protected Iterable<ModelRenderer> bodyParts()
	{
		return ImmutableList.of(leftLeg, rightLeg);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}