package superworldsun.superslegend.items;

import java.util.List;
import java.util.Objects;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

import javax.annotation.Nonnull;

public class BluePotionMix extends Item
{

	public BluePotionMix(Properties properties)
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

			player.setHealth(player.getHealth() + 1.0F);
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 1);
			stack.shrink(1);
			player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		}
		if(!world.isRemote && !player.isCreative() && !player.getFoodStats().needFood() && player.shouldHeal())
		{
			BlockPos currentPos = player.getPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);

			player.setHealth(player.getHealth() + 1.0F);
			stack.shrink(1);
			player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		}
		if(!world.isRemote && !player.isCreative() && player.getFoodStats().needFood() && !player.shouldHeal())
		{
			BlockPos currentPos = player.getPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);

			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 1);
			stack.shrink(1);
			player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		}



		if(player.isCreative() && player.getFoodStats().needFood())
		{
			BlockPos currentPos = player.getPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);

			player.setHealth(player.getHealth() + 1.0F);
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 1);
		}
		if(player.isCreative() && !player.getFoodStats().needFood())
		{
			BlockPos currentPos = player.getPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);

			player.setHealth(player.getHealth() + 1.0F);
		}

		return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
	}

	public void addInformation(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "This could probably be cooked"));
	}  
	
}
