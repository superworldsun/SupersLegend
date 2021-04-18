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

public class BugNet extends Item
{

	public BugNet(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand)
	{
		playerIn.swingArm(hand);
		if(entity instanceof BeeEntity)
		{
			{
				for (int i = 0; i < playerIn.inventory.getSizeInventory(); ++i) {
					ItemStack itemStack = playerIn.inventory.getStackInSlot(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						playerIn.addItemStackToInventory(new ItemStack(ItemList.bottled_bee));
						break;
					}
				}
			}
		}
		if(entity instanceof SilverfishEntity)
		{
			{
				for (int i = 0; i < playerIn.inventory.getSizeInventory(); ++i) {
					ItemStack itemStack = playerIn.inventory.getStackInSlot(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						playerIn.addItemStackToInventory(new ItemStack(ItemList.bottled_silverfish));
						break;
					}
				}
			}

		}
		if(entity instanceof EndermiteEntity) {
			{
				for (int i = 0; i < playerIn.inventory.getSizeInventory(); ++i) {
					ItemStack itemStack = playerIn.inventory.getStackInSlot(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						playerIn.addItemStackToInventory(new ItemStack(ItemList.bottled_endermite));
						break;
					}
				}
			}
		}
		return super.itemInteractionForEntity(stack, playerIn, entity, hand);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		playerIn.swingArm(handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
	{
		if(entity instanceof BeeEntity)
		{
			{
				for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
					ItemStack itemStack = player.inventory.getStackInSlot(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						player.addItemStackToInventory(new ItemStack(ItemList.bottled_bee));
						break;
					}
				}
			}
		}
		if(entity instanceof SilverfishEntity)
		{
			{
				for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
					ItemStack itemStack = player.inventory.getStackInSlot(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						player.addItemStackToInventory(new ItemStack(ItemList.bottled_silverfish));
						break;
					}
				}
			}

		}
		if(entity instanceof EndermiteEntity)
		{
			{
				for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
					ItemStack itemStack = player.inventory.getStackInSlot(i);
					if (itemStack.getItem() == Items.GLASS_BOTTLE) {
						itemStack.shrink(1);

						entity.remove();
						player.addItemStackToInventory(new ItemStack(ItemList.bottled_endermite));
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
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.WHITE + "Bottles small critters on right click"));
	}
}
