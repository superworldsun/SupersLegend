package com.superworldsun.superslegend.items.weapons.bow;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class LynelBowX3 extends BowItem {
    protected final Random random = new Random();
    public float velocity = 2.95f;
    public float inaccuracy = 1f;
    public int shotType;

    //TODO, this is what it was in 1.16.5 for the constructor. Currently the bow isnt firing any arrows
    /*public LynelBowX3(int shotType, Properties builder) {
        super(builder);

        this.shotType = shotType;
    }*/

    //This is just here to stop an error for now
    public LynelBowX3(Properties builder) {
        super(builder);

        this.shotType = shotType;
    }

    public void releaseUsing(@Nonnull ItemStack stack, @Nonnull Level levelIn, @Nonnull LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player) {
            Player playerentity = (Player)entityLiving;
            ItemStack itemstack = playerentity.getProjectile(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, levelIn, playerentity, i, !itemstack.isEmpty());
            if (i < 0) return;

            if (itemstack.isEmpty()) {
                itemstack = new ItemStack(Items.ARROW);
            }

            float f = getPowerForTime(i);
            if (!((double)f < 0.1D)) {
                boolean flag1 = playerentity.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));

                if(shotType == 1){
                    if (!levelIn.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrowentity = arrowitem.createArrow(levelIn, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.shootFromRotation(playerentity, playerentity.xRotO, playerentity.yRotO, 0.0F, f * velocity, inaccuracy);

                        if (f == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));

                        if (flag1 || playerentity.getAbilities().instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        levelIn.addFreshEntity(abstractarrowentity);
                    }

                    if (!levelIn.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrowentity = arrowitem.createArrow(levelIn, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.shootFromRotation(playerentity, playerentity.xRotO, playerentity.yRotO + 7, 0.0F, f * velocity, inaccuracy);

                        if (f == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));

                        abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

                        levelIn.addFreshEntity(abstractarrowentity);
                    }

                    if (!levelIn.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrowentity = arrowitem.createArrow(levelIn, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.shootFromRotation(playerentity, playerentity.xRotO, playerentity.yRotO -7, 0.0F, f * velocity, inaccuracy);
                        if (f == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));

                        abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

                        levelIn.addFreshEntity(abstractarrowentity);
                    }
                }

                levelIn.playSound(null, playerentity.xo, playerentity.yo, playerentity.zo,
                        SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (!flag1 && !playerentity.getAbilities().instabuild) { itemstack.shrink(1);
                    if (itemstack.isEmpty()) { playerentity.getInventory().removeItem(itemstack); }
                }

                playerentity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @ParametersAreNonnullByDefault
    public void onCraftedBy(ItemStack itemStack, Level world, Player player) {
        super.onCraftedBy(itemStack, world, player);
        shotType = 1;
    }
}