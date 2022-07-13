package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class DesertVoeHelmetModel extends BipedModel<LivingEntity>
{
	public DesertVoeHelmetModel()
	{
		super(0.0F);
		
		texWidth = 64;
		texHeight = 64;
		
		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.1F, false);
		
		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(32, 24).addBox(-1.5F, 0.0F, 2.399F, 3.0F, 1.0F, 0.0F, 0.0F, false);
		body.texOffs(16, 33).addBox(-1.5F, 0.0F, -2.401F, 3.0F, 1.0F, 0.0F, 0.0F, false);
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