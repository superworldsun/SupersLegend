package com.superworldsun.superslegend.events;

import java.util.Random;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.LargeMagicJarEntity;
import com.superworldsun.superslegend.entities.MagicJarEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class MagicJarDropEvents
{
	@SubscribeEvent
	public static void onEntityKilledSpawnMagicJar(LivingDeathEvent event)
	{
		Random random = new Random();
		// Check if player killed the entity
		// Check if entity being killed is MonsterEntity
		if (event.getSource().getEntity() instanceof PlayerEntity && event.getEntityLiving() instanceof MonsterEntity)
		{
			Entity entity = event.getEntity();
			PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
			World level = player.level;
			
			// Appear with 15% probability
			if (!level.isClientSide && new Random().nextDouble() <= 0.15)
			{
				double d0 = (double) (random.nextFloat() * 0.5F) + 0.25D;
				double d1 = (double) (random.nextFloat() * 0.5F) + 0.25D;
				double d2 = (double) (random.nextFloat() * 0.5F) + 0.25D;
				MagicJarEntity magicJarEntity = new MagicJarEntity(level, entity.getX() + d0, entity.getY() + d1, entity.getZ() + d2);
				// Amount of mana restore.
				magicJarEntity.value = 4;
				level.addFreshEntity(magicJarEntity);
			}
			
			// Appear with 5% probability
			if (!level.isClientSide && new Random().nextDouble() <= 0.05)
			{
				double d0 = (double) (random.nextFloat() * 0.5F) + 0.25D;
				double d1 = (double) (random.nextFloat() * 0.5F) + 0.25D;
				double d2 = (double) (random.nextFloat() * 0.5F) + 0.25D;
				LargeMagicJarEntity largeMagicJarEntity = new LargeMagicJarEntity(level, entity.getX() + d0, entity.getY() + d1, entity.getZ() + d2);
				// Amount of mana restore.
				largeMagicJarEntity.value = 10;
				level.addFreshEntity(largeMagicJarEntity);
			}
		}
	}
}
