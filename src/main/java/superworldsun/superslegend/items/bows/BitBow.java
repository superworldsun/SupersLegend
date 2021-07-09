package superworldsun.superslegend.items.bows;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

import java.util.List;

import net.minecraft.item.Item.Properties;

public class BitBow extends BowItem
{

	public BitBow(Properties properties)
	{
		super(properties);
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getItemInHand(handIn);
		if (!worldIn.isClientSide && !playerIn.isCreative() && playerIn.inventory.contains(new ItemStack(ItemList.rupee)))
		{
			playerIn.getCooldowns().addCooldown(this, 15);

			BlockPos currentPos = playerIn.blockPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BITBOW_ARROW, SoundCategory.PLAYERS, 3f, 1f);

			ArrowItem itemarrow = (ArrowItem)Items.ARROW;
			AbstractArrowEntity entityarrow = itemarrow.createArrow(worldIn, new ItemStack(Items.ARROW), playerIn);
			float arrowVelocity = 3.0F;
			entityarrow.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, arrowVelocity, 1.0F);
			entityarrow.setBaseDamage(1);
			worldIn.addFreshEntity(entityarrow);
			entityarrow.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;

			for (int i = 0; i < playerIn.inventory.getContainerSize(); ++i)
			{
				ItemStack stackslot = playerIn.inventory.getItem(i);
				if (stackslot.getItem() == ItemList.rupee)
				{
					stackslot.shrink(1);
					break;
				}
			}
		}
		else if (!worldIn.isClientSide && playerIn.isCreative()) {
			playerIn.getCooldowns().addCooldown(this, 15);

			BlockPos currentPos = playerIn.blockPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BITBOW_ARROW, SoundCategory.PLAYERS, 3f, 1f);

			ArrowItem itemarrow = (ArrowItem)Items.ARROW;
			AbstractArrowEntity entityarrow = itemarrow.createArrow(worldIn, new ItemStack(Items.ARROW), playerIn);
			float arrowVelocity = 3.0F;
			entityarrow.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, arrowVelocity, 1.0F);
			entityarrow.setBaseDamage(1);
			worldIn.addFreshEntity(entityarrow);
			entityarrow.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
		}
		return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Uses Green Rupee as ammo"));
	}

}