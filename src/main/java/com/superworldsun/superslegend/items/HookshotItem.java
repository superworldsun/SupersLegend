package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

public class HookshotItem extends NonEnchantItem {

    /**
     * sprite It is used to change the sprite of the item when used.
     * needCharge makes the hookshot work with load. If you disable it, it will continue to load but the animation will remain. This can be changed below.
     */
    public static boolean sprite;
    boolean needCharge = false;

    public HookshotItem(Properties properties) {
        super(properties);
    }
    //TODO 1 Sometimes the model will randomly swap to the fired model and have no hook in the model when it should
    //TODO 2 the Chain part looks really dark near the hook instead of the same color as the rest of the chain
    //TODO 3 the chain flicks up and down when you hook a block and are going towards it.
    //TODO 4 the chain looks like it comes from your stomach and could look more like its coming out of the item in hand
    //TODO 5 the off hand dosent look the same as main hand, needs to be redone
    //Let you shoot.
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GNAT_HAT.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GIANTSMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack1 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_DEKUMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack2 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack3 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_ZORAMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack maskStack4 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_FIERCEDEITYSMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if(!maskStack.isEmpty() || !maskStack0.isEmpty() || !maskStack1.isEmpty() ||
                !maskStack2.isEmpty() || !maskStack3.isEmpty() || !maskStack4.isEmpty())
            return ActionResult.fail(itemstack);

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

        BlockPos currentPos = player.blockPosition();
        world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.HOOKSHOT_FIRE.get(), SoundCategory.PLAYERS, 1f, 1f);

        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        //Get Charge
        int i = this.getUseDuration(itemStack) - remainingUseTicks;

        if (!world.isClientSide) {
            if (!HookModel.get((PlayerEntity) player).getHasHook()) {
                double maxRange = 15D;
                if(needCharge) {
                    maxRange = 15D * getPowerForTime(i);
                }
                double maxSpeed = 10D;
                //Get Entity, set properties and spawn in the world.
                HookshotEntity hookshot = new HookshotEntity(EntityTypeInit.HOOKSHOT_ENTITY.get(), player, world);
                hookshot.setProperties(stack, maxRange, maxSpeed, player.xRot, player.yRot, 0f, 1.5f * (float) (maxSpeed / 10));
                 world.addFreshEntity(hookshot);
                sprite = true;
            }

            HookModel.get((PlayerEntity) player).setHasHook(!HookModel.get((PlayerEntity) player).getHasHook());

        }

        if (!HookModel.get((PlayerEntity) player).getHasHook()) { //Sound at launch.
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
}