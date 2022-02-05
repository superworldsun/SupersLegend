package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.tile.CookingPotTileEntity;
import com.superworldsun.superslegend.container.*;

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
	public static final RegistryObject<ContainerType<SmallQuiverContainer>> SMALL_QUIVER = CONTAINERS.register("small_quiver", () -> IForgeContainerType.create(SmallQuiverContainer::new));
	public static final RegistryObject<ContainerType<MediumQuiverContainer>> MEDIUM_QUIVER = CONTAINERS.register("medium_quiver", () -> IForgeContainerType.create(MediumQuiverContainer::new));
	public static final RegistryObject<ContainerType<BigQuiverContainer>> BIG_QUIVER = CONTAINERS.register("big_quiver", () -> IForgeContainerType.create(BigQuiverContainer::new));
	public static final RegistryObject<ContainerType<SmallBombContainer>> SMALL_BOMB_BAG = CONTAINERS.register("small_bomb_bag", () -> IForgeContainerType.create(SmallBombContainer::new));
	//public static final RegistryObject<ContainerType<MediumQuiverContainer>> BIG_BOMB_BAG = CONTAINERS.register("medium_bomb_bag", () -> IForgeContainerType.create(MediumQuiverContainer::new));
	//public static final RegistryObject<ContainerType<BigQuiverContainer>> BIGGEST_BOMB_BAG = CONTAINERS.register("big_bomb_bag", () -> IForgeContainerType.create(BigQuiverContainer::new));
	public static final RegistryObject<ContainerType<PostboxContainer>> POSTBOX = CONTAINERS.register("postbox", () -> IForgeContainerType.create(PostboxContainer::new));
	public static final RegistryObject<ContainerType<SelectContainer>> SELECT_CONTAINER = CONTAINERS.register("select_container", () -> IForgeContainerType.create(SelectContainer::new));


	public static final RegistryObject<ContainerType<CookingPotContainer>> COOKING_POT = CONTAINERS.register("cooking_pot", () ->
			IForgeContainerType.<CookingPotContainer>create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		return new CookingPotContainer(windowId, inv, (CookingPotTileEntity) inv.player.level.getBlockEntity(pos));
	}));
}
