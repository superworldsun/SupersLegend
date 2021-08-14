package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.BagContainer;

import com.superworldsun.superslegend.container.BagContainerLetter;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit
{
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<ContainerType<BagContainer>> BAG = CONTAINERS.register("bag", () -> IForgeContainerType.create(BagContainer::new));
	public static final RegistryObject<ContainerType<BagContainerLetter>> LETTER = CONTAINERS.register("letter", () -> IForgeContainerType.create(BagContainerLetter::new));
}
