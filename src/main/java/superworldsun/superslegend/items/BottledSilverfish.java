package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

import java.util.List;

public class BottledSilverfish extends Item
{

	public BottledSilverfish(Properties properties)
	{
		super(properties);
	}

	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if(!world.isRemote && !player.isCreative())
		{
			BlockPos currentPos = player.getPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOTTLE_POP, SoundCategory.PLAYERS, 1f, 1f);

			if (!player.world.isRemote) {
				SilverfishEntity entity = EntityType.SILVERFISH.create(player.world);
				entity.setLocationAndAngles(player.getPosX(), player.getPosY(), player.getPosZ(), player.rotationYaw, 0.0F);
				player.world.addEntity(entity);
				player.getCooldownTracker().setCooldown(this, 6);
			}
			stack.shrink(1);
			player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		}

		if(!world.isRemote && player.isCreative())
		{
			BlockPos currentPos = player.getPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOTTLE_POP, SoundCategory.PLAYERS, 1f, 1f);

			if (!player.world.isRemote) {
				SilverfishEntity entity = EntityType.SILVERFISH.create(player.world);
				entity.setLocationAndAngles(player.getPosX(), player.getPosY(), player.getPosZ(), player.rotationYaw, 0.0F);
				player.world.addEntity(entity);
				player.getCooldownTracker().setCooldown(this, 6);
			}
		}

		return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
	}


	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "A captive Silverfish in a bottle"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-Click to Release"));
	}   
}
