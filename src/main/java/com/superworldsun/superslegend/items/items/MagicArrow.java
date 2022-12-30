package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public abstract class MagicArrow extends ArrowItem
{
	public MagicArrow()
	{
		super(new Item.Properties().tab(ItemGroupInit.RESOURCES).stacksTo(1));
	}
	
	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, PlayerEntity player)
	{
		return true;
	}
	
	@Override
	public AbstractArrowEntity createArrow(World world, ItemStack stack, LivingEntity shooter)
	{
		boolean isPlayer = shooter instanceof PlayerEntity;
		boolean hasMana = isPlayer && ManaProvider.get((PlayerEntity) shooter).getMana() >= getManacost();
		boolean inCreative = isPlayer && ((PlayerEntity) shooter).abilities.instabuild;
		
		if (hasMana || inCreative)
		{
			if (hasMana && !inCreative)
			{
				ManaProvider.get((PlayerEntity) shooter).spendMana(getManacost());
				ManaProvider.sync((ServerPlayerEntity) shooter);
			}
			
			return createMagicArrow(world, stack, shooter);
		}
		else
		{
			// If no mana, just shoot regular arrows
			return new ArrowEntity(world, shooter);
		}
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
			boolean hasArrows = player.inventory.contains(new ItemStack(Items.ARROW));
			boolean inCreative = player.abilities.instabuild;
			
			// Fail if no regular arrows in inventory
			if (!inCreative && !hasArrows)
			{
				event.setAction(new ActionResult<ItemStack>(ActionResultType.FAIL, event.getBow()));
			}
		}
	}
	
	@SubscribeEvent
	public static void onArrowLoose(ArrowLooseEvent event)
	{
		ItemStack projectile = event.getEntityLiving().getProjectile(event.getBow());
		
		if (!event.getPlayer().abilities.instabuild && projectile.getItem() instanceof MagicArrow)
		{
			int slotWithArrows = event.getPlayer().inventory.findSlotMatchingUnusedItem(new ItemStack(Items.ARROW));
			event.getPlayer().inventory.getItem(slotWithArrows).shrink(1);
		}
	}
	
	protected float getManacost()
	{
		return 4.0F;
	}
	
	protected abstract AbstractArrowEntity createMagicArrow(World world, ItemStack stack, LivingEntity shooter);
}
