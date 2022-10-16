package com.superworldsun.superslegend.effect;

import com.google.common.collect.ImmutableSet;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.items.MagicCape;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.EffectInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class CloakedEffect extends Effect
{
	public CloakedEffect()
	{
		super(EffectType.BENEFICIAL, 0xB02828);
	}
	
	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier)
	{
		if (livingEntity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) livingEntity;
			boolean hasMana = ManaProvider.get(player).getMana() >= MagicCape.MANA_COST || player.abilities.instabuild;
			boolean hasCape = player.inventory.hasAnyOf(ImmutableSet.of(ItemInit.MAGIC_CAPE.get()));
			
			if (!hasCape)
			{
				player.removeEffect(this);
				return;
			}
			
			if (hasMana)
			{
				if (!player.abilities.instabuild)
				{
					ManaProvider.get(player).spendMana(MagicCape.MANA_COST);
				}
			}
			else
			{
				player.removeEffect(this);
			}
		}
	}
	
	// you can regulate how often it spends mana here
	@Override
	public boolean isDurationEffectTick(int tick, int amplifier)
	{
		return true;
	}
	
	@SubscribeEvent
	public static void onLivingAttacked(LivingAttackEvent event)
	{
		if (event.getEntityLiving().hasEffect(EffectInit.CLOAKED.get()))
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event)
	{
		if (event.getTarget() != null && event.getTarget().hasEffect(EffectInit.CLOAKED.get()))
		{
			((MobEntity) event.getEntityLiving()).setTarget(null);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Pre event)
	{
		if (event.getPlayer().hasEffect(EffectInit.CLOAKED.get()))
		{
			Minecraft client = Minecraft.getInstance();
			
			// if the client player is using lens of truth, he will see cloaked players anyway
			if (!client.player.isUsingItem() || client.player.getItemInHand(client.player.getUsedItemHand()).getItem() != ItemInit.LENS_OF_TRUTH.get())
			{
				// completely disables render of the player (including armor, held items, etc)
				event.setCanceled(true);
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Post event)
	{
		if (event.getPlayer().hasEffect(EffectInit.CLOAKED.get()))
		{
			Minecraft client = Minecraft.getInstance();
			
			// if the client player is using lens of truth, he will see cloaked players anyway
			if (!client.player.isUsingItem() || client.player.getItemInHand(client.player.getUsedItemHand()).getItem() != ItemInit.LENS_OF_TRUTH.get())
			{
				// completely disables render of the player (including armor, held items, etc)
				event.setCanceled(true);
			}
		}
	}
}
