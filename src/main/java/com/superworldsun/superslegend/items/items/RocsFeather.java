package com.superworldsun.superslegend.items.items;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IJumpingEntity;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.DoubleJumpMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class RocsFeather extends Item
{
	public RocsFeather(Properties properties)
	{
		super(properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "Holding this will grant a second jump"));
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event)
	{
		if (event.getEntityLiving().getMainHandItem().getItem() instanceof RocsFeather)
		{
			event.setDistance(event.getDistance() - 2F);
		}
	}
	
	private static boolean canDoubleJump;
	private static boolean hasReleasedJumpKey;
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		Minecraft client = Minecraft.getInstance();
		ClientPlayerEntity player = client.player;
		
		if (event.phase == TickEvent.Phase.END && player != null && player.input != null)
		{
			if ((player.isOnGround() || player.onClimbable()) && !player.isInWater())
			{
				hasReleasedJumpKey = false;
				canDoubleJump = true;
			}
			else if (!player.input.jumping)
			{
				hasReleasedJumpKey = true;
			}
			else if (!player.abilities.flying && canDoubleJump && hasReleasedJumpKey)
			{
				canDoubleJump = false;
				
				if (player.getMainHandItem().getItem() instanceof RocsFeather)
				{
					NetworkDispatcher.networkChannel.sendToServer(new DoubleJumpMessage());
					((IJumpingEntity) player).doubleJump();
				}
				else if (player.getOffhandItem().getItem() instanceof RocsFeather)
				{
					NetworkDispatcher.networkChannel.sendToServer(new DoubleJumpMessage());
					((IJumpingEntity) player).doubleJump();
				}
			}
		}
	}
}