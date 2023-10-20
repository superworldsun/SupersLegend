package com.superworldsun.superslegend.items.item;

import net.minecraft.world.item.BowItem;

public class SlingShot extends BowItem {
    public SlingShot(Properties pProperties) {
        super(pProperties);
    }


    /*@Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        //return stack -> stack.getItem().is(TagInit.PELLETS);
        return null;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player) {
            Player player = (Player) entityLiving;
            boolean infiniteAmmo = player.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack ammoStack = player.getProjectile(stack);
            int i = getUseDuration(stack) - timeLeft;
            i = ForgeEventFactory.onArrowLoose(stack, level, player, i, !ammoStack.isEmpty() || infiniteAmmo);

            if (ammoStack.getItem() == Items.ARROW) {
                ammoStack = new ItemStack(ItemInit.DEKU_SEEDS.get());
            }

            if (i < 0) {
                return;
            }

            if (!ammoStack.isEmpty() || infiniteAmmo) {
                float shotPower = getPowerForTime(i) * 0.5f;

                if (shotPower >= 0.1D) {
                    if (!level.isClientSide) {
                        SeedEntity projectile = createAmmoEntity(level, ammoStack);
                        projectile.setOwner(player);
                        projectile.setPos(player.getEyePosition(1F).add(0, -0.1, 0));
                        projectile.shoot(player.getLookAngle(), shotPower * 3F, 0F);
                        level.addFreshEntity(projectile);
                    }

                    level.playSound(null, player, SoundInit.SLINGSHOT_SHOOT.get(), SoundSource.PLAYERS, 1.0F,
                            1.0F / (random.nextFloat() * 0.4F + 1.2F) + shotPower * 0.5F);

                    if (!infiniteAmmo && !player.abilities.instabuild) {
                        ammoStack.shrink(1);

                        if (ammoStack.isEmpty()) {
                            player.getInventory().removeItem(ammoStack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Nonnull
    private SeedEntity createAmmoEntity(Level world, ItemStack ammoStack) {
        Item ammoItem = ammoStack.getItem();

        if (ammoItem == Items.BEETROOT_SEEDS) {
            return new BeetrootSeedEntity(world);
        } else if (ammoItem == Items.WHEAT_SEEDS) {
            return new WheatSeedEntity(world);
        } else if (ammoItem == Items.MELON_SEEDS) {
            return new MelonSeedEntity(world);
        } else if (ammoItem == Items.PUMPKIN_SEEDS) {
            return new PumpkinSeedEntity(world);
        } else if (ammoItem == Items.COCOA_BEANS) {
            return new CocoaBeanEntity(world);
        }

        return new DekuSeedEntity(world);
    }

    public static float getPowerForTime(int timeInUse) {
        float power = (float) timeInUse / 10.0F;
        power = (power * power + power * 2.0F) / 3.0F;

        if (power > 1.0F) {
            power = 1.0F;
        }

        return power;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @SubscribeEvent
    public static void onLivingEntityUseItem(LivingEntityUseItemEvent event) {
        if (event.getItem().getItem() instanceof SlingShot) {
            if (event.getEntityLiving().isUsingItem()) {
                if (event.getDuration() == 72000) {
                    event.getEntityLiving().level.playSound(null, event.getEntityLiving(), SoundInit.SLINGSHOT_PULL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                }

                if (event.getDuration() > 71980) {
                    event.setDuration(event.getDuration() - 1);
                }
            }
        }
    }*/
}