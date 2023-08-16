package com.superworldsun.superslegend.items.weapons.hammer;

import com.superworldsun.superslegend.items.customclass.HammerItem;
import com.superworldsun.superslegend.util.ItemToolTiers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SkullHammer extends HammerItem
{
    public SkullHammer(Properties properties)
    {
        super(ItemToolTiers.SKULL_HAMMER, 2, new Properties());
    }

    //TODO When breaking plants there is no particle effect or sound played
    //TODO Add blocks to the Init, list isnt full
    //TODO Add a sound for when the hammer hits a block & entity
    @Override
    protected int getLeftClickCooldown() {
        return 24;
    }

    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(entity instanceof Player)
        {
            Player player = (Player)entity;
            ItemStack equipped = player.getMainHandItem();
            {
                if(stack == equipped)
                {
                    if(player.hasItemInSlot(EquipmentSlot.OFFHAND))
                    {
                        player.spawnAtLocation(player.getOffhandItem());
                        player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                }
            }
        }
    }
}
