package com.superworldsun.superslegend.blocks;

import java.util.Optional;
import java.util.Random;

import com.superworldsun.superslegend.blocks.entity.ShadowTileEntity;
import com.superworldsun.superslegend.items.block.ShadowBlockBaseItem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.DiggingParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShadowBlock extends Block {
	public ShadowBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ShadowTileEntity();
	}

	@Override
	public boolean addLandingEffects(BlockState state1, ServerWorld worldserver, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
		getTileEntity(worldserver, pos).ifPresent(t -> {
			worldserver.sendParticles(getBlockParticles(t), entity.getX(), entity.getY(), entity.getZ(), numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15F);
		});

		return true;
	}

	@Override
	public boolean addRunningEffects(BlockState state, World world, BlockPos pos, Entity entity) {
		getTileEntity(world, pos).ifPresent(t -> {
			Vector3d vector3d = entity.getDeltaMovement();
			Random random = new Random();
			world.addParticle(getBlockParticles(t).setPos(pos), entity.getX() + (random.nextDouble() - 0.5D) * entity.dimensions.width, entity.getY() + 0.1D,
					entity.getZ() + (random.nextDouble() - 0.5D) * entity.dimensions.width, vector3d.x * -4.0D, 1.5D, vector3d.z * -4.0D);
		});

		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean addHitEffects(BlockState state, World world, RayTraceResult target, ParticleManager manager) {
		BlockPos blockPos = ((BlockRayTraceResult) target).getBlockPos();

		getTileEntity(world, blockPos).ifPresent(t -> {
			Random random = new Random();
			BlockState blockstate = t.getAppearance();
			if (blockstate.getRenderShape() != BlockRenderType.INVISIBLE) {
				int i = blockPos.getX();
				int j = blockPos.getY();
				int k = blockPos.getZ();
				AxisAlignedBB axisalignedbb = blockstate.getShape(world, blockPos).bounds();
				double d0 = (double) i + random.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - (double) 0.2F) + (double) 0.1F + axisalignedbb.minX;
				double d1 = (double) j + random.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - (double) 0.2F) + (double) 0.1F + axisalignedbb.minY;
				double d2 = (double) k + random.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - (double) 0.2F) + (double) 0.1F + axisalignedbb.minZ;
				BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) target;
				if (blockRayTraceResult.getDirection() == Direction.DOWN) {
					d1 = (double) j + axisalignedbb.minY - (double) 0.1F;
				}

				if (blockRayTraceResult.getDirection() == Direction.UP) {
					d1 = (double) j + axisalignedbb.maxY + (double) 0.1F;
				}

				if (blockRayTraceResult.getDirection() == Direction.NORTH) {
					d2 = (double) k + axisalignedbb.minZ - (double) 0.1F;
				}

				if (blockRayTraceResult.getDirection() == Direction.SOUTH) {
					d2 = (double) k + axisalignedbb.maxZ + (double) 0.1F;
				}

				if (blockRayTraceResult.getDirection() == Direction.WEST) {
					d0 = (double) i + axisalignedbb.minX - (double) 0.1F;
				}

				if (blockRayTraceResult.getDirection() == Direction.EAST) {
					d0 = (double) i + axisalignedbb.maxX + (double) 0.1F;
				}

				Minecraft minecraft = Minecraft.getInstance();
				minecraft.particleEngine
						.add(new DiggingParticle((ClientWorld) world, d0, d1, d2, 0.0D, 0.0D, 0.0D, blockstate).init(blockPos).setPower(0.2F).scale(0.6F));
			}
		});

		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
		getTileEntity(world, pos).ifPresent(t -> {
			VoxelShape voxelshape = state.getShape(world, pos);

			voxelshape.forAllBoxes((p_228348_3_, p_228348_5_, p_228348_7_, p_228348_9_, p_228348_11_, p_228348_13_) -> {
				double d1 = Math.min(1.0D, p_228348_9_ - p_228348_3_);
				double d2 = Math.min(1.0D, p_228348_11_ - p_228348_5_);
				double d3 = Math.min(1.0D, p_228348_13_ - p_228348_7_);
				int i = Math.max(2, MathHelper.ceil(d1 / 0.25D));
				int j = Math.max(2, MathHelper.ceil(d2 / 0.25D));
				int k = Math.max(2, MathHelper.ceil(d3 / 0.25D));

				for (int l = 0; l < i; ++l) {
					for (int i1 = 0; i1 < j; ++i1) {
						for (int j1 = 0; j1 < k; ++j1) {
							double d4 = ((double) l + 0.5D) / (double) i;
							double d5 = ((double) i1 + 0.5D) / (double) j;
							double d6 = ((double) j1 + 0.5D) / (double) k;
							double d7 = d4 * d1 + p_228348_3_;
							double d8 = d5 * d2 + p_228348_5_;
							double d9 = d6 * d3 + p_228348_7_;
							Minecraft minecraft = Minecraft.getInstance();
							minecraft.particleEngine.add((new DiggingParticle((ClientWorld) world, pos.getX() + d7, pos.getY() + d8, pos.getZ() + d9, d4 - 0.5D,
									d5 - 0.5D, d6 - 0.5D, t.getAppearance())).init(pos));
						}
					}
				}
			});
		});
		return true;
	}

	private BlockParticleData getBlockParticles(ShadowTileEntity tileEntity) {
		return new BlockParticleData(ParticleTypes.BLOCK, tileEntity.getAppearance());
	}

	@Override
	public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		BlockState disguise = ShadowBlockBaseItem.loadDisguiseFromStack(itemStack);
		getTileEntity(world, blockPos).ifPresent(t -> t.setDisguise(disguise));
	}

	private Optional<ShadowTileEntity> getTileEntity(World world, BlockPos blockPos) {
		return Optional.ofNullable(world.getBlockEntity(blockPos)).map(t -> (ShadowTileEntity) t);
	}
}
