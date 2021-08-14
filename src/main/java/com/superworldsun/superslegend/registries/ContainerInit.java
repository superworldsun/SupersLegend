package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.BagContainer;
import com.superworldsun.superslegend.container.BigQuiverContainer;
import com.superworldsun.superslegend.container.LetterContainer;
import com.superworldsun.superslegend.container.MediumQuiverContainer;
import com.superworldsun.superslegend.container.SmallQuiverContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit
{
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<ContainerType<BagContainer>> BAG = CONTAINERS.register("bag", () -> IForgeContainerType.create(BagContainer::new));
	public static final RegistryObject<ContainerType<LetterContainer>> LETTER = CONTAINERS.register("letter", () -> IForgeContainerType.create(LetterContainer::new));
	public static final RegistryObject<ContainerType<SmallQuiverContainer>> SMALL_QUIVER = CONTAINERS.register("small_quiver", () -> IForgeContainerType.create(SmallQuiverContainer::new));
	public static final RegistryObject<ContainerType<MediumQuiverContainer>> MEDIUM_QUIVER = CONTAINERS.register("medium_quiver", () -> IForgeContainerType.create(MediumQuiverContainer::new));
	public static final RegistryObject<ContainerType<BigQuiverContainer>> BIG_QUIVER = CONTAINERS.register("big_quiver", () -> IForgeContainerType.create(BigQuiverContainer::new));
}
