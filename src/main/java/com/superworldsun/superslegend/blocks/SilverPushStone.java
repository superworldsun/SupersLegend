package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

import static net.minecraft.world.item.Tiers.WOOD;
import static net.minecraft.world.level.block.Blocks.BAMBOO;
import static net.minecraft.world.level.block.Blocks.BAMBOO_SAPLING;
import static net.minecraft.world.level.block.Blocks.STONE;
import static net.minecraft.world.level.block.Blocks.*;
import static net.minecraft.world.level.block.SoundType.*;

public class SilverPushStone extends FallingBlock{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public SilverPushStone(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    /*@Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (player.onGround()) {
            if (hit.getDirection().getAxis().isHorizontal()) {
                double playerY = player.getY() - pos.getY();
                if (playerY > 0.2 || playerY < -0.2) {
                    return InteractionResult.PASS;
                }
                Direction pushDir = hit.getDirection();
                if (player.isShiftKeyDown()) {
                    pushDir = pushDir.getOpposite();
                }
                BlockPos pushPos = pos.relative(pushDir.getOpposite());
                if (level.isEmptyBlock(pushPos)) {
                    AABB aabb = new AABB(pushPos);
                    List<Entity> entities = level.getEntitiesOfClass(Entity.class, aabb);
                    for (Entity entity : entities) {
                        if (entity instanceof LivingEntity) {
                            return InteractionResult.PASS;
                        }
                    }

                    BlockState belowState = level.getBlockState(pos.below());
                    Block material = belowState.getBlock();

                    //TODO, When previously ported, i was able to make the sounds of the block push based on material, i can no longer do this.
                        i want to be able to have the sounds be based on groups of blocks instead of picking every type of block i want individually.
                    SoundEvent soundEvent;
                    if (DIRT.equals(material) || CLAY.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_DIRT.get();
                    } else if (STONE.equals(material) || HEAVY_METAL.equals(material) || METAL.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_STONE.get();
                    } else if (WOOD.equals(material) || NETHER_WOOD.equals(material) || BAMBOO.equals(material) || BAMBOO_SAPLING.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_WOOD.get();
                    } else if (ICE.equals(material) || ICE_SOLID.equals(material) || SNOW.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_ICE.get();
                    } else if (GRASS.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_GRASS.get();
                    } else if (SAND.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_SAND.get();
                    } else if (LAVA.equals(material) || FIRE.equals(material) || EXPLOSIVE.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_LAVA.get();
                    } else if (WOOL.equals(material) || CLOTH_DECORATION.equals(material) || WEB.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_WOOL.get();
                    } else if (LEAVES.equals(material) || CACTUS.equals(material) || CORAL.equals(material) || VEGETABLE.equals(material)) {
                        soundEvent = SoundInit.BLOCK_PUSH_FLESH.get();
                    } else {
                        soundEvent = SoundInit.BLOCK_PUSH_DIRT.get();
                    }
                    level.playSound((Player) null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(pushPos, state, 3);
                    level.removeBlock(pos, false);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }*/


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.SILVER_GAUNTLETS.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack stack2 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GOLDEN_GAUNTLETS.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (player.onGround()) {
            if (!stack.isEmpty() || !stack2.isEmpty()) {
                if (hit.getDirection().getAxis().isHorizontal()) {
                    double playerY = player.getY() - pos.getY();
                    if (playerY > 0.2 || playerY < -0.2) {
                        return InteractionResult.PASS;
                    }
                    Direction pushDir = hit.getDirection();
                    if (player.isShiftKeyDown()) {
                        pushDir = pushDir.getOpposite();
                    }
                    BlockPos pushPos = pos.relative(pushDir.getOpposite());
                    if (level.isEmptyBlock(pushPos)) {
                        AABB aabb = new AABB(pushPos);
                        List<Entity> entities = level.getEntitiesOfClass(Entity.class, aabb);
                        for (Entity entity : entities) {
                            if (entity instanceof LivingEntity) {
                                return InteractionResult.PASS;
                            }
                        }
                        BlockState belowState = level.getBlockState(pos.below());
                        Block block = belowState.getBlock();

                        SoundEvent soundEvent;
                        if (DIRT.equals(block) || CLAY.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_DIRT.get();
                        } else if (STONE.equals(block) || SoundType.METAL.equals(soundType)) {
                            soundEvent = SoundInit.BLOCK_PUSH_STONE.get();
                        } else if (WOOD.equals(block) || NETHER_WOOD.equals(block) || BAMBOO.equals(block) || BAMBOO_SAPLING.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_WOOD.get();
                        } else if (ICE.equals(block) || SoundType.SNOW.equals(soundType)) {
                            soundEvent = SoundInit.BLOCK_PUSH_ICE.get();
                        } else if (Blocks.GRASS.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_GRASS.get();
                        } else if (Blocks.SAND.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_SAND.get();
                        } else if (LAVA.equals(block) || FIRE.equals(block) || TNT.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_LAVA.get();
                        } else if (WOOL.equals(block) || SoundType.WOOL.equals(soundType) || COBWEB.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_WOOL.get();
                        } else if (CROP.equals(block) || CACTUS.equals(block) || CORAL_BLOCK.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_FLESH.get();
                        } else {
                            soundEvent = SoundInit.BLOCK_PUSH_DIRT.get();
                        }
                        level.playSound((Player) null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.setBlock(pushPos, state, 3);
                        level.removeBlock(pos, false);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState ReplaceableState, FallingBlockEntity fallingBlock) {
        super.onLand(level, pos, state, ReplaceableState, fallingBlock);
        level.playSound((Player) null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}