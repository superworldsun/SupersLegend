package io.superworldsun.superslegend.items.armor;

import io.superworldsun.superslegend.SupersLegend;
import io.superworldsun.superslegend.init.ItemList;
import io.superworldsun.superslegend.util.IHasModel;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmorBase extends ItemArmor implements IHasModel{

	public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(SupersLegend.tabSupersLegend);
		
		ItemList.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		SupersLegend.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
