package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.entity.CookingPotTileEntity;
import com.superworldsun.superslegend.container.*;
import com.superworldsun.superslegend.container.bag.*;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit
{
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SupersLegendMain.MOD_ID);
	
	public static final RegistryObject<ContainerType<BagContainer>> BAG = CONTAINERS.register("bag", () -> IForgeContainerType.create(BagContainer::new));
	public static final RegistryObject<ContainerType<LetterContainer>> LETTER = CONTAINERS.register("letter", () -> IForgeContainerType.create(LetterContainer::new));
	public static final RegistryObject<ContainerType<PostboxContainer>> POSTBOX = CONTAINERS.register("postbox", () -> IForgeContainerType.create(PostboxContainer::new));
	public static final RegistryObject<ContainerType<SelectContainer>> SELECT_CONTAINER = CONTAINERS.register("select_container", () -> IForgeContainerType.create(SelectContainer::new));
	public static final RegistryObject<ContainerType<RingBoxContainer>> RING_BOX = CONTAINERS.register("ring_box", () -> IForgeContainerType.create(RingBoxContainer::new));
	public static final RegistryObject<ContainerType<BigRingBoxContainer>> RING_BOX_BIG = CONTAINERS.register("ring_box_big", () -> IForgeContainerType.create(BigRingBoxContainer::new));
	public static final RegistryObject<ContainerType<BiggestRingBoxContainer>> RING_BOX_BIGGEST = CONTAINERS.register("ring_box_biggest", () -> IForgeContainerType.create(BiggestRingBoxContainer::new));
	
	public static final RegistryObject<ContainerType<CookingPotContainer>> COOKING_POT = CONTAINERS.register("cooking_pot", () ->
			IForgeContainerType.<CookingPotContainer>create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		return new CookingPotContainer(windowId, inv, (CookingPotTileEntity) inv.player.level.getBlockEntity(pos));
	}));
}
