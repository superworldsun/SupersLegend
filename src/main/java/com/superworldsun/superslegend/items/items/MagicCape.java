package com.superworldsun.superslegend.items.items;

import java.util.List;
import java.util.Random;

import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.EffectInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
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

// TODO 1: LOOK INTO SEEING IF THERES A BETTER MORE EFFICIENT WAY TO RUN THIS ITEM
// TODO 2: ALLOW THE PLAYERS SHADOW TO BE SHOWN WHEN INVISIBLE.
// TODO 3: ALLOW THE PLAYER TO MOVE THROUGH OTHER ENTITIES WITHOUT BUMPING/PUSHING
// TODO 4: MAKE IT SO ALL MOBS CANT SEE THE PLAYER WHEN WEARING IT

public class MagicCape extends Item
{
	// make a config for that?
	public static final float MANA_COST = 0.058F;
	
	public MagicCape(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack capeStack = player.getItemInHand(hand);
		boolean hasMana = ManaProvider.get(player).getMana() >= MagicCape.MANA_COST || player.abilities.instabuild;
		boolean hasEffect = player.hasEffect(EffectInit.CLOAKED.get());
		
		if (hasEffect)
		{
			player.removeEffect(EffectInit.CLOAKED.get());
			player.getCooldowns().addCooldown(this, 8);
			Random rand = player.level.random;
			
			for (int i = 0; i < 45; i++)
			{
				double particleX = player.getX() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2;
				double particleY = player.getY() + rand.nextFloat() * 3 - 2;
				double particleZ = player.getZ() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2;
				player.level.addParticle(ParticleTypes.SMOKE, particleX, particleY, particleZ, 0, 0.105D, 0);
			}
			
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.MAGIC_CAPE_OFF.get(), SoundCategory.PLAYERS, 1f, 1f);
			return new ActionResult<>(ActionResultType.SUCCESS, capeStack);
		}
		else if (hasMana)
		{
			player.getCooldowns().addCooldown(this, 8);
			// Integer.MAX_VALUE is essentially infinity in this case
			player.addEffect(new EffectInstance(EffectInit.CLOAKED.get(), Integer.MAX_VALUE, 0, false, false));
			
			/*
			 * Random rand = player.world.rand; for (int i = 0; i < 45; i++) { player.world.addParticle(ParticleTypes.CLOUD, player.posX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2)
			 * * 2, player.posY + rand.nextFloat() * 3 - 2, player.posZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2, 0, 0.105D, 0); }
			 */
			
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.MAGIC_CAPE_ON.get(), SoundCategory.PLAYERS, 1f, 1f);
			return new ActionResult<>(ActionResultType.SUCCESS, capeStack);
		}
		
		return new ActionResult<>(ActionResultType.FAIL, capeStack);
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
}
