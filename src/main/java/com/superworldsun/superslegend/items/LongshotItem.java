package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.entities.projectiles.hooks.LongshotEntity;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class LongshotItem extends Item {

    /**
     * sprite It is used to change the sprite of the item when used.
     * needCharge makes the hookshot work with load. If you disable it, it will continue to load but the animation will remain. This can be changed below.
     */
    public static boolean spriteL;
    boolean needCharge = true;

    public LongshotItem(Properties properties) {
        super(properties);
    }

    //Let you shoot.
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, world, player, hand, true);
        if (ret != null) return ret;

            player.startUsingItem(hand);
            return ActionResult.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, World world, LivingEntity player) {
        return super.finishUsingItem(itemStack, world, player);
    }

    //Item animation.
    @Override
    public UseAction getUseAnimation(ItemStack itemStack) {
        return UseAction.BOW;
    }

    //Function that manages what happens when you launch the hook.
    @Override
    public void releaseUsing(ItemStack itemStack, World world, LivingEntity player, int remainingUseTicks) {

        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        //Get Charge
        int i = this.getUseDuration(itemStack) - remainingUseTicks;

        if (!world.isClientSide) {
            if (!HookModel.get((PlayerEntity) player).getHasHook()) {
                double maxRange = 30D;
                if(needCharge) {
                    maxRange = 30D * getPowerForTime(i);
                }
                double maxSpeed = 10D;
                //Get Entity, set properties and spawn in the world.
                LongshotEntity hookshot = new LongshotEntity(EntityTypeInit.LONGSHOT_ENTITY.get(), player, world);
                hookshot.setProperties(stack, maxRange, maxSpeed, player.xRot, player.yRot, 0f, 1.5f * (float) (maxSpeed / 10));
                world.addFreshEntity(hookshot);
                spriteL = true;
            }

                HookModel.get((PlayerEntity) player).setHasHook(!HookModel.get((PlayerEntity) player).getHasHook());

        }

        if (!HookModel.get((PlayerEntity) player).getHasHook())
            world.playSound((PlayerEntity) player, player.blockPosition(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1F, 1F);

    }

    //Time to charge the hookshot.
    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    //Get charge.
    public static float getPowerForTime(int p_185059_0_) {
        float f = (float)p_185059_0_ / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    //Set Repair Item.
    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack ingredient) {
        return ingredient.getItem() == Items.IRON_INGOT;
    }

}