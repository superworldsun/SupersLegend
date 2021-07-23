package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
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

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DekuLeaf extends Item
{
	public DekuLeaf(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack itemstack = player.getItemInHand(hand);

		// Can't use without mana (only in creative)
		if (ManaProvider.get(player).getMana() < getManaCost() && !player.abilities.instabuild)
		{
			player.fallDistance *= 0.5F;
			player.push(0f, 0.235f, 0f );
			return ActionResult.fail(itemstack);
		}
		else
		{
			player.fallDistance *= 0.5F;
			player.push(0f, 0.235f, 0f );
			player.startUsingItem(hand);
			return ActionResult.consume(itemstack);
		}
	}

	@Override
	public void onUseTick(World world, LivingEntity user, ItemStack stack, int time)
	{
		// If used not by player (somehow) we don't want errors, we have mana
		// only on players
		if (!(user instanceof PlayerEntity))
		{
			return;
		}

		PlayerEntity player = (PlayerEntity) user;

		// Stop using if out of mana
		if (ManaProvider.get(player).getMana() < getManaCost())
		{
			user.stopUsingItem();
		}

		if (!player.abilities.instabuild)
		{
			ManaProvider.get(player).spendMana(getManaCost());
		}
	}

	private float getManaCost()
	{
		return 5.0F;
	}

	//OLD CODE
	/*@Nonnull
	@ParametersAreNonnullByDefault
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getItemInHand(hand);

		if(!player.isFallFlying() &&!player.isOnGround() && !player.isInWater() && player.getFoodData().getFoodLevel()>= 1)
		        {
			player.fallDistance *= 0.5F;
			player.push(0f, 0.235f, 0f );
			
			player.addEffect(new EffectInstance(Objects.requireNonNull(Effect.byId(28)), 11, 10, false, false));
			player.causeFoodExhaustion(1f);
			
			Random rand = player.level.random;
	        for (int i = 0; i < 45; i++)
	        {
	        	player.level.addParticle(ParticleTypes.CLOUD,
	                    player.xo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 1,
	                    player.yo + rand.nextFloat() * 1 - 2,
	                    player.zo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 1,
	                    0, 0.105D, 0);
	        }
			
			player.getCooldowns().addCooldown(this, 9);
		        }
		if(player.getFoodData().getFoodLevel() == 0)
       {
			
			player.getCooldowns().addCooldown(this, 9);
			
			BlockPos currentPos = player.blockPosition();
	         player.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR.get(), SoundCategory.PLAYERS, 1f, 1f);
       }
				return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
	 }*/

	public void appendHoverText(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-Click in the air, this will slow your decent"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina when airborne"));
	}   
}