package com.superworldsun.superslegend.items.items;

import java.util.List;
import java.util.Random;

import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MagicCape extends Item
{

	public MagicCape(Properties properties)
	{
		super(properties);
	}

	//Should last about 17 seconds with this rate 0.058f
	float manaCost = 0.058F;
	
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	 {
		 boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
		 @SuppressWarnings("unused")
		ItemStack stack = player.getItemInHand(hand);

		 if(player.isCrouching() && hasMana)
	     {
			 	player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 3, 99, false, false, false));
			 	player.addEffect(new EffectInstance(Effects.INVISIBILITY, 3, 99, false, false, false));
				player.getCooldowns().addCooldown(this, 8);
				
				/*Random rand = player.world.rand;
		        for (int i = 0; i < 45; i++)
		        {
		        	player.world.addParticle(ParticleTypes.CLOUD,
		                    player.posX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    player.posY + rand.nextFloat() * 3 - 2,
		                    player.posZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    0, 0.105D, 0);
		        }*/
				
		        BlockPos currentPos = player.blockPosition();
				 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.MAGIC_CAPE_ON.get(), SoundCategory.PLAYERS, 1f, 1f);
	     }
		 if(!player.isCrouching() && hasMana)
				
				{
	        		player.removeEffect(Effect.byId(14));
	        		player.removeEffect(Effect.byId(11));

	        		player.getCooldowns().addCooldown(this, 8);
	        		
	        		
	        		Random rand = player.level.random;
			        for (int i = 0; i < 45; i++)
			        {
			        	player.level.addParticle(ParticleTypes.SMOKE,
			                    player.xo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
			                    player.yo + rand.nextFloat() * 3 - 2,
			                    player.zo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
			                    0, 0.105D, 0);
			        }
	        		
			        BlockPos currentPos = player.blockPosition();
					 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.MAGIC_CAPE_OFF.get(), SoundCategory.PLAYERS, 1f, 1f);
	        		
			 
		 }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand)); 
	 }

	 //TODO 1: LOOK INTO SEEING IF THERES A BETTER MORE EFFICIENT WAY TO RUN THIS ITEM
	 //TODO 2: ALLOW THE PLAYERS SHADOW TO BE SHOWN WHEN INVISIBLE.
	 //TODO 3: ALLOW THE PLAYER TO MOVE THROUGH OTHER ENTITIES WITHOUT BUMPING/PUSHING
	 //TODO 4: MAKE IT SO ALL MOBS CANT SEE THE PLAYER WHEN WEARING IT
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		PlayerEntity player = (PlayerEntity) entity;
		boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
		if(hasMana)
			
			{
			EffectInstance effect = ((PlayerEntity) entity).getEffect(Effects.INVISIBILITY);
        	if (effect != null && effect.getAmplifier() == 99) 
        		
        		{
					player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 3, 99, false, false, false));
					player.addEffect(new EffectInstance(Effects.INVISIBILITY, 3, 99, false, false, false));
					player.shouldRender(1f,1f,1f);
					ManaProvider.get(player).spendMana(manaCost);
        		}
			}
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Allows you to slip through many obsticals easier"));
		list.add(new StringTextComponent(TextFormatting.DARK_RED + "Grants invincibility & invisibility"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Sneak + Right-click to cloak"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to uncloak"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Magic on use"));
	}  
	
	/*public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);
		  
		 if(!world.isRemote && player.getFoodStats().getFoodLevel()!= 0)
	     {
			 player.addPotionEffect(new EffectInstance(Effect.get(18), 5, 99, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(17), 5, 0, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(15), 60, 0, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(14), 10, 0, false, false));
				player.addPotionEffect(new EffectInstance(Effect.get(11), 5, 99, false, false));
				player.isSneaking();
				player.addExhaustion(1);
				player.isInvisible();
	     }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }*/
	
}
