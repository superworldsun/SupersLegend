package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ArmorMagicArmor extends NonEnchantArmor {


	public ArmorMagicArmor(EquipmentSlotType slot, Properties properties) {
		super(ArmourInit.magic, slot, properties);
	}

	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "Magic Armor"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants invicibility from rupee"));
		list.add(new StringTextComponent(TextFormatting.RED + "Slows user when rupee are depleted"));
	}



	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (!world.isClientSide) {
			boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().getItem() == ItemInit.MAGIC_ARMOR_CAP.get();
			boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getItem() == ItemInit.MAGIC_ARMOR_TUNIC.get();
			boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().getItem() == ItemInit.MAGIC_ARMOR_LEGGINGS.get();
			boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().getItem() == ItemInit.MAGIC_ARMOR_BOOTS.get();
			if (isHelmetOn & isChestplateOn & isLeggingsOn & isBootsOn) {
				EffectInstance effect = player.getEffect(Effects.DAMAGE_RESISTANCE);
				if (effect != null && effect.getAmplifier() == 100)

					player.addEffect(new EffectInstance(Effect.byId(11), 14, 99, false, false));
				else {
					for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
						ItemStack armorStack = player.inventory.getItem(i);
						if (armorStack.getItem().getItem() == ItemInit.RUPEE.get()) {
							armorStack.shrink(1);
							player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 10, 100, false, false, false));
							break;
						}
					}
				}
			}
			if (isHelmetOn && isChestplateOn && isLeggingsOn && isBootsOn) {
				EffectInstance effect = player.getEffect(Effects.DAMAGE_RESISTANCE);
				if (effect == null) {
					player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10, 3, false, false, false));
				}
			}
			/*if (isHelmetOn && isChestplateOn && isLeggingsOn && isBootsOn) {
				if (player.attackEntityFrom(DamageSource.GENERIC, 1) ) {

					BlockPos currentPos = player.getPosition();
					world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.JAWA, SoundCategory.PLAYERS, 1f, 1f);

					for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
						ItemStack armorStack = player.inventory.getStackInSlot(i);
						if (armorStack.getItem() == ItemList.rupee) {
							armorStack.shrink(12);
							break;
						}
					}
				}
			}*/
		}
	}
}

    //EffectInstance effect = player.getActivePotionEffect(Effects.RESISTANCE);
	//if (effect != null && effect.getAmplifier() == 100)
    
    
    
    //player.getMotion().getX() != 0 && player.getMotion().getY() != 0
    
    //@Override
   // public boolean hasEffect(ItemStack stack) {
        //return false;
   // }
