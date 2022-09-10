package com.superworldsun.superslegend.items.curios.rings;

import java.util.List;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.superworldsun.superslegend.items.RingItem;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosApi;

public class HeartRingL2 extends RingItem
{	
	public HeartRingL2()
	{
		super(new Item.Properties());
	}
	
	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{		
		if (!(livingEntity instanceof PlayerEntity))
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) livingEntity;
		
		// Get the Ring as an ItemStack
		ItemStack ring = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.HEART_RING_L2.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		// Check if player is wearing it.
		if (!ring.isEmpty())
		{
			
			if (player.getHealth() < player.getMaxHealth() && player.tickCount % 300 == 0)
			{
				player.heal(2.0f);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.RED + "Recover lost Hearts"));
	}
}