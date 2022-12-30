package com.superworldsun.superslegend.items.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import com.superworldsun.superslegend.registries.ItemGroupInit;

public class AppraisedRingBox extends Item
{
	public static final ITag.INamedTag<Item> itempool =
			ItemTags.bind("superslegend:appraisal_list");

	public AppraisedRingBox(Properties tab)
	{
		super(new Properties().tab(ItemGroupInit.RESOURCES));
	}

	//TODO Add an open sound on use
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		addOrDrop(player, itempool.getRandomElement(world.random));

		if (!player.abilities.instabuild)
		{
			stack.shrink(1);
		}
		return ActionResult.success(stack);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GOLD + "Contains an Appraised Ring"));
	}
	
	private void addOrDrop(PlayerEntity player, Item itemSupplier)
	{
		if (!player.addItem(new ItemStack(itemSupplier)))
		{
			player.spawnAtLocation(itemSupplier);
		}
	}
}