package com.superworldsun.superslegend.items.items;

import java.util.List;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.client.screen.OcarinaScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

public class OcarinaOfTime extends Item
{
	public OcarinaOfTime(Properties properties)
	{
		super(properties);
	}

	//TODO When the player plays any notes from the ocarina, make it so other players can hear the notes
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> showOcarinaScreen());
		return ActionResult.success(player.getItemInHand(hand));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		list.add(new StringTextComponent(TextFormatting.BLUE + "An Ocarina to Control Time"));
	}
	
	@OnlyIn(value = Dist.CLIENT)
	private void showOcarinaScreen()
	{
		Minecraft client = Minecraft.getInstance();
		client.setScreen(new OcarinaScreen());
	}
}
