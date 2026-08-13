package com.superworldsun.superslegend.songs;

import com.superworldsun.superslegend.blocks.WarpPadBlock;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.warppads.WarpPadsServerData;
import com.superworldsun.superslegend.warppads.WarpPadsStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public abstract class WarpSong extends OcarinaSong {
    protected final WarpPadBlock warpPad;

    public WarpSong(String songPattern, int iconColor, RegistryObject<WarpPadBlock> warpPadObject) {
        super(songPattern, iconColor);
        this.warpPad = warpPadObject.get();
    }

    @Override
    public void onSongPlayed(Player player, Level level) {
        if (level.isClientSide()) return;

        Optional<BlockPos> warpPos = WarpPadsStorage.getWarpPosition(player, warpPad);
        if (warpPos.isEmpty()) {
         player.sendSystemMessage(
                 Component.translatable("superslegend.message.warp_not_found")
                         .withStyle(ChatFormatting.DARK_RED)
         );
         return;
        }

        MinecraftServer server = level.getServer();
        if (server == null) return;

        WarpPadBlock serverWarpPad = WarpPadsServerData.instance(server).getWarpPad(warpPos.get());
        if (serverWarpPad != warpPad) {
         player.sendSystemMessage(Component.translatable("superslegend.message.warp_destroyed")
                 .withStyle(ChatFormatting.DARK_RED));
         return;
        }

        BlockPos pos = warpPos.get();
        player.teleportTo(pos.getX() + 0.5, pos.getY() , pos.getZ() + 0.5);
        if (player instanceof ServerPlayer serverPlayer) {
            ModAdvancementHelper.recordWarpPadTeleport(serverPlayer, warpPad);
        }
    }
}