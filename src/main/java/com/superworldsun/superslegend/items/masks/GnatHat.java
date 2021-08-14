package com.superworldsun.superslegend.items.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.registries.ArmourInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GnatHat extends ArmorItem implements IEntityResizer
{
	public GnatHat()
	{
		super(ArmourInit.gnathat, EquipmentSlotType.HEAD, new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public float getScale(PlayerEntity player)
	{
		return 0.2F;
	}
}
