package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import java.util.List;

public class UnnapraisedRingItem extends NonEnchantItem
{
	public UnnapraisedRingItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GRAY + "An Unapraised Ring, give it to a novice Tool Smith"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "to have it looked at"));
	}

}
