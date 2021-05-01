package superworldsun.superslegend.items.food;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class HylianLoachCooked extends Item {


    public HylianLoachCooked(Properties properties) {
        super(properties.food(new Food.Builder().saturation(0.4f).hunger(2).meat().build()));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 32;
    }

}
