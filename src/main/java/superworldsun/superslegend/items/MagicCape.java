package superworldsun.superslegend.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class MagicCape extends Item
{

	private static final boolean isInvulnerable = true;

	public MagicCape(Properties properties)
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
					player.addPotionEffect(new EffectInstance(Effect.get(18), 5, 3, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(17), 5, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(15), 60, 0, false, false));
					player.addPotionEffect(new EffectInstance(Effect.get(14), 5, 0, false, false));
					player.setInvulnerable(isInvulnerable);
		        }
            	else
            	{
            		player.setInvulnerable(false);;
            	}
			}	
			
		}
	}
}
