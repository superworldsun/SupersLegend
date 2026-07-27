package com.superworldsun.superslegend.items.hookshot;

import com.superworldsun.superslegend.capability.hookshot.HookModel;
import com.superworldsun.superslegend.entities.projectiles.hooks.LongshotEntity;
import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.items.curios.head.masks.GiantsMask;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

public class LongshotItem extends NonEnchantItem {

    /**
     * sprite It is used to change the sprite of the item when used.
     * needCharge makes the hookshot work with load. If you disable it, it will continue to load but the animation will remain. This can be changed below.
     */
    public static boolean LONG_SPRITE;
    boolean needCharge = false;

    public LongshotItem(Properties properties) {
        super(properties);
    }

    //Let you shoot.
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GNAT_HAT.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GIANTSMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack1 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_DEKUMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack2 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack3 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_ZORAMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack4 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_FIERCEDEITYSMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if(!maskStack.isEmpty() || (!maskStack0.isEmpty() && GiantsMask.hasTransformationMagic(player)) || !maskStack1.isEmpty() ||
                !maskStack2.isEmpty() || !maskStack3.isEmpty() || !maskStack4.isEmpty())
            return InteractionResultHolder.fail(itemstack);

        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, world, player, hand, true);
        if (ret != null) return ret;

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity player) {
        return super.finishUsingItem(itemStack, world, player);
    }

    //Item animation.
    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BOW;
    }

    //Function that manages what happens when you launch the hook.
    @Override
    public void releaseUsing(ItemStack itemStack, Level world, LivingEntity player, int remainingUseTicks) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        //Get Charge
        int i = this.getUseDuration(itemStack) - remainingUseTicks;

        if (!world.isClientSide) {
            if (!HookModel.get((Player) player).getHasHook()) {
                BlockPos currentPos = player.blockPosition();
                world.playSound(null, currentPos, SoundInit.HOOKSHOT_FIRE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                double maxRange = 30D;
                if(needCharge) {
                    maxRange = 30D * getPowerForTime(i);
                }
                double maxSpeed = 10D;
                //Get Entity, set properties and spawn in the world.
                LongshotEntity hookshot = new LongshotEntity(EntityTypeInit.LONGSHOT_ENTITY.get(), player, world);
                hookshot.setProperties(stack, maxRange, maxSpeed, player.getXRot(), player.getYRot(), 0f, 1.5f * (float) (maxSpeed / 10));
                hookshot.setFiredHand(player.getUsedItemHand());
                world.addFreshEntity(hookshot);
                LONG_SPRITE = true;
                if (player instanceof Player serverPlayer) {
                    serverPlayer.getInventory().setChanged();
                }
            }

            HookModel.get((Player) player).setHasHook(!HookModel.get((Player) player).getHasHook());

        }

        if (!HookModel.get((Player) player).getHasHook()) { //Sound at launch.
            //world.playSound((PlayerEntity) player, player.blockPosition(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1F, 1F);
        }
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

    public static void resetSprite(Player player) {
        LONG_SPRITE = false;
        player.getInventory().setChanged();
    }
}
