package superworldsun.superslegend.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class ItemCustomPickaxe extends PickaxeItem
{

	public ItemCustomPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) 
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        // TODO Auto-generated method stub
        return !super.isBookEnchantable(stack, book);
	}
}
