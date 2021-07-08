package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import net.minecraft.item.Item.Properties;

public class KokiriSet extends Item{

	public KokiriSet(Properties properties)
	{
		super(properties);
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	 {

		 if(player.inventory.getFreeSlot() != -1) {

			 ItemStack stack = player.getItemInHand(hand);
			 {
				 //if(player.inventory.)
				 stack.shrink(1);


				 player.addItem(new ItemStack(ItemList.kokiri_cap));
				 player.addItem(new ItemStack(ItemList.kokiri_tunic));
				 player.addItem(new ItemStack(ItemList.kokiri_leggings));


				 BlockPos currentPos = player.blockPosition();
				 player.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.JAWA, SoundCategory.PLAYERS, 1f, 1f);
			 }
		 }

	return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));

	}


	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GREEN + "kokiri set"));
	}   
} 