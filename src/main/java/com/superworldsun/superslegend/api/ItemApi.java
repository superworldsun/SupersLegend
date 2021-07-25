package com.superworldsun.superslegend.api;

import com.superworldsun.superslegend.util.registry.RegistryHelper;
import net.minecraft.item.Item;

public class ItemApi extends Item {

    /**
     * @deprecated  This method workes like a charm thanks to the AutoRegLib,
     * but u can also just register the item urself under our modid, superslegend.
     * Blocks cannot be registered yet.
     */
    public ItemApi(String regname, Properties properties) {
        super(properties);
        RegistryHelper.registerItem(this, regname);
    }

}
