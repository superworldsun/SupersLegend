package superworldsun.superslegend.items.bows;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
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

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if (player.inventory.hasItemStack(new ItemStack(ItemList.rupee)))
        {
            ArrowEntity ent = new ArrowEntity(world, player); ent.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);

			 world.addEntity(ent);


            for (int i = 0; i < player.inventory.getSizeInventory(); i++)
            {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack .getItem() == ItemList.rupee)
                {
                    itemStack .shrink(stack.getMaxStackSize());
                }
            }

            player.getCooldownTracker().setCooldown(this, 30);


            return super.onItemRightClick(world, player, hand);
        }else{
            return null;
        }
    }

	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Uses Green Rupee as ammo"));
	}  
	
}
