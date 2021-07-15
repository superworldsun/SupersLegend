package com.superworldsun.superslegend.items.weapons;

import com.superworldsun.superslegend.items.custom.ItemCustomSword;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import java.util.List;

public class MasterSword extends ItemCustomSword
{

	public MasterSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	//TODO CHANGE ENTITY TO NEW ONE (ARROW PLACE HOLDER)

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getItemInHand(handIn);
		playerIn.swing(handIn);
		if (!worldIn.isClientSide && !playerIn.isCreative() && !playerIn.isHurt())
		{
			playerIn.getCooldowns().addCooldown(this, 15);

			BlockPos currentPos = playerIn.blockPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BITBOW_ARROW.get(), SoundCategory.PLAYERS, 3f, 1f);

			ArrowEntity arrow = new ArrowEntity(playerIn.level, playerIn);
			arrow.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
			playerIn.level.addFreshEntity(arrow);
		}
		else if (!worldIn.isClientSide && playerIn.isCreative()) {
			playerIn.getCooldowns().addCooldown(this, 15);

			BlockPos currentPos = playerIn.blockPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BITBOW_ARROW.get(), SoundCategory.PLAYERS, 3f, 1f);

			//TODO PORT OVER ENTITY WHEN ADDED
			/*EntityArrowFire firearrow = new EntityArrowFire(playerIn.level, playerIn);
			firearrow.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
			playerIn.level.addFreshEntity(firearrow);*/
		}
		return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
	}


	/*public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getHeldItemMainhand();
			if(!world.isRemote)
			{
				if(stack == equipped && !player.shouldHeal())
				{
					if player.swingArm(Hand.MAIN_HAND);
					{
						EntityArrowFire firearrow = new EntityArrowFire(player.world, player);
						firearrow.shootFromRotation(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
						player.world.addEntity(firearrow);
					}
				}
			}
		}
	}*/


	/*@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity playerIn)
	{
			{
				if (playerIn.getHealth() >= playerIn.getMaxHealth()) {

					EntityArrowFire firearrow = new EntityArrowFire(playerIn.world, playerIn);
					firearrow.shootFromRotation(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
					playerIn.world.addEntity(firearrow);
				}
		}
		return true;
	}*/

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GRAY + "Blade of Evil's Bane"));
	}
}
