package com.superworldsun.superslegend.items.weapons;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class DekuStick extends Item
{
	public DekuStick(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "A stick"));
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		if(!player.isCreative())
		{
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
			stack.shrink(1);
		}
		if(player.isCreative())
		{
			entity.hurt(DamageSource.GENERIC, 8.0F);
		}

		return true;
	}

	/*public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		RayTraceResult raytraceresult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.NONE);
		if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && world.getBlockState(((BlockRayTraceResult)raytraceresult).getBlockPos()).is(Blocks.FIRE))
		{
			//player.startUsingItem(hand);
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.MAGIC_ARROW_HIT_FIRE.get(), SoundCategory.PLAYERS, 1f, 1f);
			stack.shrink(1);
			player.addItem(new ItemStack(ItemInit.DEKU_STICK_LIT.get()));
		}
		return ActionResult.pass(stack);
	}*/

	public ActionResultType useOn(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		ItemStack stack = context.getItemInHand();
		RayTraceResult raytraceresult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.NONE);
		if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && world.getBlockState(((BlockRayTraceResult)raytraceresult).getBlockPos()).is(Blocks.FIRE))
		{
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.FIRE_IGNITE.get(), SoundCategory.PLAYERS, 1f, 1f);
			stack.shrink(1);
			player.addItem(new ItemStack(ItemInit.DEKU_STICK_LIT.get()));

			return ActionResultType.sidedSuccess(world.isClientSide());
		}
		if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && world.getBlockState(((BlockRayTraceResult)raytraceresult).getBlockPos()).is(BlockInit.TORCH_TOWER_TOP_LIT.get()))
		{
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.FIRE_IGNITE.get(), SoundCategory.PLAYERS, 1f, 1f);
			stack.shrink(1);
			player.addItem(new ItemStack(ItemInit.DEKU_STICK_LIT.get()));

			return ActionResultType.sidedSuccess(world.isClientSide());
		}
		else
		{
				return ActionResultType.FAIL;
		}
	}

	//stack.shrink(1);
	//
	//BlockPos currentPos = player.blockPosition();
	//world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.SHIELD_BREAK, SoundCategory.PLAYERS, 1f, 1f);
	//
	//
	//

}