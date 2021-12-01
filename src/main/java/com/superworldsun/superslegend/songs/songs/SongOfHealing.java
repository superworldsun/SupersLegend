package com.superworldsun.superslegend.songs.songs;

import java.util.List;
import java.util.Map;

import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class SongOfHealing extends OcarinaSong
{
	public SongOfHealing()
	{
		super("lrdlrd");
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		double radius = 10D;
		List<Entity> entities = level.getEntities((Entity) null, player.getBoundingBox().inflate(radius), entity -> player.distanceTo(entity) < radius);
		entities.forEach(entity ->
		{
			if (entity instanceof LivingEntity)
			{
				LivingEntity livingEntity = (LivingEntity) entity;
				livingEntity.removeEffect(Effects.POISON);
				livingEntity.removeEffect(Effects.WITHER);
				
				entity.getArmorSlots().forEach(stack ->
				{
					Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
					enchantments.entrySet().removeIf(entry -> entry.getKey().isCurse());
					EnchantmentHelper.setEnchantments(enchantments, stack);
				});
			}
		});
	}
}
