package superworldsun.superslegend.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class Triforce extends Item
{

	public Triforce(Properties properties)
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
					player.addPotionEffect(new EffectInstance(Effect.get(1), 300, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(11), 300, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(5), 300, 0, false, true));
					player.addPotionEffect(new EffectInstance(Effect.get(3), 300, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(16), 900, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(13), 900, 0, false, false));
		        }
			}	
			
		}
	}
}
