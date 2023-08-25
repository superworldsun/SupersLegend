package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;

import java.util.List;

import static net.minecraft.world.level.block.Blocks.*;

public class CrateBlock extends FallingBlock{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public CrateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

                    //TODO TODO, When previously ported, i was able to make the sounds of the block push based on material,
                    // i can no longer do this. i want to be able to have the sounds be based on groups of blocks instead of picking every type of block i want individually.
                    /*SoundEvent soundEvent;
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
                        soundEvent = SoundInit.BLOCK_PUSH_DIRT.get();*/


    @Override
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
                    Block block = belowState.getBlock();

                    SoundEvent soundEvent;
                    if (belowState.is(BlockTags.STONE_BRICKS) || belowState.is(Tags.Blocks.END_STONES) ||
                            belowState.is(BlockTags.STONE_BUTTONS) || belowState.is(BlockTags.BASE_STONE_NETHER) ||
                            belowState.is(BlockTags.STONE_PRESSURE_PLATES) || belowState.is(BlockTags.REDSTONE_ORES) ||
                            belowState.is(BlockTags.COPPER_ORES) || belowState.is(BlockTags.DIAMOND_ORES) ||
                            belowState.is(BlockTags.LAPIS_ORES) || belowState.is(BlockTags.IRON_ORES) ||
                            belowState.is(BlockTags.EMERALD_ORES) || belowState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES) ||
                            belowState.is(BlockTags.ANCIENT_CITY_REPLACEABLE) && !GRAY_WOOL.equals(block) ||
                            belowState.is(BlockTags.STONE_ORE_REPLACEABLES)
                    ) {
                        soundEvent = SoundInit.BLOCK_PUSH_STONE.get();
                    } else if (belowState.is(BlockTags.PLANKS) || belowState.is(BlockTags.LOGS) ||
                            belowState.is(BlockTags.LOGS_THAT_BURN) || belowState.is(BlockTags.ACACIA_LOGS) ||
                            belowState.is(BlockTags.BIRCH_LOGS) || belowState.is(BlockTags.CHERRY_LOGS) ||
                            belowState.is(BlockTags.DARK_OAK_LOGS) || belowState.is(BlockTags.JUNGLE_LOGS) ||
                            belowState.is(BlockTags.MANGROVE_LOGS) || belowState.is(BlockTags.OAK_LOGS) ||
                            belowState.is(BlockTags.SPRUCE_LOGS) || belowState.is(BlockTags.WOODEN_BUTTONS) ||
                            belowState.is(BlockTags.WOODEN_DOORS) || belowState.is(BlockTags.WOODEN_FENCES) ||
                            belowState.is(BlockTags.WOODEN_SLABS) || belowState.is(BlockTags.WOODEN_STAIRS) ||
                            belowState.is(BlockTags.WOODEN_PRESSURE_PLATES) || belowState.is(BlockTags.WOODEN_TRAPDOORS)) {
                        soundEvent = SoundInit.BLOCK_PUSH_WOOD.get();
                    }
                    else if (belowState.is(BlockTags.ICE) || belowState.is(BlockTags.SNOW)) {
                        soundEvent = SoundInit.BLOCK_PUSH_ICE.get();
                    } else if (GRASS_BLOCK.equals(block) || PODZOL.equals(block) || MYCELIUM.equals(block)) {
                        soundEvent = SoundInit.BLOCK_PUSH_GRASS.get();
                    } else if (belowState.is(BlockTags.SAND) || belowState.is(Tags.Blocks.SAND) ||
                            belowState.is(Tags.Blocks.SAND_RED) || belowState.is(Tags.Blocks.SAND_COLORLESS)) {
                        soundEvent = SoundInit.BLOCK_PUSH_SAND.get();
                    } else if (belowState.equals(FluidTags.LAVA) || MAGMA_BLOCK.equals(block) || belowState.is(BlockTags.FIRE) || TNT.equals(block)) {
                        soundEvent = SoundInit.BLOCK_PUSH_LAVA.get();
                    } else if (belowState.is(BlockTags.WOOL) || belowState.is(BlockTags.WOOL_CARPETS) || belowState.is(BlockTags.BEDS) ||
                            COBWEB.equals(block)) {
                        soundEvent = SoundInit.BLOCK_PUSH_WOOL.get();
                    } else if (belowState.is(BlockTags.CROPS) || CACTUS.equals(block) ||
                            belowState.is(BlockTags.CORAL_BLOCKS) || belowState.is(BlockTags.CORAL_PLANTS) ||
                            belowState.is(BlockTags.CORALS) || belowState.is(BlockTags.WALL_CORALS) ||
                            belowState.is(BlockTags.LEAVES)
                    ) {
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
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState ReplaceableState, FallingBlockEntity fallingBlock) {
        super.onLand(level, pos, state, ReplaceableState, fallingBlock);
        level.playSound((Player) null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}