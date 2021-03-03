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

public class BitBow extends BowItem
{

	public BitBow(Properties properties)
	{
		super(properties);
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote && !playerIn.isCreative() && playerIn.inventory.hasItemStack(new ItemStack(ItemList.rupee)))
		{
			playerIn.getCooldownTracker().setCooldown(this, 15);

			BlockPos currentPos = playerIn.getPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BITBOW_ARROW, SoundCategory.PLAYERS, 3f, 1f);

			ArrowItem itemarrow = (ArrowItem)Items.ARROW;
			AbstractArrowEntity entityarrow = itemarrow.createArrow(worldIn, new ItemStack(Items.ARROW), playerIn);
			float arrowVelocity = 3.0F;
			entityarrow.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, arrowVelocity, 1.0F);
			entityarrow.setDamage(1);
			worldIn.addEntity(entityarrow);
			entityarrow.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;

			for (int i = 0; i < playerIn.inventory.getSizeInventory(); ++i)
			{
				ItemStack stackslot = playerIn.inventory.getStackInSlot(i);
				if (stackslot.getItem() == ItemList.rupee)
				{
					stackslot.shrink(1);
					break;
				}
			}
		}
		else if (!worldIn.isRemote && playerIn.isCreative()) {
			playerIn.getCooldownTracker().setCooldown(this, 15);

			BlockPos currentPos = playerIn.getPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BITBOW_ARROW, SoundCategory.PLAYERS, 3f, 1f);

			ArrowItem itemarrow = (ArrowItem)Items.ARROW;
			AbstractArrowEntity entityarrow = itemarrow.createArrow(worldIn, new ItemStack(Items.ARROW), playerIn);
			float arrowVelocity = 3.0F;
			entityarrow.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, arrowVelocity, 1.0F);
			entityarrow.setDamage(1);
			worldIn.addEntity(entityarrow);
			entityarrow.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
		}
		return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Uses Green Rupee as ammo"));
	}

}