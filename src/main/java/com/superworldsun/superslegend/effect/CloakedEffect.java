package com.superworldsun.superslegend.effect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.items.MagicCape;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.EffectInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class CloakedEffect extends Effect
{
	public CloakedEffect()
	{
		super(EffectType.BENEFICIAL, 0xB02828);
	}
	
	@Override
	public void addAttributeModifiers(LivingEntity livingEntity, AttributeModifierManager attributeModifierManager, int amplifier)
	{
		livingEntity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false, false));
		livingEntity.addEffect(new EffectInstance(Effects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false, false));
	}
	
	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier)
	{
		if (livingEntity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) livingEntity;
			boolean hasMana = ManaProvider.get(player).getMana() >= MagicCape.MANA_COST || player.abilities.instabuild;
			
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
				player.removeEffect(Effects.DAMAGE_RESISTANCE);
				player.removeEffect(Effects.INVISIBILITY);
			}
		}
	}
	
	// you can regulate how often it spends mana here
	@Override
	public boolean isDurationEffectTick(int tick, int amplifier)
	{
		return true;
	}
	
	@Override
	public void removeAttributeModifiers(LivingEntity livingEntity, AttributeModifierManager attributeModifierManager, int amplifier)
	{
		livingEntity.removeEffect(Effects.DAMAGE_RESISTANCE);
		livingEntity.removeEffect(Effects.INVISIBILITY);
	}
	
	@OnlyIn(Dist.CLIENT)
	private static Method render_shadow;
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Pre event)
	{
		PlayerEntity player = event.getPlayer();
		
		if (player.hasEffect(EffectInit.CLOAKED.get()))
		{
			// completely disables render of the player (including armor, held items, etc)
			event.setCanceled(true);
			renderShadowManually(player, event.getRenderer(), event.getPartialRenderTick(), event.getMatrixStack(), event.getBuffers(), event.getLight());
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	private static void renderShadowManually(PlayerEntity player, PlayerRenderer playerRenderer, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light)
	{
		if (render_shadow == null)
		{
			render_shadow = ObfuscationReflectionHelper.findMethod(EntityRendererManager.class, "func_229096_a_", MatrixStack.class, IRenderTypeBuffer.class, Entity.class, float.class, float.class,
					IWorldReader.class, float.class);
		}
		
		EntityRendererManager rendererManager = Minecraft.getInstance().getEntityRenderDispatcher();
		double distance = rendererManager.distanceToSqr(player.getX(), player.getY(), player.getZ());
		float shadowStrength = ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, playerRenderer, "field_76987_f");
		float shadowRadius = ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, playerRenderer, "field_76989_e");
		float shadowOpacity = (float) ((1D - distance / 256D) * shadowStrength);
		
		if (shadowOpacity > 0.0F)
		{
			try
			{
				render_shadow.invoke(null, matrixStack, buffer, player, shadowOpacity, partialTick, player.level, shadowRadius);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
	}
}
