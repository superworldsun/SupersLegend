package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowFire;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class BugNet extends Item
{

	public BugNet(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand)
	{
		playerIn.swing(hand);
		if(entity instanceof BeeEntity)
		{
			{
				for (int i = 0; i < playerIn.inventory.getContainerSize(); ++i) {
					ItemStack itemStack = playerIn.inventory.getItem(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						playerIn.addItem(new ItemStack(ItemList.bottled_bee));
						break;
					}
				}
			}
		}
		if(entity instanceof SilverfishEntity)
		{
			{
				for (int i = 0; i < playerIn.inventory.getContainerSize(); ++i) {
					ItemStack itemStack = playerIn.inventory.getItem(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						playerIn.addItem(new ItemStack(ItemList.bottled_silverfish));
						break;
					}
				}
			}

		}
		if(entity instanceof EndermiteEntity) {
			{
				for (int i = 0; i < playerIn.inventory.getContainerSize(); ++i) {
					ItemStack itemStack = playerIn.inventory.getItem(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						playerIn.addItem(new ItemStack(ItemList.bottled_endermite));
						break;
					}
				}
			}
		}
		return super.interactLivingEntity(stack, playerIn, entity, hand);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		playerIn.swing(handIn);
		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
	{
		if(entity instanceof BeeEntity)
		{
			{
				for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
					ItemStack itemStack = player.inventory.getItem(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						player.addItem(new ItemStack(ItemList.bottled_bee));
						break;
					}
				}
			}
		}
		if(entity instanceof SilverfishEntity)
		{
			{
				for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
					ItemStack itemStack = player.inventory.getItem(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						player.addItem(new ItemStack(ItemList.bottled_silverfish));
						break;
					}
				}
			}

		}
		if(entity instanceof EndermiteEntity)
		{
			{
				for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
					ItemStack itemStack = player.inventory.getItem(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						player.addItem(new ItemStack(ItemList.bottled_endermite));
						break;
					}
				}
			}

		}


		return true;
	}


	/*@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		//acts as a cooldown.
		if (player.isSwingInProgress) {
			return new ActionResult<>(ActionResultType.PASS, stack);
		}
		player.swingArm(hand);
		if (!player.isSneaking()) {
			world.playSound(null, player.getPosition(), SoundInit.BOOMERANG_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

		}
		return super.onItemRightClick(world, player, hand);
	}*/

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.WHITE + "Bottles small critters on right click"));
	}
}
