package com.superworldsun.superslegend;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SupersLegendItemGroup extends ItemGroup {

    public SupersLegendItemGroup()
    {
        super("supers_legend");
    }

    //Tab Icon
    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack makeIcon()
    {
            return new ItemStack(ItemInit.TRIFORCE.get());
    }
}
