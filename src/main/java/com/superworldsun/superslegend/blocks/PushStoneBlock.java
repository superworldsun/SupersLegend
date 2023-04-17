package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

import static net.minecraft.block.material.Material.*;

public class PushStoneBlock extends FallingBlock{
	private static final DirectionProperty FACING = HorizontalBlock.FACING;

	public PushStoneBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GORONS_BRACELET.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		ItemStack stack2 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.SILVER_GAUNTLETS.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		ItemStack stack3 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GOLDEN_GAUNTLETS.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

		// Check if player is wearing it.
		if (player.isOnGround()) {
			if (!stack.isEmpty() || !stack2.isEmpty() || !stack3.isEmpty()) {
				if (hit.getDirection().getAxis().isHorizontal()) {
					double playerY = player.getY() - pos.getY();
					if (playerY > 0.2 || playerY < -0.2) {
						return ActionResultType.PASS;
					}
					Direction pushDir = hit.getDirection();
					if (player.isShiftKeyDown()) {
						pushDir = pushDir.getOpposite();
					}
					BlockPos pushPos = pos.relative(pushDir.getOpposite());
					if (world.isEmptyBlock(pushPos)) {
						AxisAlignedBB aabb = new AxisAlignedBB(pushPos);
						List<Entity> entities = world.getEntitiesOfClass(Entity.class, aabb);
						for (Entity entity : entities) {
							if (entity instanceof LivingEntity) {
								return ActionResultType.PASS;
							}
						}
						BlockState belowState = world.getBlockState(pos.below());
						Material material = belowState.getMaterial();
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
						world.playSound((PlayerEntity) null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
						world.setBlock(pushPos, state, 3);
						world.removeBlock(pos, false);
						return ActionResultType.SUCCESS;
					}
				}
			}
		}
		return ActionResultType.PASS;
	}


	/*@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (hit.getDirection().getAxis().isHorizontal()) {
			Direction pushDir = hit.getDirection();
			if (player.isShiftKeyDown()) {
				pushDir = pushDir.getOpposite();
			}
			BlockPos pushPos = pos.relative(pushDir.getOpposite());
			if (world.isEmptyBlock(pushPos)) {
				BlockState belowState = world.getBlockState(pos.below());
				Material material = belowState.getMaterial();
				SoundEvent soundEvent;
				if (DIRT.equals(material) || CLAY.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_DIRT.get();
				}
				else if (STONE.equals(material) || HEAVY_METAL.equals(material) || METAL.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_STONE.get();
				}
				else if (WOOD.equals(material) || NETHER_WOOD.equals(material) || BAMBOO.equals(material) || BAMBOO_SAPLING.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_WOOD.get();
				}
				else if (ICE.equals(material) || ICE_SOLID.equals(material) || SNOW.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_ICE.get();
				}
				else if (GRASS.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_GRASS.get();
				}
				else if (SAND.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_SAND.get();
				}
				else if (LAVA.equals(material) || FIRE.equals(material) || EXPLOSIVE.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_LAVA.get();
				}
				else if (WOOL.equals(material) || CLOTH_DECORATION.equals(material) || WEB.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_WOOL.get();
				}
				else if (LEAVES.equals(material) || CACTUS.equals(material) || CORAL.equals(material) || VEGETABLE.equals(material)) {
					soundEvent = SoundInit.BLOCK_PUSH_FLESH.get();
				}
				else {
					soundEvent = SoundInit.BLOCK_PUSH_DIRT.get();
				}
				world.playSound((PlayerEntity) null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.setBlock(pushPos, state, 3);
				world.removeBlock(pos, false);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}*/


	public void onLand(World world, BlockPos pos, BlockState state, BlockState fallState, FallingBlockEntity fallingBlock) {
		world.playSound((PlayerEntity)null, pos, SoundEvents.STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
		super.neighborChanged(state, world, pos, block, neighborPos, isMoving);
		world.getBlockTicks().scheduleTick(pos, this, 2);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
