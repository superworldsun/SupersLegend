package superworldsun.superslegend.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public class ItemCustomShield extends ShieldItem
{
	public ItemCustomShield(Item.Properties builder) {
	      super(builder);
	}
	      
	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	      @Override
	      public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
	          // TODO Auto-generated method stub
	    	  return false;	
	
	}
}
