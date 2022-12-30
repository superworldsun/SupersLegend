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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class MagicJarDropEvents {
	private static final int LARGE_JAR_MANA_RESTORATION = 10;
	private static final int JAR_MANA_RESTORATION = 4;
	private static final double LARGE_JAR_DROP_CHANCE = 0.05;
	private static final double JAR_DROP_CHANCE = 0.15;

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof PlayerEntity && event.getEntityLiving() instanceof MonsterEntity) {
			Entity entity = event.getEntity();
			PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
			World level = player.level;
			Random random = new Random();

			if (!level.isClientSide && random.nextDouble() <= JAR_DROP_CHANCE) {
				double xOffset = random.nextFloat() * 0.5F - 0.25F;
				double yOffset = random.nextFloat() * 0.5F;
				double zOffset = random.nextFloat() * 0.5F - 0.25F;
				MagicJarEntity jar = new MagicJarEntity(level, entity.getX() + xOffset, entity.getY() + yOffset, entity.getZ() + zOffset);
				jar.value = JAR_MANA_RESTORATION;
				level.addFreshEntity(jar);
			}

			if (!level.isClientSide && random.nextDouble() <= LARGE_JAR_DROP_CHANCE) {
				double xOffset = random.nextFloat() * 0.5F - 0.25F;
				double yOffset = random.nextFloat() * 0.5F;
				double zOffset = random.nextFloat() * 0.5F - 0.25F;
				LargeMagicJarEntity jar = new LargeMagicJarEntity(level, entity.getX() + xOffset, entity.getY() + yOffset, entity.getZ() + zOffset);
				jar.value = LARGE_JAR_MANA_RESTORATION;
				level.addFreshEntity(jar);
			}
		}
	}
}
