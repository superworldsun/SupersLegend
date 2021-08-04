package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.container.BagContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class BagItem extends Item
{
	public BagItem(Properties properties)
	{
		super(properties.stacksTo(1));
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		if (!world.isClientSide)
		{
			INamedContainerProvider containerProvider = new INamedContainerProvider()
			{
				@Override
				public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player)
				{
					return new BagContainer(windowId, player.inventory, hand);
				}
				
				@Override
				public ITextComponent getDisplayName()
				{
					return player.getItemInHand(hand).getDisplayName();
				}
			};
			
			NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, packetBuffer -> packetBuffer.writeEnum(hand));
		}
		
		return ActionResult.success(player.getItemInHand(hand));
	}
	
	public abstract boolean canHoldItem(ItemStack stack);
}
