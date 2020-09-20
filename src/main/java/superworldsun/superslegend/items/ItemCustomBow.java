package superworldsun.superslegend.items;

import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCustomBow extends BowItem
{
	public ItemCustomBow(Item.Properties builder) {
	      super(builder);
	}
	      
	      @Override
	      public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
	          // TODO Auto-generated method stub
	    	  return false;	
	
	}
}
