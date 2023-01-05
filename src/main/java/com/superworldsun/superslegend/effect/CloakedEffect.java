package com.superworldsun.superslegend.effect;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.mana.ManaHelper;
import com.superworldsun.superslegend.items.items.MagicCape;
import com.superworldsun.superslegend.registries.EffectInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class CloakedEffect extends Effect {
	public CloakedEffect() {
		super(EffectType.BENEFICIAL, 0xB02828);
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
		if (!(livingEntity instanceof PlayerEntity)) {
			return;
		}

		PlayerEntity player = (PlayerEntity) livingEntity;
		boolean hasMana = ManaHelper.hasMana(player, MagicCape.MANA_COST);
		boolean hasCape = player.inventory.contains(new ItemStack(ItemInit.MAGIC_CAPE.get()));

		if (!hasCape || !hasMana) {
			player.removeEffect(this);
			return;
		}

		ManaHelper.spendMana(player, MagicCape.MANA_COST);
	}

	@SubscribeEvent
	public static void onLivingAttacked(LivingAttackEvent event) {
		if (event.getEntityLiving().hasEffect(EffectInit.CLOAKED.get())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
		boolean isTargetCloked = event.getTarget() != null && event.getTarget().hasEffect(EffectInit.CLOAKED.get());

		if (isTargetCloked) {
			MobEntity mobEntity = (MobEntity) event.getEntityLiving();
			mobEntity.setTarget(null);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Pre event) {
		if (event.getPlayer().hasEffect(EffectInit.CLOAKED.get())) {
			Minecraft minecraft = Minecraft.getInstance();
			boolean isClientPlayerUsingLens = minecraft.player.isUsingItem()
					&& minecraft.player.getItemInHand(minecraft.player.getUsedItemHand()).getItem() == ItemInit.LENS_OF_TRUTH.get();

			if (isClientPlayerUsingLens) {
				event.setCanceled(true);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onHandRender(RenderHandEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		boolean isClientPlayerCloaked = minecraft.player.hasEffect(EffectInit.CLOAKED.get());

		if (isClientPlayerCloaked) {
			event.setCanceled(true);
		}
	}
}
