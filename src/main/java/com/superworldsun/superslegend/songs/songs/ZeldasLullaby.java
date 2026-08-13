package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.blocks.RoyalTileBlock;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.registries.ItemInit;

public class ZeldasLullaby extends OcarinaSong {
    private static final int EFFECT_RADIUS = 5;

    public ZeldasLullaby() {
        super("lurlur", 0x625CAD);
    }

    @Override
    public SoundEvent getPlayingSound() {
        return SoundInit.ZELDAS_LULLABY.get();
    }

    @Override
    public boolean requiresOcarinaOfTime() {
        return false;
    }

    @Override
    public void onSongPlayed(Player player, Level level) {
        final boolean[] activated = {false};
        getBlocksInAreaOfEffect(player).forEach(pos -> {
            activated[0] |= activateRoyalTile(level, pos);
        });
        if (activated[0] && player instanceof ServerPlayer serverPlayer
                && (player.getMainHandItem().is(ItemInit.OCARINA_OF_TIME.get())
                || player.getOffhandItem().is(ItemInit.OCARINA_OF_TIME.get())))
            ModAdvancementHelper.award(serverPlayer, "royal_resonance", "activated_tile");
    }

    private boolean activateRoyalTile(Level level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block instanceof RoyalTileBlock) {
            RoyalTileBlock royalTileBlock = (RoyalTileBlock) block;
            royalTileBlock.activate(level, blockState, pos);
            return true;
        }
        return false;
    }

    private Iterable<BlockPos> getBlocksInAreaOfEffect(Player player) {
        BlockPos start = player.blockPosition().offset(-EFFECT_RADIUS, -EFFECT_RADIUS, -EFFECT_RADIUS);
        BlockPos end = player.blockPosition().offset(EFFECT_RADIUS, EFFECT_RADIUS, EFFECT_RADIUS);
        return BlockPos.betweenClosed(start, end);
    }
}
