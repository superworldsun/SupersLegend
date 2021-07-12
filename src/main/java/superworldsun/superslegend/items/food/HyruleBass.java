package superworldsun.superslegend.items.food;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

import net.minecraft.item.Item.Properties;

public class HyruleBass extends Item {


    public HyruleBass(Properties properties) {
        super(properties.food(new Food.Builder().saturationMod(0.3f).nutrition(1).meat().build()));
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 32;
    }

}
