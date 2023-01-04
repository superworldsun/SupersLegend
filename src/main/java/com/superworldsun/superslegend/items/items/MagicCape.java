package com.superworldsun.superslegend.items.items;

import java.util.List;
import java.util.Random;

import com.superworldsun.superslegend.capability.mana.ManaHelper;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MagicCape extends Item {
	public static final float MANA_COST = 0.058F;

	public MagicCape(Properties properties) {
		super(properties);
	}

	// TODO Make sure it works in multiplayer with other players, other players can still see invisible player

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack capeStack = player.getItemInHand(hand);
		boolean hasMana = ManaHelper.hasMana(player, MANA_COST);
		boolean hasEffect = player.hasEffect(EffectInit.CLOAKED.get());

		if (hasEffect) {
			player.removeEffect(EffectInit.CLOAKED.get());
			player.getCooldowns().addCooldown(this, 8);
			addSmokeParticles(player);
			world.playSound(null, player, SoundInit.MAGIC_CAPE_OFF.get(), SoundCategory.PLAYERS, 1f, 1f);
			return new ActionResult<>(ActionResultType.SUCCESS, capeStack);
		} else if (hasMana) {
			player.getCooldowns().addCooldown(this, 8);
			player.addEffect(new EffectInstance(EffectInit.CLOAKED.get(), Integer.MAX_VALUE, 0, false, false, true));
			world.playSound(null, player, SoundInit.MAGIC_CAPE_ON.get(), SoundCategory.PLAYERS, 1f, 1f);
			return new ActionResult<>(ActionResultType.SUCCESS, capeStack);
		}

		return new ActionResult<>(ActionResultType.FAIL, capeStack);
	}

	private void addSmokeParticles(PlayerEntity player) {
		Random random = player.level.random;

		for (int i = 0; i < 45; i++) {
			double particleX = player.getX() + (random.nextBoolean() ? -1 : 1) * Math.pow(random.nextFloat(), 2) * 2;
			double particleY = player.getY() + random.nextFloat() * 3 - 2;
			double particleZ = player.getZ() + (random.nextBoolean() ? -1 : 1) * Math.pow(random.nextFloat(), 2) * 2;
			player.level.addParticle(ParticleTypes.SMOKE, particleX, particleY, particleZ, 0, 0.105D, 0);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.RED + "Allows you to slip through many obstacles easier"));
		list.add(new StringTextComponent(TextFormatting.DARK_RED + "Grants invincibility & invisibility"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Sneak + Right-click to cloak"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to uncloak"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Magic on use"));
		list.add(new StringTextComponent(TextFormatting.RED + "Invisible doesn't work on other players [WIP]"));
	}
}
