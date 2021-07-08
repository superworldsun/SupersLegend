package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
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

public class BluePotion extends Item
{

	public BluePotion(Properties properties)
	{
		super(properties);
	}

	@Nonnull
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
	 }

	public void appendHoverText(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "The Medicine of Life & Stamina"));
	}  
	
}
