package com.superworldsun.superslegend.items;

import java.util.Set;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.SupersLegendRegistries;
import com.superworldsun.superslegend.songs.LearnedSongsProvider;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AllSongsSheet extends Item
{
	public AllSongsSheet()
	{
		super(new Item.Properties().tab(SupersLegendMain.RESOURCES).stacksTo(1));
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
	{
		Set<OcarinaSong> learnedSongs = LearnedSongsProvider.get(playerEntity).getLearnedSongs();
		
		if (!world.isClientSide)
		{
			SupersLegendRegistries.OCARINA_SONGS.forEach(song ->
			{
				if (!learnedSongs.contains(song))
				{
					learnedSongs.add(song);
				}
			});
			
			LearnedSongsProvider.sync((ServerPlayerEntity) playerEntity);
		}
		
		return ActionResult.success(playerEntity.getItemInHand(hand));
	}
}
