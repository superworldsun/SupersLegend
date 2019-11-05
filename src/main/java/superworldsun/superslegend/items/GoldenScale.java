package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GoldenScale extends Item
{

	public GoldenScale(Properties properties)
	{
		super(properties);
	}
	
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;

			if(!world.isRemote)
			{
		        {
					if(player.isInWater()) 
	            	{
	            		player.isAlive();
	            	}
	            	else
	            	{
	            		player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 415, 0, false, false, false));
	            	}
	                
	            	}

			}	
			
		}
	}
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GOLD + "Allows you to dive even longer"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Have this anywhere in your inventory"));
	}   
}
