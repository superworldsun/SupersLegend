package com.superworldsun.superslegend.items.weapons.wand;

import com.superworldsun.superslegend.blocks.TorchTowerTopLit;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.entities.projectiles.magic.IceballEntity;
import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class IceRod extends NonEnchantItem
{
    public IceRod(Properties properties)
    {
        super(properties);
    }

    //TODO Ice should extinguish fire easily with held right click

    //TODO Change the Ice ball explosion sound

    //TODO, need to finish port
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand Usehand)
    {
        if (player.isCrouching())
        {
            if (!level.isClientSide)
            {
                // per use
                float manacost = 2F;

                if (MagicProvider.hasMagic(player, manacost))
                {
                    float iceballSpeed = 0.5F;
                    Vec3 playerLookVec = player.getLookAngle();
                    Vec3 iceballPosition = player.getEyePosition(1F).add(playerLookVec);
                    Vec3 iceballMotion = playerLookVec.multiply(iceballSpeed, iceballSpeed, iceballSpeed);
                    IceballEntity iceballEntity = new IceballEntity(iceballPosition, iceballMotion, level, player);
                    level.addFreshEntity(iceballEntity);
                    MagicProvider.spendMagic(player, manacost);
                    player.getCooldowns().addCooldown(this, 16);
                    level.playSound(null, player, SoundEvents.SNOW_BREAK, SoundSource.PLAYERS, 1f, 1f);
                }
            }
        }
        else
        {
            player.startUsingItem(Usehand);
        }

        return InteractionResultHolder.consume(player.getItemInHand(Usehand));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack)
    {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemStack)
    {
        return 72000;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int timeInUse) {
        if (livingEntity instanceof Player) {
            // per tick
            float manacost = 0.025F;
            Player player = (Player) livingEntity;

            if (!MagicProvider.hasMagic(player, manacost)) {
                // no effect in not enough mana and not in creative mod
                return;
            }

            int particlesDensity = 2;
            float particlesSpread = 0.3F;
            float particlesSpeed = 1F;
            float effectRange = 6F;
            float damage = 1F;

            Vec3 playerLookVec = player.getLookAngle();
            Vec3 effectStart = player.getEyePosition(1F).add(playerLookVec);
            Vec3 effectEnd = effectStart.add(playerLookVec.multiply(effectRange, effectRange, effectRange));
            Vec3 particlesMotionVec = playerLookVec.multiply(particlesSpeed, particlesSpeed, particlesSpeed);

            for (int i = 0; i < particlesDensity; i++) {
                double particleX = effectStart.x + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread;
                double particleY = effectStart.y + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread;
                double particleZ = effectStart.z + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread;
                double particleMotionX = particlesMotionVec.x + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread / 5F;
                double particleMotionY = particlesMotionVec.y + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread / 5F;
                double particleMotionZ = particlesMotionVec.z + (player.getRandom().nextFloat() * 2 - 1) * particlesSpread / 5F;
                level.addParticle(ParticleTypes.SPIT, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
            }

            BlockHitResult blockRayTraceResult = level.clip(new ClipContext(effectStart, effectEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));

            if (blockRayTraceResult.getType() != EntityHitResult.Type.MISS && !level.isClientSide())
            {
                // if we hit block, area of effect ends at the hit location
                effectEnd = blockRayTraceResult.getLocation();
                // blocks effect
                // once between 5 - 15 Ticks at random
                if (timeInUse % (5 + player.getRandom().nextInt(10)) == 0)
                {
                    BlockPos hitPos = blockRayTraceResult.getBlockPos();

                    if (level.getFluidState(hitPos).is(FluidTags.WATER))
                    {
                        level.setBlock(hitPos, Blocks.ICE.defaultBlockState(), 3);
                    }
                    else if (level.getFluidState(hitPos).is(FluidTags.LAVA))
                    {
                        level.setBlock(hitPos, Blocks.OBSIDIAN.defaultBlockState(), 3);
                        level.playSound(null, hitPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1F, 1F);
                    }
                    //This dosent seem to extinguish the Torch tower
                    else if (level.getBlockState(hitPos).getBlock() instanceof TorchTowerTopLit)
                    {
                        level.setBlock(hitPos, BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState(), 3);
                        level.playSound(null, hitPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1F, 1F);
                    }
                    else {
                        BlockPos snowPos = hitPos.relative(blockRayTraceResult.getDirection());
                        BlockState snowBlockState = Blocks.SNOW.defaultBlockState();
                        //TODO fix .getmaterial, finish port
                        /*if (snowBlockState.canSurvive(level, snowPos) && level.getBlockState(snowPos).getMaterial().isReplaceable()) {
                            level.setBlock(snowPos, snowBlockState, 11);
                        }*/
                    }
                }
            }

            // we want to only attack living entities
            Predicate<Entity> canHit = e -> e instanceof LivingEntity;
            EntityHitResult entityRayTraceResult = ProjectileUtil.getEntityHitResult(level, player, effectStart, effectEnd, new AABB(effectStart, effectEnd).inflate(1.0D), canHit);

            // if we hit entity
            if (entityRayTraceResult != null)
            {
                //TODO Causes crashes / cant port
                /*DamageSource damageSource = DamageSource.playerAttack(player).setIsFire();
                entityRayTraceResult.getEntity().hurt(damageSource, damage);
                entityRayTraceResult.getEntity().setSecondsOnFire(secondsOnFire);*/
            }

            MagicProvider.spendMagic(player, manacost);

            // plays sound 4 times per second
            if (timeInUse % 5 == 0)
            {
                level.playSound(null, player.position().x, player.position().y, player.position().z, SoundEvents.SNOW_BREAK, SoundSource.PLAYERS, 1F, 1F);
            }
        }
    }
}
