package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.AttributeInit;

import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class TemperatureEvents
{
	private static final DamageSource HEAT_DAMAGE = new DamageSource("heat").bypassArmor();
	private static final DamageSource COLD_DAMAGE = new DamageSource("cold").bypassArmor();
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START)
		{
			return;
		}
		
		if (event.player.tickCount % 20 != 0)
		{
			return;
		}
		
		BlockPos playerPos = event.player.blockPosition();
		Biome currentBiome = event.player.level.getBiome(playerPos);
		float temperature = currentBiome.getTemperature(playerPos);
		double coldResistance = event.player.getAttributeValue(AttributeInit.COLD_RESISTANCE.get());
		double heatResistance = event.player.getAttributeValue(AttributeInit.HEAT_RESISTANCE.get());
		
		if (coldResistance < 1.0D && temperature < 0.0F)
		{
			if (Math.abs(temperature) > coldResistance)
			{
				event.player.hurt(COLD_DAMAGE, 1.0F);
			}
		}
		else if (heatResistance < 1.0D && temperature > 1.0F)
		{
			if (Math.abs(temperature) > heatResistance)
			{
				event.player.hurt(HEAT_DAMAGE, 1.0F);
			}
		}
	}
}
