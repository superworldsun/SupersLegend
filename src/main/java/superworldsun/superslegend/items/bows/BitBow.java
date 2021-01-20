package superworldsun.superslegend.items.bows;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.items.Rupee;
import superworldsun.superslegend.lists.ItemList;

public class BitBow extends BowItem
{

	public BitBow(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{

		if (!worldIn.isRemote && playerIn.inventory.hasItemStack(new ItemStack(ItemList.rupee)))
		{
			playerIn.getCooldownTracker().setCooldown(this, 20);

			ArrowItem itemarrow = (ArrowItem)Items.ARROW;
			AbstractArrowEntity entityarrow = itemarrow.createArrow(worldIn, new ItemStack(Items.ARROW), playerIn);
			float arrowVelocity = 2.0F;
			entityarrow.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, arrowVelocity, 1.0F);
			entityarrow.setDamage(1);
			worldIn.addEntity(entityarrow);
			entityarrow.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;

			for (int i = 0; i < playerIn.inventory.getSizeInventory(); i++)
			{
				ItemStack itemStack = playerIn.inventory.getStackInSlot(i);
				if (itemStack.getItem() == ItemList.rupee)
				{
					itemStack.shrink(1);
				}
			}




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
