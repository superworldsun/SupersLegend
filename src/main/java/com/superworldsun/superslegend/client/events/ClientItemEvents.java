package com.superworldsun.superslegend.client.events;

import java.util.ArrayList;
import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.item.LensOfTruth;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class ClientItemEvents {
	private static final List<LivingEntity> REVEALED_ENTITIES = new ArrayList<>();

	/**
	 * Reveals invisible entities if client player is using the {@link LensOfTruth}
	 */
	@SubscribeEvent
	public static void revealInvisibleEntities(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
		if (!event.getEntity().isInvisible()) return;
		Minecraft client = Minecraft.getInstance();
		Player player = client.player;
		if (player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof LensOfTruth) {
			removeInvisibility(event.getEntity());
		}
	}

	@SubscribeEvent
	public static void restoreEntitiesInvisibility(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
		if (REVEALED_ENTITIES.contains(event.getEntity())) {
			restoreInvisibility(event.getEntity());
		}
	}

	private static void restoreInvisibility(LivingEntity livingEntity) {
		REVEALED_ENTITIES.remove(livingEntity);
		livingEntity.setInvisible(true);
	}

	private static void removeInvisibility(LivingEntity livingEntity) {
		livingEntity.setInvisible(false);
		REVEALED_ENTITIES.add(livingEntity);
	}
}
