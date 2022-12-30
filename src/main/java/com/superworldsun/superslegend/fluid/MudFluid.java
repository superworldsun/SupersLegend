package com.superworldsun.superslegend.fluid;

import java.util.UUID;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.FluidInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MudFluid {
	private static final ResourceLocation FLOWING_BLOCK_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "block/mud_flowing");
	private static final ResourceLocation SOURCE_BLOCK_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "block/mud_still");
	private static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/block/mud_overlay.png");
	private static final int LEVEL_DECREASE_PER_BLOCK = 2;
	private static final int TICK_RATE = 20;
	private static final int VISCOSITY = 10000;
	private static final int DENSITY = 2048;

	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		Fluid fluid = event.getEntity().level.getFluidState(event.getEntity().blockPosition()).getType();
		UUID modifierId = UUID.fromString("91b846fd-adb1-4035-bbff-1ecdd49825fc");

		if (fluid == FluidInit.MUD_FLOWING.get() || fluid == FluidInit.MUD_SOURCE.get()) {
			addOrReplaceModifier(event.getEntityLiving(), ForgeMod.SWIM_SPEED.get(), modifierId, -0.5F, Operation.MULTIPLY_TOTAL);
		} else {
			removeModifier(event.getEntityLiving(), ForgeMod.SWIM_SPEED.get(), modifierId);
		}
	}

	private static void removeModifier(LivingEntity livingEntity, Attribute attribute, UUID id) {
		ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
		AttributeModifier modifier = attributeInstance.getModifier(id);

		if (modifier != null) {
			attributeInstance.removeModifier(modifier);
		}
	}

	private static void addOrReplaceModifier(LivingEntity livingEntity, Attribute attribute, UUID id, float amount, Operation operation) {
		ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
		AttributeModifier modifier = attributeInstance.getModifier(id);

		if (modifier != null && modifier.getAmount() != amount) {
			attributeInstance.removeModifier(modifier);
			modifier = new AttributeModifier(id, id.toString(), amount, operation);
		} else if (modifier == null) {
			modifier = new AttributeModifier(id, id.toString(), amount, operation);
		}

		if (modifier != null && !attributeInstance.hasModifier(modifier)) {
			attributeInstance.addPermanentModifier(modifier);
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
		Properties properties = new Properties(FluidInit.MUD_SOURCE, FluidInit.MUD_FLOWING, attributes);
		return properties.block(BlockInit.LIQUID_MUD).bucket(ItemInit.MUD_BUCKET).tickRate(TICK_RATE).levelDecreasePerBlock(LEVEL_DECREASE_PER_BLOCK).canMultiply();
	}
}
