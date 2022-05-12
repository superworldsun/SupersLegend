package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AppraisedRingBox extends Item
{
	public AppraisedRingBox(Properties tab)
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		List<RegistryObject<Item>> possibleOutcomes = Arrays.asList
				(
						//Pulls a random item from this list
						//Adding in the same one more than once increases its odds
						ItemInit.CURSED_RING,
						ItemInit.POWER_RING_L1,
						ItemInit.ARMOR_RING_L1,
						ItemInit.HEART_RING_L1,
						ItemInit.GREEN_LUCK_RING,
						ItemInit.BLUE_LUCK_RING,
						ItemInit.GOLD_LUCK_RING,
						ItemInit.RED_LUCK_RING,
						ItemInit.STEADFAST_RING,
						ItemInit.GREEN_HOLY_RING,
						ItemInit.BLUE_HOLY_RING,
						ItemInit.RED_HOLY_RING,
						ItemInit.SWIMMERS_RING
				);
		addOrDrop(player, possibleOutcomes.get(Item.random.nextInt(possibleOutcomes.size())));

		if (!player.abilities.instabuild)
		{
			stack.shrink(1);
		}
		return ActionResult.success(stack);
	}

	/*@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		int rand = random.nextInt(3);

		if(rand == 0)
		{
			addOrDrop(player, ItemInit.CURSED_RING);
		}
		if(rand == 1)
		{
			addOrDrop(player, ItemInit.POWER_RING_L1);
		}
		return ActionResult.success(stack);
	}*/
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GOLD + "Contains an Appraised Ring"));
	}
	
	private void addOrDrop(PlayerEntity player, RegistryObject<Item> itemSupplier)
	{
		if (!player.addItem(new ItemStack(itemSupplier.get())))
		{
			player.spawnAtLocation(itemSupplier.get());
		}
	}
}