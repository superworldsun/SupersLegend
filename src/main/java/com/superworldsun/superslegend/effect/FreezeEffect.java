package com.superworldsun.superslegend.effect;

import java.util.ArrayList;
import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.layer.FreezeEffectLayer;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SyncFreezeEffectMessage;
import com.superworldsun.superslegend.registries.EffectInit;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.PacketDistributor;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class FreezeEffect extends Effect
{
	public FreezeEffect()
	{
		super(EffectType.HARMFUL, 0xD6FFFF);
	}
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		
		if (entity.hasEffect(EffectInit.FREEZE.get()))
		{
			EffectInstance freezeEffect = entity.getEffect(EffectInit.FREEZE.get());
			
			if (entity.isDeadOrDying() || freezeEffect.getDuration() == 0)
			{
				entity.removeEffect(EffectInit.FREEZE.get());
				return;
			}
			
			if (!entity.level.isClientSide)
				NetworkDispatcher.networkChannel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new SyncFreezeEffectMessage(entity));
			
			entity.baseTick();
			entity.move(MoverType.SELF, entity.getDeltaMovement());
			entity.setDeltaMovement(entity.getDeltaMovement().scale(0.8));
			entity.move(MoverType.SELF, new Vector3d(0, -0.4, 0));
			event.setCanceled(true);
		}
	}
	
	private static List<LivingRenderer<?, ?>> modified_renderers = new ArrayList<>();
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onLivingRender(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event)
	{
		if (!event.getEntity().hasEffect(EffectInit.FREEZE.get()))
			return;
		
		event.getEntity().xOld = event.getEntity().getX();
		event.getEntity().yOld = event.getEntity().getY();
		event.getEntity().zOld = event.getEntity().getZ();
		event.getEntity().yRotO = event.getEntity().yRot;
		event.getEntity().calculateEntityAnimation(event.getEntity(), true);
		
		if (!modified_renderers.contains(event.getRenderer()))
		{
			event.getRenderer().addLayer(new FreezeEffectLayer(event.getRenderer()));
			modified_renderers.add(event.getRenderer());
		}
	}
}
