package com.superworldsun.superslegend.items.masks;

import com.superworldsun.superslegend.cooldowns.CooldownsProvider;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class BlastMask extends NonEnchantArmor
{
	public BlastMask(Properties properties)
	{
		super(ArmourInit.blastmask, EquipmentSlotType.HEAD, properties);
	}
	
	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GRAY + "Bomb Blastic"));
	}
	
	public static void abilityUsed(World world, PlayerEntity player)
	{
		// Do nothing if on cooldown
		if (CooldownsProvider.get(player).hasCooldown(ItemInit.MASK_BLASTMASK.get()))
		{
			return;
		}
		
		Vector3d explosionPos = player.getEyePosition(1.0F).add(player.getLookAngle().multiply(0.5D, 0.5D, 0.5D));
		world.explode(player, explosionPos.x, explosionPos.y, explosionPos.z, 2.0F, Mode.BREAK);
		player.hurt(DamageSource.explosion(player), 2.0F);
		// 200 ticks are 10 seconds
		CooldownsProvider.get(player).setCooldown(ItemInit.MASK_BLASTMASK.get(), 200);
	}
}
