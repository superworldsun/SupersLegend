package superworldsun.superslegend.items;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class ItemCustomHoe extends HoeItem
{
	public ItemCustomHoe(IItemTier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Properties p_i231595_4_) {
		super(p_i231595_1_, p_i231595_2_, p_i231595_3_, p_i231595_4_);
	}

	/*public ItemCustomHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, builder);
	}*/
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
	
}
