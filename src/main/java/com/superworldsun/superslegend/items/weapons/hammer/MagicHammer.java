package com.superworldsun.superslegend.items.weapons.hammer;

import com.superworldsun.superslegend.items.customclass.HammerItem;
import com.superworldsun.superslegend.util.ItemToolTiers;

public class MagicHammer extends HammerItem
{
    public MagicHammer(Properties properties)
    {
        super(ItemToolTiers.MAGIC_HAMMER, 2, new Properties());
    }

    //TODO When breaking plants there is no particle effect or sound played
    //TODO Add blocks to the Init, list isnt full
    //TODO Add a sound for when the hammer hits a block & entity
    @Override
    protected int getLeftClickCooldown() {
        return 12;
    }
}
