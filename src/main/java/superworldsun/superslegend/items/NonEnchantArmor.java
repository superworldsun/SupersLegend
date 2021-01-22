package superworldsun.superslegend.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

public class NonEnchantArmor extends ArmorItem {

	public NonEnchantArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	
	
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
	}
	
	
}
