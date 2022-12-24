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

	//TODO When the Ocarina Gui is open the player can only play 8 notes and must cancel the gui to play any more.
	// Have it so if the list of notes is full and a new one is made it will start a new. Or if no note is played for a couple seconds

	//TODO when you play songs, you have to play perfectly. Make it so that even if you dont play the song on the first
	// note you can still have the song played as long as the right notes are eventually played
	
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
		list.add(new StringTextComponent(TextFormatting.BLUE + "A Ocarina to control time"));
	}
	
	@OnlyIn(value = Dist.CLIENT)
	private void showOcarinaScreen()
	{
		Minecraft client = Minecraft.getInstance();
		client.setScreen(new OcarinaScreen());
	}
}
