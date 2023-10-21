package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
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
import net.minecraftforge.common.Tags;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.minecraft.world.level.block.Blocks.*;

public class SilverPushStone extends FallingBlock{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public SilverPushStone(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private static final Set<TagKey<Block>> StoneTags = new HashSet<>(Arrays.asList(
            BlockTags.STONE_BRICKS, Tags.Blocks.END_STONES, Tags.Blocks.END_STONES,
            BlockTags.STONE_BUTTONS, BlockTags.BASE_STONE_NETHER, BlockTags.STONE_PRESSURE_PLATES,
            BlockTags.REDSTONE_ORES, BlockTags.COPPER_ORES, BlockTags.DIAMOND_ORES,
            BlockTags.LAPIS_ORES, BlockTags.IRON_ORES, BlockTags.EMERALD_ORES,
            BlockTags.DEEPSLATE_ORE_REPLACEABLES, BlockTags.ANCIENT_CITY_REPLACEABLE, BlockTags.STONE_ORE_REPLACEABLES
    ));
    private static final Set<Block> StoneBlocks = new HashSet<>(Arrays.asList(
            END_STONE_BRICKS,
            END_STONE_BRICK_SLAB,
            END_STONE_BRICK_STAIRS,
            END_STONE_BRICK_WALL
    ));

    private static final Set<TagKey<Block>> WoodTags = new HashSet<>(Arrays.asList(
            BlockTags.PLANKS, BlockTags.LOGS, BlockTags.LOGS_THAT_BURN,
            BlockTags.ACACIA_LOGS, BlockTags.BIRCH_LOGS, BlockTags.CHERRY_LOGS,
            BlockTags.DARK_OAK_LOGS, BlockTags.CHERRY_LOGS, BlockTags.DARK_OAK_LOGS,
            BlockTags.JUNGLE_LOGS, BlockTags.MANGROVE_LOGS, BlockTags.OAK_LOGS,
            BlockTags.SPRUCE_LOGS, BlockTags.WOODEN_BUTTONS, BlockTags.WOODEN_DOORS,
            BlockTags.WOODEN_FENCES, BlockTags.WOODEN_SLABS, BlockTags.WOODEN_STAIRS,
            BlockTags.WOODEN_PRESSURE_PLATES, BlockTags.WOODEN_TRAPDOORS
    ));

    private static final Set<TagKey<Block>> IceTags = new HashSet<>(Arrays.asList(
            BlockTags.ICE,
            BlockTags.SNOW
    ));

    private static final Set<Block> GrassBlocks = new HashSet<>(Arrays.asList(
            GRASS_BLOCK,
            PODZOL,
            MYCELIUM
    ));

    private static final Set<TagKey<Block>> SandTags = new HashSet<>(Arrays.asList(
            BlockTags.SAND,
            Tags.Blocks.SAND,
            Tags.Blocks.SAND_RED,
            Tags.Blocks.SAND_COLORLESS
    ));

    private static final Set<Block> FireBlocks = new HashSet<>(Arrays.asList(
            TNT,
            MAGMA_BLOCK
    ));

    private static final Set<TagKey<Block>> WoolTags = new HashSet<>(Arrays.asList(
            BlockTags.WOOL,
            BlockTags.WOOL_CARPETS,
            BlockTags.BEDS
    ));

    private static final Set<TagKey<Block>> CropTags = new HashSet<>(Arrays.asList(
            BlockTags.CROPS,
            BlockTags.CORAL_BLOCKS,
            BlockTags.CORAL_PLANTS,
            BlockTags.WALL_CORALS,
            BlockTags.CORAL_PLANTS,
            BlockTags.LEAVES
    ));

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit)
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
                        if (belowState.getTags().anyMatch(blockTagKey -> StoneTags.contains(blockTagKey)) || StoneBlocks.contains(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_STONE.get();
                        } else if (belowState.getTags().anyMatch(blockTagKey -> WoodTags.contains(blockTagKey))) {
                            soundEvent = SoundInit.BLOCK_PUSH_WOOD.get();
                        } else if (belowState.getTags().anyMatch(blockTagKey -> IceTags.contains(blockTagKey))) {
                            soundEvent = SoundInit.BLOCK_PUSH_ICE.get();
                        } else if (GrassBlocks.contains(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_GRASS.get();
                        } else if (belowState.getTags().anyMatch(blockTagKey -> SandTags.contains(blockTagKey))) {
                            soundEvent = SoundInit.BLOCK_PUSH_SAND.get();
                        } else if (belowState.equals(FluidTags.LAVA) || belowState.is(BlockTags.FIRE) || FireBlocks.contains(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_LAVA.get();
                        } else if (belowState.getTags().anyMatch(blockTagKey -> WoolTags.contains(blockTagKey)) || COBWEB.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_WOOL.get();
                        } else if (belowState.getTags().anyMatch(blockTagKey -> CropTags.contains(blockTagKey)) || CACTUS.equals(block)) {
                            soundEvent = SoundInit.BLOCK_PUSH_FLESH.get();
                        } else {
                            soundEvent = SoundInit.BLOCK_PUSH_DIRT.get();
                        }
                        level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
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
    public void onLand(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BlockState ReplaceableState, @NotNull FallingBlockEntity fallingBlock) {
        super.onLand(level, pos, state, ReplaceableState, fallingBlock);
        level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}