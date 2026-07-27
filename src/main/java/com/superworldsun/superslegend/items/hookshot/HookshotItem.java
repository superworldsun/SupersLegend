package com.superworldsun.superslegend.items.hookshot;

import com.superworldsun.superslegend.capability.hookshot.HookModel;
import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.items.curios.head.masks.GiantsMask;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.ForgeEventFactory;
import top.theillusivec4.curios.api.CuriosApi;

import static com.superworldsun.superslegend.items.item.SlingShot.getPowerForTime;

public class HookshotItem extends NonEnchantItem {

    public static boolean SPRITE;
    boolean needCharge = false;

    public HookshotItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (CuriosApi.getCuriosHelper().findFirstCurio(player, ItemInit.GNAT_HAT.get()).isPresent() ||
                (CuriosApi.getCuriosHelper().findFirstCurio(player, ItemInit.MASK_GIANTSMASK.get()).isPresent()
                        && GiantsMask.hasTransformationMagic(player)) ||
                CuriosApi.getCuriosHelper().findFirstCurio(player, ItemInit.MASK_DEKUMASK.get()).isPresent() ||
                CuriosApi.getCuriosHelper().findFirstCurio(player, ItemInit.MASK_GORONMASK.get()).isPresent() ||
                CuriosApi.getCuriosHelper().findFirstCurio(player, ItemInit.MASK_ZORAMASK.get()).isPresent() ||
                CuriosApi.getCuriosHelper().findFirstCurio(player, ItemInit.MASK_FIERCEDEITYSMASK.get()).isPresent()) {
            return InteractionResultHolder.fail(itemstack);
        }

        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, world, player, hand, true);
        if (ret != null) return ret;

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity player) {
        return super.finishUsingItem(itemStack, world, player);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level world, LivingEntity player, int remainingUseTicks) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        int i = this.getUseDuration(itemStack) - remainingUseTicks;

        if (!world.isClientSide) {
            if (!HookModel.get((Player) player).getHasHook()) {
                BlockPos currentPos = player.blockPosition();
                world.playSound(null, currentPos, SoundInit.HOOKSHOT_FIRE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                double maxRange = needCharge ? 15D * getPowerForTime(i) : 15D;
                double maxSpeed = 10D;
                HookshotEntity hookshot = new HookshotEntity(EntityTypeInit.HOOKSHOT_ENTITY.get(), player, world);
                hookshot.setProperties(stack, maxRange, maxSpeed, player.getXRot(), player.getYRot(), 0f, 1.5f * (float) (maxSpeed / 10));
                hookshot.setFiredHand(player.getUsedItemHand());
                world.addFreshEntity(hookshot);
                SPRITE = true;
                if (player instanceof Player serverPlayer) {
                    serverPlayer.getInventory().setChanged();
                }
            }

            HookModel.get((Player) player).setHasHook(!HookModel.get((Player) player).getHasHook());
        }
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    public static float getPowerForTime(int p_185059_0_) {
        float f = (float)p_185059_0_ / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack ingredient) {
        return ingredient.is(Items.IRON_INGOT);
    }

    public static void resetSprite(Player player) {
        SPRITE = false;
        player.getInventory().setChanged();
    }
}
