package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.Random;

public class HoverBoots extends NonEnchantArmor {


	public HoverBoots(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "No road needed"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Sprint To Hover over Gaps"));


	}

	//todo add potion back in
	/*@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{


		boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemList.hover_boots);

		if(isBootsOn && player.isSprinting() && player.isOnGround())
		{

			player.addEffect(new EffectInstance(PotionList.hover_boots_effect, 19, 0, true, false));

		}
		if(player.isSprinting() && !player.isOnGround())
		{
			EffectInstance effect = player.getEffect(PotionList.hover_boots_effect);
			if (effect != null)

			{
				Vector3d v = player.getDeltaMovement();
				player.setDeltaMovement(v.x, v.y * -0.1D, v.z);

				if(player.tickCount % 2 == 0) {
					BlockPos currentPos = player.blockPosition();
					world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.HOVER_BOOTS, SoundCategory.PLAYERS, 1f, 1f);
				}


				if(player.tickCount % 4 == 0){
				Random rand = player.level.random;
				for (int i = 0; i < 45; i++) {
					player.level.addParticle(ParticleTypes.CLOUD,
							player.getX() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 0,
							player.getY() + rand.nextFloat() * 0 - 0,
							player.getZ() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 0,
							0, 0, 0);
					}
				}

			}

		}
		if(isBootsOn && !player.isSprinting())
		{

			player.removeEffect(PotionList.hover_boots_effect);

		}

	}*/
}
        