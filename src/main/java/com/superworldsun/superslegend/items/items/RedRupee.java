package com.superworldsun.superslegend.items.items;

import java.util.List;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

public class RedRupee extends Item{

	public RedRupee(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		if(stack.getCount() < 5)
		{

		}
		else if(stack.getCount() >= 5)
		{
			addOrDrop(player, ItemInit.SILVER_RUPEE);

			BlockPos currentPos = player.blockPosition();
			player.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.RUPEE_RED.get(), SoundCategory.PLAYERS, 1f, 1f);

			if (!player.abilities.instabuild)
			{
				stack.shrink(5);
			}
		}
		return ActionResult.success(stack);
	}

	private void addOrDrop(PlayerEntity player, RegistryObject<SilverRupee> itemSupplier)
	{
		if (!player.addItem(new ItemStack(itemSupplier.get())))
		{
			player.spawnAtLocation(itemSupplier.get());
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "20 Rupee"));
	}
} 