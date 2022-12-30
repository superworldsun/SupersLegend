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
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public abstract class PoisonFluid {
	private static final ResourceLocation FLOWING_BLOCK_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "block/poison_flowing");
	private static final ResourceLocation SOURCE_BLOCK_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "block/poison_still");
	private static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/block/poison_overlay.png");
	private static final int VISCOSITY = 1024;
	private static final int DENSITY = 512;
	private static final int EFFECT_DURATION = 210;
	private static final int MAXIMUM_DURATION_FOR_REAPPLYMENT = 185;

	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		Fluid fluid = event.getEntity().level.getFluidState(event.getEntity().blockPosition()).getType();

		if (fluid == FluidInit.POISON_FLOWING.get() || fluid == FluidInit.POISON_SOURCE.get()) {
			if (!event.getEntityLiving().hasEffect(Effects.POISON)
					|| event.getEntityLiving().getEffect(Effects.POISON).getDuration() < MAXIMUM_DURATION_FOR_REAPPLYMENT) {
				event.getEntityLiving().addEffect(new EffectInstance(Effects.POISON, EFFECT_DURATION));
			}
		}
	}

	public static class Source extends CustomFluid.Source {
		public Source() {
			super(OVERLAY_TEXTURE, buildProperties());
		}
	}

	public static class Flowing extends CustomFluid.Flowing {
		public Flowing() {
			super(OVERLAY_TEXTURE, buildProperties());
		}
	}

	private static Properties buildProperties() {
		FluidAttributes.Builder attributes = FluidAttributes.builder(SOURCE_BLOCK_TEXTURE, FLOWING_BLOCK_TEXTURE).density(DENSITY).viscosity(VISCOSITY);
		Properties properties = new Properties(FluidInit.POISON_SOURCE, FluidInit.POISON_FLOWING, attributes);
		return properties.block(BlockInit.LIQUID_POISON).bucket(ItemInit.POISON_BUCKET).canMultiply();
	}
}
