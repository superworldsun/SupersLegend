package com.superworldsun.superslegend.items.items;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class Lantern extends Item
{
	private static final HashMap<UUID, BlockPos> LIT_BLOCKS = new HashMap<>();
	
	public Lantern(Properties properties)
	{
		super(properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GREEN + "Provides light on held"));
		list.add(new StringTextComponent(TextFormatting.RED + "Runs out of fuel, use carefully"));
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity user, int tick, boolean p_77663_5_)
	{
		for (ItemStack item : user.getHandSlots())
		{
			if (item == stack)
			{
				if (stack.getDamageValue() < stack.getMaxDamage())
				{
					stack.setDamageValue(stack.getDamageValue() + 1);
				}
				
				if (stack.getDamageValue() == stack.getMaxDamage())
				{
				}
			}
		}
		
		if (user instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) user;
			
			if (player.getMainHandItem() == stack)
			{
				if (stack.getDamageValue() == stack.getMaxDamage())
				{
					player.setItemInHand(Hand.MAIN_HAND, new ItemStack(ItemInit.EXTINGUISHEDLANTERN.get()));
				}
			}
			
			if (player.getOffhandItem() == stack)
			{
				if (stack.getDamageValue() == stack.getMaxDamage())
				{
					player.setItemInHand(Hand.OFF_HAND, new ItemStack(ItemInit.EXTINGUISHEDLANTERN.get()));
				}
			}
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity)
	{
		return super.onEntityItemUpdate(stack, entity);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
	{
		return false;
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		PlayerEntity player = minecraft.player;
		
		if (player == null)
		{
			return;
		}
		
		boolean shouldRemove = true;
		
		for (ItemStack stack : player.getHandSlots())
		{
			if (stack.getItem() instanceof Lantern)
			{
				if (stack.getDamageValue() != stack.getMaxDamage())
				{
					if (!player.isInWater())
					{
						shouldRemove = false;
						
						if (!LIT_BLOCKS.containsValue(player.blockPosition()))
						{
							if (LIT_BLOCKS.containsKey(player.getUUID()))
							{
								LIT_BLOCKS.forEach((uuid, blockPos) -> player.level.getLightEngine().checkBlock(blockPos));
								LIT_BLOCKS.replace(player.getUUID(), player.blockPosition());
							}
							else
							{
								LIT_BLOCKS.put(player.getUUID(), player.blockPosition());
							}
							
							player.level.getLightEngine().onBlockEmissionIncrease(player.blockPosition(), 10);
						}
					}
				}
			}
		}
		
		if (shouldRemove)
		{
			if (LIT_BLOCKS.containsKey(player.getUUID()))
			{
				LIT_BLOCKS.forEach((uuid, blockPos) -> player.level.getLightEngine().checkBlock(blockPos));
			}
		}
	}
}
