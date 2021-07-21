package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.mana.ManaProvider;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class MagicArrow extends ArrowItem
{
	public MagicArrow(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, PlayerEntity player)
	{
		return true;
	}
	
	@SubscribeEvent
	public static void onArrowNock(ArrowNockEvent event)
	{
		// Works only for players
		if (!(event.getEntity() instanceof PlayerEntity))
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) event.getEntity();
		ItemStack projectile = event.getEntityLiving().getProjectile(event.getBow());
		
		if (projectile.getItem() instanceof MagicArrow)
		{
			float manacost = ((MagicArrow) projectile.getItem()).getManacost();
			
			// Fail if not enough mana or no regular arrows in inventory
			if (ManaProvider.get(player).getMana() < manacost || !player.inventory.contains(new ItemStack(Items.ARROW)))
			{
				event.setAction(new ActionResult<ItemStack>(ActionResultType.FAIL, event.getBow()));
			}
		}
	}
	
	@SubscribeEvent
	public static void onArrowLoose(ArrowLooseEvent event)
	{
		ItemStack projectile = event.getEntityLiving().getProjectile(event.getBow());
		
		if (projectile.getItem() instanceof MagicArrow)
		{
			float manacost = ((MagicArrow) projectile.getItem()).getManacost();
			ManaProvider.get(event.getPlayer()).spendMana(manacost);
			int slotWithArrows = event.getPlayer().inventory.findSlotMatchingUnusedItem(new ItemStack(Items.ARROW));
			event.getPlayer().inventory.getItem(slotWithArrows).shrink(1);
		}
	}
	
	protected float getManacost()
	{
		return 1.0F;
	}
}
