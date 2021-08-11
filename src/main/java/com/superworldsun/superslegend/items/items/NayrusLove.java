package com.superworldsun.superslegend.items.items;

import java.util.List;
import java.util.Random;

import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class NayrusLove extends Item
{
	//private static int duration = 25;
	
	public NayrusLove(Properties properties)
	{
		super(properties);
	}

	float manaCost = 8.00F;

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	 {
		 boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
		ItemStack stack = player.getItemInHand(hand);
		 //player.setActiveHand(hand);
	        
		  
		 if (player.isAlive() && hasMana)
		 {
	            @SuppressWarnings("unused")
				ActionResult<ItemStack> success = new ActionResult<>(ActionResultType.SUCCESS, player.getItemInHand(hand));
	            
	            
	            BlockPos currentPos = player.blockPosition();
				 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.NAYRUS_LOVE_CAST.get(), SoundCategory.PLAYERS, 1f, 1f);

			 ManaProvider.get(player).spendMana(manaCost);
	            
	            Random rand = player.level.random;
		        for (int i = 0; i < 45; i++)
		        {
		        	player.level.addParticle(ParticleTypes.TOTEM_OF_UNDYING,
		                    player.xo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    player.yo + rand.nextFloat() * 3 - 2,
		                    player.zo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    0, 0.105D, 0);
		        }
	            
	            
				player.addEffect(new EffectInstance(Effect.byId(24), 300, 0, true, true));
				player.addEffect(new EffectInstance(Effect.byId(11), 300, 99, false, false));
				player.getCooldowns().addCooldown(this, 100);
		 		}
		 		else if(ManaProvider.get(player).getMana() <8)
		 		{
		 			BlockPos currentPos = player.blockPosition();
					 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR.get(), SoundCategory.PLAYERS, 1f, 1f);
				
		 		}
		 return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
		 //return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
	 }
	
	/*@Override
    public UseAction getUseAction (ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return duration;
    }*/
	

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.AQUA + "Grants invinciblity"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}   
}