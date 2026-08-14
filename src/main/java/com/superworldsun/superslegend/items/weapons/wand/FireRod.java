package com.superworldsun.superslegend.items.weapons.wand;

import com.superworldsun.superslegend.util.IceExtinguishUtil;

import com.superworldsun.superslegend.blocks.TorchTowerTopUnlit;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.entities.projectiles.magic.FireballEntity;
import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class FireRod extends NonEnchantItem {
    public static final float PARTICLES_SPEED = 1F;
    public static final float PARTICLES_SPREAD = 0.3F;
    public static final int PARTICLES_DENSITY = 2;
    public static final float EFFECT_RANGE = 6F;
    public static final float DAMAGE = 1F;
    public static final int IGNITE_DURATION = 3;
    public static final float FIREBALL_MANACOST = 2F;
    public static final float FIREBALL_SPEED = 0.5F;
    public static final int FIREBALL_COOLDOWN = 16;
    public static final float FIRETHROWER_MANACOST = 0.025F;

    public FireRod(Properties properties) {
        super(properties);
    }

    //TODO Change the fire ball explosion sound

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        if (player.isCrouching()) {
            if (!level.isClientSide) {
                if (MagicProvider.hasMagic(player, FIREBALL_MANACOST)) {
                    MagicProvider.spendMagic(player, FIREBALL_MANACOST);
                    castFireball(level, player);
                }
            }
        } else {
            player.startUsingItem(hand);
            // Check if the player is targeting a snow layer block
            BlockHitResult hitResult = getPlayerBlockHitResult(level, player);
            if (hitResult.getType() != HitResult.Type.MISS) {
                BlockPos blockPos = hitResult.getBlockPos();
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(Blocks.SNOW)) {
                    level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                    return InteractionResultHolder.success(player.getItemInHand(hand));
                }
            }
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    private static BlockHitResult getPlayerBlockHitResult(Level level, Player player) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 lookVector = player.getLookAngle();
        Vec3 targetPosition = eyePosition.add(lookVector.scale(5.0)); // Adjust scale as needed for reach
        return level.clip(new ClipContext(eyePosition, targetPosition, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }

    private void castFireball(@NotNull Level level, Player player) {
        Vec3 playerLook = player.getLookAngle();
        Vec3 position = player.getEyePosition(1F).add(playerLook);
        Vec3 motion = playerLook.multiply(FIREBALL_SPEED, FIREBALL_SPEED, FIREBALL_SPEED);
        FireballEntity fireball = new FireballEntity(position, motion, level, player);
        level.addFreshEntity(fireball);
        if (!player.isCreative())
            player.getCooldowns().addCooldown(this, FIREBALL_COOLDOWN);
        level.playSound(null, player, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1f, 1f);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 72000;
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int timeInUse) {
        if (!(entity instanceof Player player)) return;
        if (MagicProvider.hasMagic(player, FIRETHROWER_MANACOST)) {
            MagicProvider.spendMagic(player, FIRETHROWER_MANACOST);
            castFirethrower(level, player, timeInUse);
        }
    }

    private static void castFirethrower(@NotNull Level level, Player player, int timeInUse) {
        Vec3 playerLook = player.getLookAngle();
        Vec3 effectStart = player.getEyePosition(1F).add(playerLook);
        Vec3 effectEnd = effectStart.add(playerLook.multiply(EFFECT_RANGE, EFFECT_RANGE, EFFECT_RANGE));

        addFirethrowerParticles(level, player.getRandom(), effectStart, playerLook);

        BlockHitResult blockHit = getBlockHit(level, effectStart, effectEnd);

        if (blockHit.getType() != EntityHitResult.Type.MISS && !level.isClientSide()) {
            // if we hit block, area of effect ends at the hit location
            effectEnd = blockHit.getLocation();
            onBlockHit(level, player, blockHit, timeInUse);
        }

        EntityHitResult entityHit = getEntityHit(level, player, effectStart, effectEnd);

        // if we hit entity
        if (entityHit != null) {
            onEntityHit(player, entityHit);
        }

        // plays sound 4 times per second
        if (timeInUse % 5 == 0) {
            level.playSound(null, player.position().x, player.position().y, player.position().z, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1F, 1F);
        }
    }

    private static void onBlockHit(@NotNull Level level, Player player, BlockHitResult blockHit, int timeInUse) {
        // once between 5 - 15 Ticks at random
        if (timeInUse % (5 + player.getRandom().nextInt(11)) != 0) return;
        BlockPos hitPos = blockHit.getBlockPos();

        if (level.getBlockState(hitPos).is(TagInit.CAN_MELT) || level.getBlockState(hitPos).is(BlockTags.ICE)) {
            // replaces meltable blocks with air
            level.setBlock(hitPos, Blocks.AIR.defaultBlockState(), 3);
            return;
        }
        if (level.getBlockState(hitPos).getBlock() instanceof TorchTowerTopUnlit) {
            level.setBlock(hitPos, BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 3);
            level.playSound(null, hitPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1F, 1F);
            return;
        }
        BlockPos firePos = hitPos.relative(blockHit.getDirection());
        // sets other blocks on fire
        if (FireBlock.canBePlacedAt(level, firePos, blockHit.getDirection())) {
            BlockState fireBlockState = FireBlock.getState(level, firePos);
            level.setBlock(firePos, fireBlockState, 11);
        }
    }

    private static void onEntityHit(Player player, EntityHitResult entityHit) {
        DamageSource damageSource = player.damageSources().playerAttack(player);
        entityHit.getEntity().hurt(damageSource, DAMAGE);
        entityHit.getEntity().setSecondsOnFire(IGNITE_DURATION);
        IceExtinguishUtil.markFireRodIgnition(entityHit.getEntity(), player);
    }

    @NotNull
    private static BlockHitResult getBlockHit(@NotNull Level level, Vec3 start, Vec3 end) {
        return level.clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null));
    }

    @Nullable
    private static EntityHitResult getEntityHit(@NotNull Level level, Player player, Vec3 effectStart, Vec3 effectEnd) {
        // we want to only attack living entities
        Predicate<Entity> canHit = LivingEntity.class::isInstance;
        AABB aoe = new AABB(effectStart, effectEnd).inflate(1f);
        return ProjectileUtil.getEntityHitResult(level, player, effectStart, effectEnd, aoe, canHit);
    }

    private static void addFirethrowerParticles(@NotNull Level level, RandomSource random, Vec3 position, Vec3 movement) {
        Vec3 particlesMotionVec = movement.multiply(PARTICLES_SPEED, PARTICLES_SPEED, PARTICLES_SPEED);

        for (int i = 0; i < PARTICLES_DENSITY; i++) {
            double x = position.x + (random.nextFloat() * 2 - 1) * PARTICLES_SPREAD;
            double y = position.y + (random.nextFloat() * 2 - 1) * PARTICLES_SPREAD;
            double z = position.z + (random.nextFloat() * 2 - 1) * PARTICLES_SPREAD;
            double motionX = particlesMotionVec.x + (random.nextFloat() * 2 - 1) * PARTICLES_SPREAD / 5f;
            double motionY = particlesMotionVec.y + (random.nextFloat() * 2 - 1) * PARTICLES_SPREAD / 5f;
            double motionZ = particlesMotionVec.z + (random.nextFloat() * 2 - 1) * PARTICLES_SPREAD / 5f;
            level.addParticle(ParticleTypes.FLAME, x, y, z, motionX, motionY, motionZ);
        }
    }
}
