package superworldsun.superslegend.items.bows;

import java.util.function.Predicate;
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

public class SlingShot extends ShootableItem implements IVanishable {
	public SlingShot(Item.Properties builder) {
		super(builder);
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity)entityLiving;
			boolean flag = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
			ItemStack itemstack = playerentity.getProjectile(stack);

			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
			if (i < 0) return;

			if (!itemstack.isEmpty() || flag) {
				if (itemstack.isEmpty()) {
					itemstack = new ItemStack(Items.ARROW);
				}

				float f = getArrowVelocity(i);
				if (!((double)f < 0.1D)) {
					boolean flag1 = playerentity.abilities.instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
					if (!worldIn.isClientSide) {
						ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
						AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
						abstractarrowentity = customArrow(abstractarrowentity);
						abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F) {
							abstractarrowentity.setCritArrow(true);
						}

						int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						if (j > 0) {
							abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)j * 0.5D + 0.5D);
						}

						int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
						if (k > 0) {
							abstractarrowentity.setKnockback(k);
						}

						if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
							abstractarrowentity.setSecondsOnFire(100);
						}

						stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> {
							p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
						});
						if (flag1 || playerentity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
							abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
						}

						worldIn.addFreshEntity(abstractarrowentity);
					}

					worldIn.playSound((PlayerEntity)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !playerentity.abilities.instabuild) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							playerentity.inventory.removeItem(itemstack);
						}
					}

					playerentity.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge) {
		float f = (float)charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.BOW;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		boolean flag = !playerIn.getProjectile(itemstack).isEmpty();

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
		if (ret != null) return ret;

		if (!playerIn.abilities.instabuild && !flag) {
			return ActionResult.fail(itemstack);
		} else {
			playerIn.startUsingItem(handIn);
			return ActionResult.consume(itemstack);
		}
	}

	/**
	 * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
	 */
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
		return arrow;
	}

	public int getDefaultProjectileRange() {
		return 15;
	}
}
