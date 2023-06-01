package com.superworldsun.superslegend.songs;

import java.util.Optional;

import com.superworldsun.superslegend.blocks.WarpPadBlock;
import com.superworldsun.superslegend.capability.warppads.WarpPadsHelper;
import com.superworldsun.superslegend.warppads.WarpPadsServerData;

import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public abstract class WarpSong extends OcarinaSong {
	protected final WarpPadBlock warpPad;

	public WarpSong(String songPattern, int iconColor, RegistryObject<WarpPadBlock> warpPadObject) {
		super(songPattern, iconColor);
		this.warpPad = warpPadObject.get();
	}

	@Override
	public void onSongPlayed(PlayerEntity player, World level) {
		if (level.isClientSide) {
			return;
		}
		Optional<BlockPos> warpPos = WarpPadsHelper.getWarpPosition(player, warpPad);
		if (!warpPos.isPresent()) {
			player.sendMessage(new TranslationTextComponent("superslegend.message.warp_not_found"), Util.NIL_UUID);
			return;
		}
		WarpPadBlock serverWarpPad = WarpPadsServerData.instance().getWarpPad(warpPos.get());
		if (serverWarpPad == null) {
			player.sendMessage(new TranslationTextComponent("superslegend.message.warp_destroyed"), Util.NIL_UUID);
			return;
		}
		MinecraftServer server = level.getServer();
		String playerName = player.getName().getString();
		String teleportaionCommand = "tp " + playerName + " " + warpPos.get().getX() + " " + warpPos.get().getY() + " " + warpPos.get().getZ();
		new Commands(Commands.EnvironmentType.ALL).performCommand(server.createCommandSourceStack(), teleportaionCommand);
	}
}
