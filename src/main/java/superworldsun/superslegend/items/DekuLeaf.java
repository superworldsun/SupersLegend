package superworldsun.superslegend.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class DekuLeaf extends Item
{
	public DekuLeaf(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);

		if(!player.isElytraFlying() &&!player.onGround && !player.isInWater() && !player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.hover_boots) && player.getFoodStats().getFoodLevel()>= 1)
		        {
			player.fallDistance *= 0.5F;
			player.addVelocity(0f, 0.235f, 0f );
			
			player.addPotionEffect(new EffectInstance(Effect.get(28), 11, 10, false, false));
			player.addExhaustion(1f);
			
			Random rand = player.world.rand;
	        for (int i = 0; i < 45; i++)
	        {
	        	player.world.addParticle(ParticleTypes.CLOUD,
	                    player.posX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 1,
	                    player.posY + rand.nextFloat() * 1 - 2,
	                    player.posZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 1,
	                    0, 0.105D, 0);
	        }
			
			player.getCooldownTracker().setCooldown(this, 9);
		        }
		if(player.getFoodStats().getFoodLevel() == 0)
       {
			
			player.getCooldownTracker().setCooldown(this, 9);
			
			BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR, SoundCategory.PLAYERS, 1f, 1f);
       }
				return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);

		if(player.onGround && player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.hover_boots) && player.getFoodStats().getFoodLevel()!= 0) 
         	{
         		player.removePotionEffect(Effect.get(28));
         	}
		else if(!player.onGround && !player.isInWater() && player.getFoodStats().getFoodLevel()>= 1)
		        {
					player.addExhaustion(0.2f);
					player.addPotionEffect(new EffectInstance(Effect.get(28), 5, 10, false, false));
		        }
		else if(!player.onGround && player.isInWater() && player.getFoodStats().getFoodLevel()< 1)
        {
			
			player.getCooldownTracker().setCooldown(this, 10);
			
			BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR, SoundCategory.PLAYERS, 1f, 1f);
        }
				return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
		}*/
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-Click in the air, this will slow your decent"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina when airborne"));
	}   
}