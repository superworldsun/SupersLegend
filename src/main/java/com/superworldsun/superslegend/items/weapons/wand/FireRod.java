package com.superworldsun.superslegend.items.weapons.wand;

import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;

public class FireRod extends NonEnchantItem
{
    public FireRod(Properties properties)
    {
        super(new Item.Properties().stacksTo(1));
    }

    //TODO Fire should melt thin snow layers super easily with held right click
    //TODO Change the fire ball explosion sound


    //TODO, need to finish port
    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand Usehand)
    {
        return super.use(level, player, Usehand);
        if (player.isCrouching())
        {
            if (!level.isClientSide)
            {
                // per use
                float manacost = 2F;

                if (MagicProvider.hasMagic(player, manacost))
                {
                    float fireballSpeed = 0.5F;
                    Vec3 playerLookVec = Player.getLookAngle();
                    Vec3 fireballPosition = Player.getEyePosition(1F).add(playerLookVec);
                    Vec3 fireballMotion = playerLookVec.multiply(fireballSpeed, fireballSpeed, fireballSpeed);
                    FireballEntity fireballEntity = new FireballEntity(fireballPosition, fireballMotion, world, Player);
                    level.addFreshEntity(fireballEntity);
                    MagicProvider.spendMagic(player, manacost);
                    player.getCooldowns().addCooldown(this, 16);
                    level.playSound(null, Player.position().x, Player.position().y, Player.position().z, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1F, 1F);
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
            int secondsOnFire = 3;
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
                level.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
            }

            BlockRayTraceResult blockRayTraceResult = level.clip(new RayTraceContext(effectStart, effectEnd, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, null));

            if (blockRayTraceResult.getType() != RayTraceResult.Type.MISS && !level.isClientSide()) {
                // if we hit block, area of effect ends at the hit location
                effectEnd = blockRayTraceResult.getLocation();
                // blocks effect
                // once between 5 - 15 Ticks at random
                if (timeInUse % (5 + random.nextInt(10)) == 0) {
                    BlockPos hitPos = blockRayTraceResult.getBlockPos();

                    if (level.getBlockState(hitPos).is(TagInit.CAN_MELT) || world.getBlockState(hitPos).is(BlockTags.ICE)) {
                        // replaces meltable blocks with air
                        level.setBlock(hitPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                    //TODO i want it so that you can light torch towers but this dosent seem to work
					else if (world.getBlockState(hitPos).getBlock() instanceof TorchTowerTopUnlit) {
						world.setBlock(hitPos, BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 3);
						world.playSound(null, hitPos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1F);
					}
                    else {
                        BlockPos firePos = hitPos.relative(blockRayTraceResult.getDirection());
                        // sets other blocks on fire
                        if (FireBlock.canBePlacedAt(level, firePos, blockRayTraceResult.getDirection())) {
                            BlockState fireBlockState = FireBlock.getState(level, firePos);
                            level.setBlock(firePos, fireBlockState, 11);
                        }
                    }
                }
            }

            // we want to only attack living entities
            Predicate<Entity> canHit = e -> e instanceof LivingEntity;
            EntityRayTraceResult entityRayTraceResult = ProjectileHelper.getEntityHitResult(level, player, effectStart, effectEnd, new AxisAlignedBB(effectStart, effectEnd).inflate(1.0D), canHit);

            // if we hit entity
            if (entityRayTraceResult != null)
            {
                DamageSource damageSource = DamageSource.playerAttack(player).setIsFire();
                entityRayTraceResult.getEntity().hurt(damageSource, damage);
                entityRayTraceResult.getEntity().setSecondsOnFire(secondsOnFire);
            }

            MagicProvider.spendMagic(player, manacost);

            // plays sound 4 times per second
            if (timeInUse % 5 == 0)
            {
                level.playSound(null, player.position().x, player.position().y, player.position().z, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1F, 1F);
            }
        }
    }*/
}
