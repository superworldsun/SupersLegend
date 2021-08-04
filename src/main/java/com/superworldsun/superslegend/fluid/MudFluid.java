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
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public abstract class MudFluid extends ForgeFlowingFluid
{
	private static final UUID MUD_MODIFIER_ID = UUID.fromString("91b846fd-adb1-4035-bbff-1ecdd49825fc");
	
	protected MudFluid()
	{
		super(buildProperties());
	}
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event)
	{
		Fluid fluid = event.getEntity().level.getFluidState(event.getEntity().blockPosition()).getType();
		
		if (fluid == FluidInit.MUD_FLOWING.get() || fluid == FluidInit.MUD_SOURCE.get())
		{
			addOrReplaceModifier(event.getEntityLiving(), ForgeMod.SWIM_SPEED.get(), MUD_MODIFIER_ID, -0.5F, Operation.MULTIPLY_TOTAL);
		}
		else
		{
			removeModifier(event.getEntityLiving(), ForgeMod.SWIM_SPEED.get(), MUD_MODIFIER_ID);
		}
	}
	
	private static void removeModifier(LivingEntity livingEntity, Attribute attribute, UUID id)
	{
		ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
		AttributeModifier modifier = attributeInstance.getModifier(id);
		
		if (modifier != null)
		{
			attributeInstance.removeModifier(modifier);
		}
	}
	
	private static void addOrReplaceModifier(LivingEntity livingEntity, Attribute attribute, UUID id, float amount, Operation operation)
	{
		ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
		AttributeModifier modifier = attributeInstance.getModifier(id);
		
		if (modifier != null && modifier.getAmount() != amount)
		{
			attributeInstance.removeModifier(modifier);
			modifier = new AttributeModifier(id, id.toString(), amount, operation);
		}
		else if (modifier == null)
		{
			modifier = new AttributeModifier(id, id.toString(), amount, operation);
		}
		
		if (modifier != null && !attributeInstance.hasModifier(modifier))
		{
			attributeInstance.addPermanentModifier(modifier);
		}
	}
	
	public static class Source extends ForgeFlowingFluid.Source
	{
		public Source()
		{
			super(buildProperties());
		}
	}
	
	public static class Flowing extends ForgeFlowingFluid.Flowing
	{
		public Flowing()
		{
			super(buildProperties());
		}
	}
	
	private static Properties buildProperties()
	{
		ResourceLocation sourceTexture = new ResourceLocation(SupersLegendMain.MOD_ID, "block/mud_still");
		ResourceLocation flowingTexture = new ResourceLocation(SupersLegendMain.MOD_ID, "block/mud_flowing");
		FluidAttributes.Builder attributes = FluidAttributes.builder(sourceTexture, flowingTexture).density(2048).viscosity(10000);
		return new Properties(FluidInit.MUD_SOURCE, FluidInit.MUD_FLOWING, attributes).block(BlockInit.LIQUID_MUD).bucket(ItemInit.MUD_BUCKET).tickRate(20)
				.levelDecreasePerBlock(2);
	}
}
