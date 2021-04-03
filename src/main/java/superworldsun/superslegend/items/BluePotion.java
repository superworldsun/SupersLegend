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

public class BluePotion extends Item
{

	public BluePotion(Properties properties)
	{
		super(properties);
	}

	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player,@Nonnull Hand hand)
	 {
		ItemStack stack = player.getHeldItem(hand);
		  
		 if(!world.isRemote &&  !player.isCreative() && player.getFoodStats().needFood() && player.shouldHeal())
	     {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addPotionEffect(new EffectInstance(Objects.requireNonNull(Effect.get(10)), 60, 4, false, false));
			 player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 20);
			 stack.shrink(1);
			 player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
	     }
		 if(!world.isRemote && !player.isCreative() && !player.getFoodStats().needFood() && player.shouldHeal())
		 {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addPotionEffect(new EffectInstance(Objects.requireNonNull(Effect.get(10)), 60, 4, false, false));
			 stack.shrink(1);
			 player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		 }
		 if(!world.isRemote && !player.isCreative() && player.getFoodStats().needFood() && !player.shouldHeal())
		 {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);

			 player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 20);
			 stack.shrink(1);
			 player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		 }



		 if(player.isCreative() && player.getFoodStats().needFood())
		 {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addPotionEffect(new EffectInstance(Objects.requireNonNull(Effect.get(10)), 60, 4, false, false));
			 player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 20);
		 }
		 if(player.isCreative() && !player.getFoodStats().needFood())
		 {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addPotionEffect(new EffectInstance(Objects.requireNonNull(Effect.get(10)), 60, 4, false, false));
		 }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }

	public void addInformation(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "The Medicine of Life & Stamina"));
	}  
	
}
