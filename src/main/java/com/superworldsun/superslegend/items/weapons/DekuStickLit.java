package com.superworldsun.superslegend.items.weapons;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class DekuStickLit extends Item
{
	public DekuStickLit(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "A stick"));
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		if(!player.isCreative() && !player.level.isClientSide)
		{
			stack.shrink(1);
			entity.setSecondsOnFire(6);
			entity.hurt(DamageSource.GENERIC, 8.0F);
			player.playSound(SoundEvents.ITEM_BREAK, 1F, 1F);

			Random rand = player.level.random;
			for (int i = 0; i < 10; i++)
			{
				player.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(ItemInit.DEKU_STICK.get())),
						player.xo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 0.2f,
						player.yo + rand.nextFloat() * 1 - -1,
						player.zo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 0.2f,
						0, 0.105D, 0);
			}

			entity.isOnFire();
		}
		if(player.isCreative() && !player.level.isClientSide)
		{
			entity.setSecondsOnFire(6);
			entity.hurt(DamageSource.GENERIC, 8.0F);
		}

		return true;
	}

	public ActionResultType useOn(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		World worldIn = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		ItemStack stack = context.getItemInHand();
		Random rand = context.getLevel().getRandom();
		RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, player, RayTraceContext.FluidMode.NONE);
		if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && worldIn.getBlockState(((BlockRayTraceResult)raytraceresult).getBlockPos()).is(Blocks.COBWEB)  && !player.level.isClientSide)
		{
			BlockPos currentPos = player.blockPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.FIRE_IGNITE.get(), SoundCategory.PLAYERS, 1f, 1f);
			worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());

			double xPos = (double) blockpos.getX() + 0.5;
			double yPos = (double) blockpos.getY() + 0.3;
			double zPos = (double) blockpos.getZ() + 0.5;
			double xOffset = MathHelper.nextDouble(rand, -0.2, 0.2);
			double zOffset = MathHelper.nextDouble(rand, -0.2, 0.2);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.0, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.1, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.15, 0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.05, 0.15, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.05, 0.15, 0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, -0.05, 0.15, -0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.05, 0.15, -0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, -0.05, 0.15, 0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.1, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.0, 0.1, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.1, 0.0, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.0, -0.1, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, -0.1, 0.0, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.1, 0.1, 0.1, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.1, 0.1, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.1, 0.1, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, -0.1, 0.1, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.1, -0.1, 0.1);

			return ActionResultType.sidedSuccess(worldIn.isClientSide());
		}
		if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && worldIn.getBlockState(((BlockRayTraceResult)raytraceresult).getBlockPos()).is(BlockInit.TORCH_TOWER_TOP_UNLIT.get())  && !player.level.isClientSide)
		{
			BlockPos currentPos = player.blockPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.FIRE_IGNITE.get(), SoundCategory.PLAYERS, 1f, 1f);
			worldIn.setBlockAndUpdate(blockpos, BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState());

			double xPos = (double) blockpos.getX() + 0.6;
			double yPos = (double) blockpos.getY() + 0.3;
			double zPos = (double) blockpos.getZ() + 0.6;
			double xOffset = MathHelper.nextDouble(rand, -0.02, 0.02);
			double zOffset = MathHelper.nextDouble(rand, -0.02, 0.02);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.0, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.1, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.15, 0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.05, 0.15, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.05, 0.15, 0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, -0.05, 0.15, -0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.05, 0.15, -0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, -0.05, 0.15, 0.05, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.1, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.0, 0.1, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.1, 0.0, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.0, -0.1, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, -0.1, 0.0, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.1, 0.1, 0.1, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.1, 0.1, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.1, 0.1, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, -0.1, 0.1, 0.0, 0.1);
			((ServerWorld) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 1, 0.0, 0.1, -0.1, 0.1);

			return ActionResultType.sidedSuccess(worldIn.isClientSide());
		}
		else
		{
			return ActionResultType.FAIL;
		}
	}
}