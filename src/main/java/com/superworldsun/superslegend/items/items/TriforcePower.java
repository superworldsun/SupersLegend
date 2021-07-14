package com.superworldsun.superslegend.items.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TriforcePower extends Item
{

	public TriforcePower(Properties properties)
	{
		super(properties);
	}
	
	 public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getItemInHand(hand);
		 {
			 BlockPos currentPos = player.blockPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1f, 1f);
					player.addEffect(new EffectInstance(Effect.byId(20), 44, 0, false, false));
					player.addEffect(new EffectInstance(Effect.byId(19), 200, 0, false, false));
					player.addEffect(new EffectInstance(Effect.byId(5), 600, 0, false, true));
					player.addEffect(new EffectInstance(Effect.byId(3), 400, 0, false, false));
					player.getCooldowns().addCooldown(this, 10);
		 }
	 			return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
	 }

	 @Override
		public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
		{
			super.appendHoverText(stack, world, list, flag);				
			list.add(new StringTextComponent(TextFormatting.RED + "This gives the you Power, but at what cost?"));
			list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
		}   
}
