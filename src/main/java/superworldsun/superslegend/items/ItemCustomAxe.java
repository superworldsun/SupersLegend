package superworldsun.superslegend.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.AxeItem;

public class ItemCustomAxe extends AxeItem
{

	public ItemCustomAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) 
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        // TODO Auto-generated method stub
        return !super.isBookEnchantable(stack, book);
	}
	
}
