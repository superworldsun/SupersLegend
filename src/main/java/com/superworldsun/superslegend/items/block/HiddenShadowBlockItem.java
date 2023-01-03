package com.superworldsun.superslegend.items.block;

import java.util.List;

import com.superworldsun.superslegend.registries.BlockInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HiddenShadowBlockItem extends ShadowBlockBaseItem {
	public HiddenShadowBlockItem() {
		super(BlockInit.HIDDEN_SHADOW_BLOCK.get());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "This Block remains unseen by the unobservant"));
		list.add(new StringTextComponent(TextFormatting.DARK_PURPLE + "Look at a block and Sneak+RightClick"));
		list.add(new StringTextComponent(TextFormatting.DARK_PURPLE + "to copy its look."));
		list.add(new StringTextComponent(TextFormatting.DARK_PURPLE + "Sneak+Right-Click the air to remove copy"));
	}
}
