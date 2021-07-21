package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.arrows.EntityArrowFire;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ArrowFire extends ArrowItem
{

    public ArrowFire(Properties builder)
    {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        EntityArrowFire entityFireArrow = new EntityArrowFire(worldIn, shooter);
        return entityFireArrow;
    }
    
    /*public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.arrows.player.PlayerEntity player) {
        @SuppressWarnings("unused")
		ItemStack itemStack = (new ItemStack(ItemList.heros_bow));
        return true;
     }*/
}
