package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class FairyBottle extends Item
{

	public FairyBottle(Properties properties)
	{
		super(properties);
	}
	
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity && !world.isClientSide)
		{
			PlayerEntity player = (PlayerEntity)entity;
					if(player.isDeadOrDying())
	            	{
	            		player.setHealth(20);
	            	}
		}
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if(!world.isClientSide && !player.isCreative())
		{
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.FAIRY_BOTTLE_USE, SoundCategory.PLAYERS, 1f, 1f);

			player.addEffect(new EffectInstance(Effect.byId(10), 60, 4, false, false));
			stack.shrink(1);
			player.addItem(new ItemStack(Items.GLASS_BOTTLE));
		}

		else if (!world.isClientSide && player.isCreative())
		{
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.FAIRY_BOTTLE_USE, SoundCategory.PLAYERS, 1f, 1f);

			player.addEffect(new EffectInstance(Effect.byId(10), 60, 4, true, true));
		}

		return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
	}


	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Restores all HP on right click"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Have this anywhere in your inventory"));
	}   
}
