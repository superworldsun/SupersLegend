package com.superworldsun.superslegend.items;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public abstract class ExtendedShieldItem extends ShieldItem
{
	public ExtendedShieldItem(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity)
	{
		return true;
	}
	
	@SubscribeEvent
	public static void onLivingAttacked(LivingAttackEvent event)
	{
		if (!(event.getEntity() instanceof PlayerEntity))
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) event.getEntity();
		
		if (!player.isBlocking())
		{
			return;
		}
		
		Item currentItem = player.getItemInHand(player.getUsedItemHand()).getItem();
		
		if (!(currentItem instanceof ExtendedShieldItem))
		{
			return;
		}
		
		ExtendedShieldItem shield = (ExtendedShieldItem) currentItem;
		LivingEntity attacker = null;
		Entity projectile = null;
		
		if (event.getSource().getEntity() instanceof LivingEntity)
		{
			attacker = (LivingEntity) event.getSource().getEntity();
		}
		
		if (event.getSource().getDirectEntity() != null && event.getSource().getDirectEntity() != attacker)
		{
			projectile = event.getSource().getDirectEntity();
		}
		
		if (isDamageBlocked(event.getSource(), event.getEntityLiving()))
		{
			shield.onShieldBlock(player.level, player, attacker, projectile, event.getSource());
		}
	}
	
	public static boolean isDamageBlocked(DamageSource damage, LivingEntity target)
	{
		Entity entity = damage.getDirectEntity();
		boolean flag = false;
		
		if (entity instanceof AbstractArrowEntity)
		{
			AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity) entity;
			
			if (abstractarrowentity.getPierceLevel() > 0)
			{
				flag = true;
			}
		}
		
		if (!damage.isBypassArmor() && target.isBlocking() && !flag)
		{
			Vector3d vector3d2 = damage.getSourcePosition();
			
			if (vector3d2 != null)
			{
				Vector3d vector3d = target.getViewVector(1.0F);
				Vector3d vector3d1 = vector3d2.vectorTo(target.position()).normalize();
				vector3d1 = new Vector3d(vector3d1.x, 0.0D, vector3d1.z);
				
				if (vector3d1.dot(vector3d) < 0.0D)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected abstract void onShieldBlock(World level, PlayerEntity player, @Nullable LivingEntity attacker, @Nullable Entity projectile, DamageSource damage);
}
