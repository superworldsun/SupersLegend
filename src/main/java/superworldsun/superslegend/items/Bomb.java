package superworldsun.superslegend.items;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import superworldsun.superslegend.config.SupersLegendConfig;
import superworldsun.superslegend.entities.projectiles.items.bomb.BombEntity;

public class Bomb extends Item implements IVanishable {


	private Object LivingEntity;

	public Bomb(Properties properties) {
		super(properties);
	}

	/*@Override
	protected boolean isInGroup(ItemGroup group) {
		return SupersLegendConfig.COMMON.BombEnabled.get() && super.isInGroup(group);
	}*/

	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getUseDuration(ItemStack stack) {
		return 52000;
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			int i = this.getUseDuration(stack) - timeLeft;
			if (i >= 10) {

				BombEntity Bomb = new BombEntity(worldIn, playerentity, stack);
				Bomb.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
				Bomb.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.5F, 0.5F, 0.5F);
				if (playerentity.abilities.isCreativeMode) {
					worldIn.addEntity(Bomb);
				}

				//worldIn.addEntity(Bomb);
				//worldIn.playMovingSound((PlayerEntity) null, Bomb, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
				if (!playerentity.abilities.isCreativeMode) {
					worldIn.addEntity(Bomb);
					playerentity.inventory.deleteStack(stack);
				}
			}
		}
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick( World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(1, attacker, (entity) -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(2, entityLiving, (entity) -> {
				entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});
		}

		return true;
	}
}
