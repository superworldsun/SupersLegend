package com.superworldsun.superslegend.fluid;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.FluidInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.fluid.Fluid;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public abstract class PoisonFluid extends CustomFluid
{
	private static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/block/poison_overlay.png");
	
	protected PoisonFluid()
	{
		super(OVERLAY_TEXTURE, buildProperties());
	}
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		Fluid fluid = event.getEntity().level.getFluidState(event.getEntity().blockPosition()).getType();
		
		if (fluid == FluidInit.POISON_FLOWING.get() || fluid == FluidInit.POISON_SOURCE.get())
		{
			if (!event.getEntityLiving().hasEffect(Effects.POISON) || event.getEntityLiving().getEffect(Effects.POISON).getDuration() < 185)
			{
				event.getEntityLiving().addEffect(new EffectInstance(Effects.POISON, 210));
			}
		}
	}
	
	public static class Source extends CustomFluid.Source
	{
		public Source()
		{
			super(OVERLAY_TEXTURE, buildProperties());
		}
	}
	
	public static class Flowing extends CustomFluid.Flowing
	{
		public Flowing()
		{
			super(OVERLAY_TEXTURE, buildProperties());
		}
	}
	
	private static Properties buildProperties()
	{
		ResourceLocation sourceTexture = new ResourceLocation(SupersLegendMain.MOD_ID, "block/poison_still");
		ResourceLocation flowingTexture = new ResourceLocation(SupersLegendMain.MOD_ID, "block/poison_flowing");
		FluidAttributes.Builder attributes = FluidAttributes.builder(sourceTexture, flowingTexture).density(512).viscosity(1024);
		return new Properties(FluidInit.POISON_SOURCE, FluidInit.POISON_FLOWING, attributes).block(BlockInit.LIQUID_POISON).bucket(ItemInit.POISON_BUCKET)
				.canMultiply();
	}
}
