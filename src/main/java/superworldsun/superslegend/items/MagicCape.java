package superworldsun.superslegend.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MagicCape extends Item
{

	

	private boolean isInvulnerable;

	public MagicCape(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);
		  
		 if(!world.isRemote)
	     {
			 player.addPotionEffect(new EffectInstance(Effect.get(18), 5, 3, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(17), 5, 0, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(15), 60, 0, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(14), 5, 0, false, false));
				player.setInvulnerable(isInvulnerable);
				player.addExhaustion(1);

				
	     }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }
	
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, PlayerEntity player, int timeLeft) 
	
		{
			player.setInvulnerable(false);;
	    }
	
}
