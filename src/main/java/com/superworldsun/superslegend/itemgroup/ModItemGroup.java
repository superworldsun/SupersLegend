package com.superworldsun.superslegend.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

public class ModItemGroup extends ItemGroup {
	private final RegistryObject<? extends IItemProvider> iconItemObject;

	public ModItemGroup(String name, RegistryObject<? extends IItemProvider> iconItemObject) {
		super(name);
		this.iconItemObject = iconItemObject;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(iconItemObject.get());
	}
}
