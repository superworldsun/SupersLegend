package com.superworldsun.superslegend.items.weapons;

import java.util.function.Predicate;

import com.superworldsun.superslegend.api.IPelletAmo;
import com.superworldsun.superslegend.entities.projectiles.arrows.PelletEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.SilverArrowEntity;
import com.superworldsun.superslegend.items.items.DekuSeed;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SlingShot extends BowItem {

	public SlingShot(Properties p_i48522_1_) {
		super(p_i48522_1_);
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return stack -> stack.getItem() instanceof IPelletAmo;
	}

	public PelletEntity customArrow(PelletEntity pellet) {
		return pellet;
	}

	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (worldIn.isClientSide) {
		}

		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			boolean infiniteAmmo = player.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
			ItemStack ammoItem = player.getProjectile(stack);

			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, player, i, !ammoItem.isEmpty() || infiniteAmmo);
			if (i < 0) return;

			if (!ammoItem.isEmpty() || infiniteAmmo) {
				if (ammoItem.isEmpty()) {
					ammoItem = new ItemStack(ItemInit.DEKU_SEEDS.get());
				}

				float f = getPowerForTime(i);
				if (!((double) f < 0.1D)) {
					boolean flag1 = player.abilities.instabuild || (ammoItem.getItem() instanceof DekuSeed && ((DekuSeed) ammoItem.getItem()).isInfinite(ammoItem, stack, player));
					if (!worldIn.isClientSide) {
						DekuSeed DekuSeed = (DekuSeed) (ammoItem.getItem() instanceof DekuSeed ? ammoItem.getItem() : ItemInit.DEKU_SEEDS.get());
						PelletEntity shot = (PelletEntity) DekuSeed.createArrow(worldIn, ammoItem, player);
						shot.shootFromRotation(player, player.xRot, player.yRot, 0.0F, f * 3.0F, 1.0F);
					}

					worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !player.abilities.instabuild) {
						ammoItem.shrink(1);
						if (ammoItem.isEmpty()) {
							player.inventory.removeItem(ammoItem);
						}
					}

					player.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}
}