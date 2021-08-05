package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class TagInit
{
	public static final IOptionalNamedTag<EntityType<?>> WEAK_TO_FIRE = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "weak_to_fire"));
	public static final IOptionalNamedTag<EntityType<?>> WEAK_TO_ICE = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "weak_to_ice"));
	public static final IOptionalNamedTag<EntityType<?>> WEAK_TO_LIGHT = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "weak_to_light"));
	public static final IOptionalNamedTag<EntityType<?>> RESISTANT_TO_FIRE = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "resistant_to_fire"));
	public static final IOptionalNamedTag<EntityType<?>> RESISTANT_TO_ICE = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "resistant_to_ice"));
	public static final IOptionalNamedTag<EntityType<?>> RESISTANT_TO_LIGHT = EntityTypeTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "resistant_to_light"));
	
	public static final IOptionalNamedTag<Fluid> POISON = FluidTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "poison"));
	public static final IOptionalNamedTag<Fluid> MUD = FluidTags.createOptional(new ResourceLocation(SupersLegendMain.MOD_ID, "mud"));
}
