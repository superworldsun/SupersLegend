package com.superworldsun.superslegend.items.items;

import java.util.List;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class KokiriSet extends Item{

	public KokiriSet(Properties properties)
	{
		super(properties);
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	 {

		 if(player.inventory.getFreeSlot() != -1) {

			 ItemStack stack = player.getItemInHand(hand);
			 {
				 //if(player.inventory.)
				 stack.shrink(1);


				 player.addItem(new ItemStack(ItemInit.KOKIRI_CAP.get()));
				 player.addItem(new ItemStack(ItemInit.KOKIRI_TUNIC.get()));
				 player.addItem(new ItemStack(ItemInit.KOKIRI_LEGGINGS.get()));


				 BlockPos currentPos = player.blockPosition();
				 player.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR.get(), SoundCategory.PLAYERS, 1f, 1f);
			 }
		 }

	return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));

	}


	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GREEN + "kokiri set"));
	}   
} 