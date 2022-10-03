package com.superworldsun.superslegend.items.items;

import java.util.List;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class GoldenScale extends Item implements ICurioItem
{

	public GoldenScale(Properties properties)
	{
		super(properties);
	}

	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {

		if(!(livingEntity instanceof PlayerEntity)) return;
		PlayerEntity player = (PlayerEntity) livingEntity;

		//Get the Charm as an ItemStack
		ItemStack charm =
				CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GOLDEN_SCALE.get(), player).map(
						ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

		//Check if player is wearing it.
		if (!charm.isEmpty()) {

			if(!player.isEyeInFluid(FluidTags.WATER) && !player.isEyeInFluid(FluidTags.LAVA) && player.isInWater())
			{
				player.addEffect(new EffectInstance(Effects.WATER_BREATHING, 415, 0, false, false, false));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.BLUE + "Allows you to stay underwater longer"));
	}
}
