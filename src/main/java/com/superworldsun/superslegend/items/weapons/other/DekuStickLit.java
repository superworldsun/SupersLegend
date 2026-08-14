package com.superworldsun.superslegend.items.weapons.other;

import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class DekuStickLit extends NonEnchantItem {
	public DekuStickLit() {
		super(new Properties().stacksTo(1).durability(400));
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged || oldStack.getItem() != newStack.getItem();
	}

	// TODO: Implement custom break sound
	private static final SoundEvent BREAK_SOUND = SoundEvents.ITEM_BREAK; // Placeholder

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		tooltip.add(Component.literal("A stick with fire on the end").withStyle(ChatFormatting.RED));
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		if (!player.level().isClientSide()) {
			entity.setSecondsOnFire(6);
			entity.hurt(player.level().damageSources().generic(), 8.0F);
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
				playBreakEffects(player);
			}
		}
		return true;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (!level.isClientSide() && stack.getDamageValue() < stack.getMaxDamage()) {
			stack.setDamageValue(stack.getDamageValue() + 1);
			if (stack.getDamageValue() == stack.getMaxDamage()) {
				if (entity instanceof Player) {
					playBreakEffects((Player) entity);
				}
				stack.shrink(1);
			}
		}
		// TODO: Implement breaking when on ground, in chest, or not in hand
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();

		if (level.isClientSide() || player == null) return InteractionResult.SUCCESS;

		Block targetBlock = level.getBlockState(pos).getBlock();
		if (targetBlock == Blocks.COBWEB) {
			burnCobweb(level, pos, player);
			return InteractionResult.SUCCESS;
		} else if (targetBlock == BlockInit.TORCH_TOWER_TOP_UNLIT.get()) {
			lightTorchTower(level, pos, player);
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	private void burnCobweb(Level level, BlockPos pos, Player player) {
		level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		if (player instanceof ServerPlayer serverPlayer) {
			ModAdvancementHelper.award(serverPlayer, "web_burner", "burned_cobweb");
		}
		playIgniteSound(level, player.blockPosition());
		spawnFlameParticles((ServerLevel) level, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5);
		// TODO: Implement fire spread to nearby cobwebs
	}

	private void lightTorchTower(Level level, BlockPos pos, Player player) {
		level.setBlockAndUpdate(pos, BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState());
		playIgniteSound(level, player.blockPosition());
		spawnFlameParticles((ServerLevel) level, pos.getX() + 0.6, pos.getY() + 0.3, pos.getZ() + 0.6);
	}

	private void playBreakEffects(Player player) {
		player.playSound(BREAK_SOUND, 1F, 1F);
		spawnBreakParticles(player);
	}

	private void playIgniteSound(Level level, BlockPos pos) {
		level.playSound(null, pos, SoundInit.FIRE_IGNITE.get(), SoundSource.PLAYERS, 1f, 1f);
	}

	private void spawnBreakParticles(Player player) {
		RandomSource rand = player.level().getRandom();
		for (int i = 0; i < 10; i++) {
			player.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ItemInit.DEKU_STICK.get())),
					player.getX() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 0.2f,
					player.getY() + rand.nextFloat() * 1 - -1,
					player.getZ() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 0.2f,
					0, 0.105D, 0);
		}
	}

	private void spawnFlameParticles(ServerLevel level, double x, double y, double z) {
		RandomSource rand = level.getRandom();
		for (int i = 0; i < 18; i++) {
			double xOffset = Mth.nextDouble(rand, -0.2, 0.2);
			double zOffset = Mth.nextDouble(rand, -0.2, 0.2);
			level.sendParticles(ParticleTypes.FLAME, x + xOffset, y, z + zOffset, 1,
					Mth.nextDouble(rand, -0.1, 0.1),
					Mth.nextDouble(rand, 0, 0.15),
					Mth.nextDouble(rand, -0.1, 0.1), 0.1);
		}
	}
}

