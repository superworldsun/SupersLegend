package com.superworldsun.superslegend.items.items;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.RocksFeatherMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
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
	
	public void jump(PlayerEntity player)
	{
		double jumpStrength = 0.5;
		
		if (player.hasEffect(Effects.JUMP))
		{
			jumpStrength += 0.1 * (player.getEffect(Effects.JUMP).getAmplifier() + 1);
		}
		
		Vector3d movementVector = player.getDeltaMovement();
		Vector3d jumpVector = new Vector3d(0, jumpStrength - movementVector.y, 0);
		player.setDeltaMovement(movementVector.add(jumpVector));
		player.hasImpulse = true;
		player.awardStat(Stats.JUMP);
		ForgeHooks.onLivingJump(player);
		
		if (player.isSprinting())
		{
			player.causeFoodExhaustion(0.2F);
		}
		else
		{
			player.causeFoodExhaustion(0.05F);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "Holding this will grant better ground mobility"));
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event)
	{
		if (event.getEntityLiving().getMainHandItem().getItem() instanceof RocsFeather)
		{
			event.setDistance(event.getDistance() - 3F);
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
					RocsFeather rocsFeatherItem = (RocsFeather) player.getMainHandItem().getItem();
					NetworkDispatcher.networkChannel.sendToServer(new RocksFeatherMessage());
					rocsFeatherItem.jump(player);
				}
			}
		}
	}
}