package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.items.material.MaskMaterial;
import com.superworldsun.superslegend.util.IExtendedPlayer;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HoverBoots extends ArmorItem
{
	public HoverBoots(Properties properties)
	{
		// I'm pretty sure uou will want to use other material
		super(MaskMaterial.INSTANCE, EquipmentSlotType.FEET, properties);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{
		IExtendedPlayer eplayer = (IExtendedPlayer) player;
		BlockPos pos = player.blockPosition().below();
		BlockState block = world.getBlockState(pos);
		// Not sure what to put here
		boolean flag = /* !block.blocksMovement() || */block.getSlipperiness(world, pos, player) > 0.6F || block.getBlock() instanceof SoulSandBlock;
		
		if (player.getDeltaMovement().y < 0.0D && eplayer.increaseHoverTime() < 40)
		{
			player.setBoundingBox(player.getBoundingBox().move(0, -player.getDeltaMovement().y, 0));
			player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);
			player.fallDistance = 0.0F;
			
			if (eplayer.getHoverTime() % 3 == 0)
			{
				world.addParticle(ParticleTypes.CLOUD, player.position().x, player.position().y - 1, player.position().z, -player.getDeltaMovement().x,
						player.getDeltaMovement().y, -player.getDeltaMovement().z);
			}
		}
		else if (eplayer.getHoverTime() > 0)
		{
			eplayer.setHoverTime(0);
			player.setSprinting(false);
			
			if (world.isClientSide && Minecraft.getInstance().options.keySprint.isDown())
			{
				KeyBinding.set(Minecraft.getInstance().options.keySprint.getKey(), false);
			}
		}
	}
}
