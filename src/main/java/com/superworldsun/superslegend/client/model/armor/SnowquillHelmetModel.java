package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SnowquillHelmetModel extends BipedModel<LivingEntity>
{
	public SnowquillHelmetModel()
	{
		super(0.0F);

		texWidth = 128;
		texHeight = 128;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(3.6F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		head.texOffs(23, 39).addBox(4.1F, -10.0F, -1.0F, 0.0F, 7.0F, 6.0F, 0.0F, false);
	}
	
	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		// if you have other model parts in the helmet model, you need to make them visible manually
		body.visible = true;
		super.renderToBuffer(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}
	
	@Override
	protected Iterable<ModelRenderer> bodyParts()
	{
		return ImmutableList.of(body);
	}
}