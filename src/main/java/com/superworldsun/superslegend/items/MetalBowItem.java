package com.superworldsun.superslegend.items;

import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.Ingredient;

public class MetalBowItem extends BowItem {

    public float bowdamagebonus = 2.5F;
    private static final Ingredient REPAIR = Ingredient.of(Items.IRON_INGOT);

    public MetalBowItem(Properties p_i48522_1_) {
        super(p_i48522_1_);
    }

    //Remove the "public static final Ingredient" line and this if you don't want a repairable bow
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return REPAIR.test(repair);
    }

    //We pull some of the code from BowArrow and modify the arrow so we can do stuff such as having a bow fire arrows straight or bows applying damage bonuses
    //You can also set the arrow on fire, make it glow, etc
    //This is a smarter way of doing custom bow-damage properties than copying the entire right-click mehtod
    @Override
    public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
        //You can also specify a mod arrow like in the commented out stuff
        // ModArrowEntity specialarrow = EntityInit.SPECIAL_ARROW_ENTITY.get().create(arrow.world);
        // return specialarrow == null ? arrow : specialarrow;
        arrow.setBaseDamage(arrow.getBaseDamage()+bowdamagebonus);
        //You can uncomment this out if you want enchantments on a bow
        //int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, this.getDefaultInstance());
        //if (j > 0) {
        //    arrow.setBaseDamage((arrow.getBaseDamage()+bowdamagebonus) + (double)j * 0.5D + 0.5D);
        //}
        //int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, this.getDefaultInstance());
        //if (k > 0) {
        //    arrow.setKnockback(k);
        //}
        //if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, this.getDefaultInstance()) > 0) {
        //    arrow.setSecondsOnFire(100);
        // }
        arrow.setNoGravity(true);
        return super.customArrow(arrow);
    }

    //The bow's enchantability, we return 0 cause we don't want it enchantable but you can return 5-ish or higher if you want enchantments for them
    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    //The bow's book-enchantability, we return false cause we don't want it enchantable but you can return 5-ish or higher if you want enchantments for them
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }

    //The distance that hostile entities holding the ranged weapon begin shooting the player at
    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    //Bows always have arbitrarily long usedurations
    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    @Override
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BOW;
    }
}
