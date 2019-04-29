package io.superworldsun.superslegend.items.armor;

import io.superworldsun.superslegend.SupersLegend;
import io.superworldsun.superslegend.init.ItemList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ZoraArmor extends ArmorBase {

	public ZoraArmor(String name, ItemArmor.ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		this.setCreativeTab(SupersLegend.tabSupersLegend);
		this.setMaxStackSize(1);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		/*
		 * Don't run this on client side
		 */
		if (!world.isRemote)
		{
			if (itemStack.getItem() == ItemList.ZORA_TUNIC)
			{
				PotionEffect wbEffect = player.getActivePotionEffect(MobEffects.WATER_BREATHING);
				if (wbEffect == null || wbEffect.getDuration() < 180) {
					SupersLegend.logger.info("Zora tunic adding water breathing effect");
					player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 200, 1));
				}
			}
			else if (itemStack.getItem() == ItemList.ZORA_BOOTS)
			{
				PotionEffect slowEffect = player.getActivePotionEffect(MobEffects.SLOWNESS);
				if (slowEffect == null || slowEffect.getDuration() < 180) {
					SupersLegend.logger.info("Zora boots adding slowness effect");
					player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
				}
			}
		}
	}
}