package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.registries.ItemGroupInit;
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

import java.util.List;

public class ClimbingSet extends Item
{
	public ClimbingSet()
	{
		super(new Properties().tab(ItemGroupInit.RESOURCES));
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		
		addOrDrop(player, ItemInit.CLIMBERS_BANDANNA);
		addOrDrop(player, ItemInit.CLIMBING_GEAR);
		addOrDrop(player, ItemInit.CLIMBING_PANTS);
		addOrDrop(player, ItemInit.CLIMBING_BOOTS);

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
		list.add(new StringTextComponent(TextFormatting.GREEN + "Climbing set"));
	}
	
	private void addOrDrop(PlayerEntity player, RegistryObject<Item> itemSupplier)
	{
		if (!player.addItem(new ItemStack(itemSupplier.get())))
		{
			player.spawnAtLocation(itemSupplier.get());
		}
	}
}