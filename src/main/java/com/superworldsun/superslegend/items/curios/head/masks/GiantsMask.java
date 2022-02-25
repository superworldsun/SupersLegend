package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ArmourInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GiantsMask extends Item implements IEntityResizer, ICurioItem
{
	public GiantsMask(Properties properties)
	{
		super(properties);	}

	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
		PlayerEntity player = (PlayerEntity) livingEntity;
		if (!player.abilities.instabuild)
		{
			float manaCost = 0.01F;
			ManaProvider.get(player).spendMana(manaCost);
		}
	}

/*
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{
		if (!player.abilities.instabuild)
		{
			float manaCost = 0.01F;
			ManaProvider.get(player).spendMana(manaCost);
		}
	}
	*/

	@Override
	public float getScale(PlayerEntity player)
	{
		float manaCost = 0.01F;
		boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
		return hasMana ? 4.0F : 1.0F;
	}
}
