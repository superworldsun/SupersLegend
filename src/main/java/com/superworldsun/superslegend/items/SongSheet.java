package com.superworldsun.superslegend.items;

import java.util.Set;
import java.util.function.Supplier;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.songs.LearnedSongsProvider;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SongSheet extends Item
{
	private final Supplier<OcarinaSong> songSupplier;
	
	public SongSheet(Supplier<OcarinaSong> songSupplier)
	{
		super(new Item.Properties().tab(SupersLegendMain.RESOURCES).stacksTo(1));
		this.songSupplier = songSupplier;
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		Set<OcarinaSong> learnedSongs = LearnedSongsProvider.get(playerIn).getLearnedSongs();
		
		if (!learnedSongs.contains(songSupplier.get()))
		{
			if (!worldIn.isClientSide)
			{
				learnedSongs.add(songSupplier.get());
				LearnedSongsProvider.sync((ServerPlayerEntity) playerIn);
			}
			
			return ActionResult.success(playerIn.getItemInHand(handIn));
		}
		
		return ActionResult.fail(playerIn.getItemInHand(handIn));
	}
}
