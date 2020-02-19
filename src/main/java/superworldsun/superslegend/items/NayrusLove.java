package superworldsun.superslegend.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
//import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
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

public class NayrusLove extends Item
{
	//private static int duration = 25;
	
	public NayrusLove(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		ItemStack stack = player.getHeldItem(hand);
		 //player.setActiveHand(hand);
	        
		  
		 if (player.isAlive() && player.getFoodStats().getFoodLevel()>= 9) 
		 {
	            @SuppressWarnings("unused")
				ActionResult<ItemStack> success = new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	            
	            
	            BlockPos currentPos = player.getPosition();
				 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1f, 1f);
				 
	            player.addExhaustion(100f);
	            
	            Random rand = player.world.rand;
		        for (int i = 0; i < 45; i++)
		        {
		        	player.world.addParticle(ParticleTypes.TOTEM_OF_UNDYING,
		                    player.posX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    player.posY + rand.nextFloat() * 3 - 2,
		                    player.posZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    0, 0.105D, 0);
		        }
	            
	            
				player.addPotionEffect(new EffectInstance(Effect.get(24), 300, 0, true, true));
				player.addPotionEffect(new EffectInstance(Effect.get(11), 300, 99, false, false));
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
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.AQUA + "Grants invinciblity"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}   
}