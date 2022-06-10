package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.api.IPelletAmo;
import com.superworldsun.superslegend.entities.projectiles.seeds.DekuSeedEntity;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class DekuSeed extends ArrowItem implements IPelletAmo {
	public DekuSeed(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		DekuSeedEntity pellet = new DekuSeedEntity(worldIn, shooter);
		return pellet;
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "Deku Seeds").withStyle(TextFormatting.ITALIC));
	}
}