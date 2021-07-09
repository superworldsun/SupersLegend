package superworldsun.superslegend.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class Heart extends Item
{

	public Heart(Properties properties)
	{
		super(properties);
	}
	
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity && !world.isClientSide)
		{
			PlayerEntity player = (PlayerEntity)entity;

			if(!world.isClientSide && !player.isCreative())
			{
				player.setHealth(player.getHealth() + 2.0F);
				stack.shrink(1);
			}
		}
			
	}
}
