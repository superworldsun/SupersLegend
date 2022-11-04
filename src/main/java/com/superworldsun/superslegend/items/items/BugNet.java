package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.registries.ItemInit;
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
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BugNet extends Item
{

	public BugNet(Properties properties)
	{
		super(properties);
	}

	//TODO make it so when a entity is bottled, their Data is saved.(name, health, effects, anger, etc)

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
						playerIn.addItem(new ItemStack(ItemInit.BOTTLED_BEE.get()));
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
						playerIn.addItem(new ItemStack(ItemInit.BOTTLED_SILVERFISH.get()));
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
						playerIn.addItem(new ItemStack(ItemInit.BOTTLED_ENDERMITE.get()));
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
						player.addItem(new ItemStack(ItemInit.BOTTLED_BEE.get()));
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
						player.addItem(new ItemStack(ItemInit.BOTTLED_SILVERFISH.get()));
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
						player.addItem(new ItemStack(ItemInit.BOTTLED_ENDERMITE.get()));
						break;
					}
				}
			}

		}


		return true;
	}

	//Tried making different ways to catch
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

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.WHITE + "Bottles small critters on right click"));
	}
}
