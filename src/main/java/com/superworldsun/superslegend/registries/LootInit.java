package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.loot.GlobalLootModifier;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LootInit
{
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, SupersLegendMain.MOD_ID);
    
	public static final RegistryObject<GlobalLootModifier.Serializer> FISHING = REGISTRY.register("fishing", GlobalLootModifier.Serializer::new);
	public static final RegistryObject<GlobalLootModifier.Serializer> CREEPER_MUSIC_DISC = REGISTRY.register("creeper_music_disc", GlobalLootModifier.Serializer::new);
	public static final RegistryObject<GlobalLootModifier.Serializer> ABANDONED_MINESHAFT = REGISTRY.register("abandoned_mineshaft", GlobalLootModifier.Serializer::new);
}
