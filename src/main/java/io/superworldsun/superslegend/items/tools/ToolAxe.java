package io.superworldsun.superslegend.items.tools;

import io.superworldsun.superslegend.SupersLegend;
import io.superworldsun.superslegend.init.ItemList;
import io.superworldsun.superslegend.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;

public class ToolAxe extends ItemAxe implements IHasModel{

	public ToolAxe(String name, ToolMaterial material)
	{
		//Damage & Speed values for Axe
		super(material,6.0F, -3.2f);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ItemList.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		SupersLegend.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
