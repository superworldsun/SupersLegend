package superworldsun.superslegend;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import superworldsun.superslegend.lists.ItemList;

public class SupersLegendItemGroup extends ItemGroup
{
	public SupersLegendItemGroup() 
	{
		super("supers_legend");
	}

	@Override
	public ItemStack createIcon() 
	{
		return new ItemStack(ItemList.rupee);
	}
}
