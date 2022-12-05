package com.superworldsun.superslegend.items.bags;

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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
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
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		if (!worldIn.isClientSide)
		{
			BlockPos currentPos = playerIn.blockPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ARMOR_EQUIP_ELYTRA, SoundCategory.PLAYERS, 1f, 1f);
			INamedContainerProvider containerProvider = new INamedContainerProvider()
			{
				@Override
				public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player)
				{
					return getContainer(windowId, playerInventory, player, handIn);
				}

				@Override
				public ITextComponent getDisplayName()
				{
					return playerIn.getItemInHand(handIn).getDisplayName();
				}
			};

			NetworkHooks.openGui((ServerPlayerEntity) playerIn, containerProvider, packetBuffer -> packetBuffer.writeEnum(handIn));
		}

		return ActionResult.success(playerIn.getItemInHand(handIn));
	}

	public Container getContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, Hand handIn)
	{
		return new BagContainer(windowId, player.inventory, handIn);
	}
	
	public abstract boolean canHoldItem(ItemStack stack);
}
