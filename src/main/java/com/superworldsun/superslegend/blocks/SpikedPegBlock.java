package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpikedPegBlock extends Block {

    private static final Map<UUID, Long> POP_UP_DAMAGE_PROTECTION = new HashMap<>();

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape HITBOX_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 24.0D, 14.0D);

    public SpikedPegBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return HITBOX_SHAPE;
    }

    @Override
    public void attack(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, Player player)
    {
        if (player.isHolding(ItemInit.SKULL_HAMMER.get()))
        {
            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundSource.PLAYERS, 1f, 1f);

            world.setBlock(pos, BlockInit.HAMMERED_SPIKED_PEG_BLOCK.get().defaultBlockState(), 3);
        }
        super.attack(state, world, pos, player);
    }

    @Override
    public void entityInside(@NotNull BlockState pState, Level level, @NotNull BlockPos pPos, Entity entity) {
        if (entity instanceof Player player && isProtectedFromPopUp(player, level.getGameTime())) {
            return;
        }
        boolean damaged = entity.hurt(level.damageSources().cactus(), 2.0F);

        if (!level.isClientSide && damaged && entity instanceof LivingEntity livingEntity) {
            Vec3 movementAfterDamage = livingEntity.getDeltaMovement();
            if (movementAfterDamage.y > 0.0D) {
                // A jump timed with the damage frame can combine with vanilla's
                // hurt response and become a damage boost. The peg remains fully
                // standable, but a successful damage tick can never add lift.
                livingEntity.setDeltaMovement(movementAfterDamage.x, 0.0D,
                        movementAfterDamage.z);
                livingEntity.hurtMarked = true;
            }
        }
    }

    static void protectFromPopUp(Player player, long gameTime) {
        POP_UP_DAMAGE_PROTECTION.put(player.getUUID(), gameTime + 3L);
    }

    private static boolean isProtectedFromPopUp(Player player, long gameTime) {
        Long protectedUntil = POP_UP_DAMAGE_PROTECTION.get(player.getUUID());
        if (protectedUntil == null) {
            return false;
        }
        if (gameTime <= protectedUntil) {
            return true;
        }
        POP_UP_DAMAGE_PROTECTION.remove(player.getUUID());
        return false;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
        return false;
    }

}
