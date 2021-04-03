package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.lists.ItemList;

import java.util.List;

public class GiantsKnife extends ItemCustomSword
{
	public GiantsKnife(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GRAY + "A Large Sword that requires two hands to wield"));
	}

	/*@Override
	public int getDamage(ItemStack stack) {
		if (stack.getDamage() <= 2)
		{

		}
		return 0;
	}*/

	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getHeldItemMainhand();
			{
				if(stack == equipped)
					{
						if(player.hasItemInSlot(EquipmentSlotType.OFFHAND))
						{
							player.entityDropItem(player.getHeldItemOffhand());
							player.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
						}
					}
			}
		}
	}
}
