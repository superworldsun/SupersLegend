package superworldsun.superslegend.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class TriforceWisdom extends Item
{

	public TriforceWisdom(Properties properties)
	{
		super(properties);
	}
	
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;

			ItemStack equipped = player.getHeldItemMainhand();

			if(!world.isRemote)
			{
				if(stack == equipped)
		        {
					player.addPotionEffect(new EffectInstance(Effect.get(16), 1200, 0, false, true));
					player.addPotionEffect(new EffectInstance(Effect.get(13), 1200, 0, false, true));
		        }
			}
			
		}
	}
}
