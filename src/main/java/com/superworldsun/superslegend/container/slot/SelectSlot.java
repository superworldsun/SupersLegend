package com.superworldsun.superslegend.container.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class SelectSlot extends Slot
{
    public SelectSlot(IInventory inventory, int index, int x, int y)
    {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPickup(PlayerEntity p_82869_1_)
    {
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack p_75214_1_)
    {
        return false;
    }

    @Override
    public ItemStack onTake(PlayerEntity entity, ItemStack stack)
    {
        PlayerInventory inv = entity.inventory;

        if(stack != ItemStack.EMPTY)
        {
            if (stack.getItem() == inv.getCarried().getItem())
            {
                inv.setItem(inv.selected, ItemStack.EMPTY);
                inv.setChanged();
            } else if (stack.getItem() instanceof ArmorItem)
            {
                int slot = ((ArmorItem) stack.getItem()).getSlot().getIndex();
                if (inv.armor.get(slot).isEmpty())
                {
                    inv.armor.set(slot, stack);
                    inv.setChanged();
                } else if (inv.getFreeSlot() != -1)
                {
                    inv.setItem(inv.getFreeSlot(), inv.armor.get(slot));
                    inv.armor.set(slot, stack);
                    inv.setChanged();
                }
            } else
            {
                int slot = inv.selected;

                if (inv.getItem(slot).isEmpty())
                {
                    inv.setItem(slot, stack);
                    inv.setChanged();
                } else if (inv.getFreeSlot() != -1)
                {
                    inv.setItem(inv.getFreeSlot(), inv.getItem(slot));
                    inv.setItem(slot, stack);
                    inv.setChanged();
                }
            }
        }

        return ItemStack.EMPTY;
    }
}
