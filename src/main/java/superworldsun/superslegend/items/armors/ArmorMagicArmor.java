package superworldsun.superslegend.items.armors;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class ArmorMagicArmor extends NonEnchantArmor {
	public ArmorMagicArmor(String name, EquipmentSlotType slot) {
		super(ArmourMaterialList.magic, slot, new Properties().group(SupersLegend.supers_legend));
		setRegistryName(SupersLegend.modid, name);
	}

	public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
		super.addInformation(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "magic armor"));
		list.add(new StringTextComponent(TextFormatting.RED + "grants invicibility from rupee"));
	}



	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (!world.isRemote) {
			boolean isHelmetOn = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.magic_armor_cap);
			boolean isChestplateOn = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.magic_armor_tunic);
			boolean isLeggingsOn = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem().equals(ItemList.magic_armor_leggings);
			boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.magic_armor_boots);
			if (isHelmetOn & isChestplateOn & isLeggingsOn & isBootsOn) {
				EffectInstance effect = player.getActivePotionEffect(Effects.RESISTANCE);
				if (effect != null && effect.getAmplifier() == 100)

					player.addPotionEffect(new EffectInstance(Effect.get(11), 14, 99, false, false));
				else {
					for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
						ItemStack armorStack = player.inventory.getStackInSlot(i);
						if (armorStack.getItem() == ItemList.rupee) {
							armorStack.shrink(1);
							player.addPotionEffect(new EffectInstance(Effect.get(11), 10, 100, false, false));
							break;
						}
					}
				}
			}
			if (isHelmetOn && isChestplateOn && isLeggingsOn && isBootsOn) {
				EffectInstance effect = player.getActivePotionEffect(Effects.RESISTANCE);
				if (effect == null) {
					player.addPotionEffect(new EffectInstance(Effect.get(2), 10, 4, false, false));
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
