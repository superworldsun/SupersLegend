package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.mana.IMana;
import com.superworldsun.superslegend.mana.ManaProvider;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import net.minecraft.item.Item.Properties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BluePotion extends Item
{

	public BluePotion(Properties properties)
	{
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity)
	{
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;

		if (player instanceof ServerPlayerEntity)
		{
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
		}

		if (player != null)
		{
			IMana mana = ManaProvider.get(player);
			// Restore to maximum
			mana.restoreMana(mana.getMaxMana());
			player.addEffect(new EffectInstance(Objects.requireNonNull(Effects.REGENERATION), 60, 4, false, false));
			player.awardStat(Stats.ITEM_USED.get(this));

			if (!player.abilities.instabuild)
			{
				stack.shrink(1);
			}
		}

		if (player == null || !player.abilities.instabuild)
		{
			if (stack.isEmpty())
			{
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (player != null)
			{
				player.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack)
	{
		return 32;
	}

	@Override
	public UseAction getUseAnimation(ItemStack stack)
	{
		return UseAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if (ManaProvider.get(player).getMana() == ManaProvider.get(player).getMaxMana() && !player.isHurt())
		{
			return ActionResult.fail(stack);
		}

		return DrinkHelper.useDrink(world, player, hand);
	}

	/*@Nonnull
	public ActionResult<ItemStack> use(World world, PlayerEntity player,@Nonnull Hand hand)
	 {
		ItemStack stack = player.getItemInHand(hand);
		  
		 if(!world.isClientSide &&  !player.isCreative() && player.getFoodData().needsFood() && player.isHurt())
	     {
			 BlockPos currentPos = player.blockPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addEffect(new EffectInstance(Objects.requireNonNull(Effect.byId(10)), 60, 4, false, false));
			 player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 20);
			 stack.shrink(1);
			 player.addItem(new ItemStack(Items.GLASS_BOTTLE));
	     }
		 if(!world.isClientSide && !player.isCreative() && !player.getFoodData().needsFood() && player.isHurt())
		 {
			 BlockPos currentPos = player.blockPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addEffect(new EffectInstance(Objects.requireNonNull(Effect.byId(10)), 60, 4, false, false));
			 stack.shrink(1);
			 player.addItem(new ItemStack(Items.GLASS_BOTTLE));
		 }
		 if(!world.isClientSide && !player.isCreative() && player.getFoodData().needsFood() && !player.isHurt())
		 {
			 BlockPos currentPos = player.blockPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);

			 player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 20);
			 stack.shrink(1);
			 player.addItem(new ItemStack(Items.GLASS_BOTTLE));
		 }



		 if(player.isCreative() && player.getFoodData().needsFood())
		 {
			 BlockPos currentPos = player.blockPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addEffect(new EffectInstance(Objects.requireNonNull(Effect.byId(10)), 60, 4, false, false));
			 player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 20);
		 }
		 if(player.isCreative() && !player.getFoodData().needsFood())
		 {
			 BlockPos currentPos = player.blockPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addEffect(new EffectInstance(Objects.requireNonNull(Effect.byId(10)), 60, 4, false, false));
		 }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand)); 
	 }*/

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "The Medicine of Life & Stamina"));
	}  
	
}
