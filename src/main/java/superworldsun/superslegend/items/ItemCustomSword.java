package superworldsun.superslegend.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class ItemCustomSword extends SwordItem
{

	public ItemCustomSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) 
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        // TODO Auto-generated method stub
        return !super.isBookEnchantable(stack, book);
	}
	
}
