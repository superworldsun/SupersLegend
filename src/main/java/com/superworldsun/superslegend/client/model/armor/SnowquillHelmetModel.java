package com.superworldsun.superslegend.client.model.armor;

import com.google.common.collect.ImmutableList;

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
	protected Iterable<ModelRenderer> bodyParts()
	{
		return ImmutableList.of();
	}
}