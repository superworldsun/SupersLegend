package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.IHoveringEntity;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class HoverBoots extends NonEnchantArmor
{
	public HoverBoots(Properties properties)
	{
		super(ArmourInit.hoverboots, EquipmentSlotType.FEET, properties);
	}
	
	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "No road needed"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Sprint To Hover over Gaps"));
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		// Prevent applying changes 2 times per tick
		if (event.phase == Phase.START)
		{
			return;
		}
		
		// Only if we have boots
		if (event.player.getItemBySlot(EquipmentSlotType.FEET).getItem() != ItemInit.HOVER_BOOTS.get())
		{
			return;
		}
		
		IHoveringEntity hoveringPlayer = (IHoveringEntity) event.player;
		
		// 40 ticks are 2 seconds
		if (hoveringPlayer.increaseHoverTime() < 40)
		{
			double motionY = event.player.getDeltaMovement().y;
			
			if (motionY < 0 && event.player.getY() <= hoveringPlayer.getHoverHeight() && !event.player.isOnGround())
			{
				// Prevent movement downwards
				event.player.setDeltaMovement(event.player.getDeltaMovement().x, 0, event.player.getDeltaMovement().z);
				// Reset fall distance, we are not falling
				event.player.fallDistance = 0;
			}
			
			// Prevent falling
			if (event.player.getY() < hoveringPlayer.getHoverHeight())
			{
				event.player.setBoundingBox(event.player.getBoundingBox().move(0, hoveringPlayer.getHoverHeight() - event.player.getY(), 0));
			}
		}
		
		// If we are on ground
		if (event.player.isOnGround())
		{
			// Reset timer for boots
			hoveringPlayer.setHoverTime(0);
			hoveringPlayer.setHoverHeight((int) Math.round(event.player.getY()));
		}
	}
}
