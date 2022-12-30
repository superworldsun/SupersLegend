package com.superworldsun.superslegend.events;

import java.util.Random;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.HeartEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class HeartDropEvents {
	private static final int HEART_HEALING = 1;
	private static final double HEART_DROP_CHANCE = 0.30;

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		if (event.getSource().getEntity() instanceof PlayerEntity && event.getEntityLiving() instanceof MonsterEntity) {
			Entity entity = event.getEntity();
			PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
			World level = player.level;
			Random random = new Random();

			if (!level.isClientSide && random.nextDouble() <= HEART_DROP_CHANCE) {
				double xOffset = random.nextFloat() * 0.5F - 0.25F;
				double yOffset = random.nextFloat() * 0.5F;
				double zOffset = random.nextFloat() * 0.5F - 0.25F;
				HeartEntity heart = new HeartEntity(level, entity.getX() + xOffset, entity.getY() + yOffset, entity.getZ() + zOffset);
				heart.value = HEART_HEALING;
				level.addFreshEntity(heart);
			}
		}
	}
}
