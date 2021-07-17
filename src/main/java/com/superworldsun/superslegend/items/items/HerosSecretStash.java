package com.superworldsun.superslegend.items.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class HerosSecretStash extends Item
{

	public static final TranslationTextComponent CONTAINER_TITLE = new TranslationTextComponent("container.enderchest");
	
	public HerosSecretStash(Properties properties)
	{
		super(properties);
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		
		EnderChestInventory enderChest = player.getEnderChestInventory();
		
		if (enderChest != null)
		{
			if (!world.isClientSide)
			{
				BlockPos currentPos = player.blockPosition();
				 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 1f, 1f);
				 player.openMenu(new SimpleNamedContainerProvider((p_220114_1_, p_220114_2_, p_220114_3_) -> {
		               return ChestContainer.threeRows(p_220114_1_, p_220114_2_, enderChest);
		            }, CONTAINER_TITLE));
			}
		}
		return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
	}
	 
    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.ITALIC + "A safe place for things on the go"));
	}  

}