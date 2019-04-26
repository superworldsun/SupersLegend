package io.superworldsun.superslegend.items.tools;

import io.superworldsun.superslegend.SupersLegend;
import io.superworldsun.superslegend.init.ItemList;
import io.superworldsun.superslegend.util.IHasModel;
import net.minecraft.item.ItemHoe;

public class ToolHoe extends ItemHoe implements IHasModel{

	public ToolHoe(String name, ToolMaterial material)
	{
		super(material);
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
