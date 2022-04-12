package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.items.BigQuiver;
import com.superworldsun.superslegend.items.MediumQuiver;
import com.superworldsun.superslegend.items.SmallQuiver;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.ItemNBTHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Predicate;

import static com.superworldsun.superslegend.items.SmallQuiver.TAG_COUNT;

@Mixin(PlayerEntity.class)
public class PlayerGetArrow {

    @Inject(locals = LocalCapture.CAPTURE_FAILSOFT,
            method = "getProjectile",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerInventory;getItem(I)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )

    //@Inject(method = "getProjectile",at = @At(value="HEAD"),cancellable = true)
    private void checkQuiver(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir)
    {
        if (!(shootable.getItem() instanceof ShootableItem)) {
            return;
        }
        Predicate<ItemStack> predicate = ((ShootableItem)shootable.getItem()).getSupportedHeldProjectiles();
        ItemStack itemStack;

        //Check for Quiver
        ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.QUIVER.get(), (PlayerEntity)(Object)this).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack stack1 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.BIG_QUIVER.get(), (PlayerEntity)(Object)this).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack stack2 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.BIGGEST_QUIVER.get(), (PlayerEntity)(Object)this).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        itemStack = Items.ARROW.getDefaultInstance();

        //Check no empty
        if(!stack0.isEmpty()){
            if (predicate.test(itemStack)) {
                //Check client-side
                if(((PlayerEntity)(Object)this).level.isClientSide()){
                    cir.setReturnValue(ItemStack.EMPTY);
                } else {
                    //Get count of arrows.
                    int count = ItemNBTHelper.getInt(stack0, TAG_COUNT, 0);
                    //Check if have at least 1
                    if (count > 0) {
                        //Return arrow.
                        cir.setReturnValue(itemStack);
                        //Reduce 1 arrow on the Quiver.
                        SmallQuiver.setCount(stack0, -1);
                    }
                }
            }
        }

        else if(!stack1.isEmpty()){
            if (predicate.test(itemStack)) {
                if(((PlayerEntity)(Object)this).level.isClientSide()){
                    cir.setReturnValue(ItemStack.EMPTY);
                }else {
                    int count = ItemNBTHelper.getInt(stack1, MediumQuiver.TAG_COUNT, 0);
                    if (count > 0) {
                        cir.setReturnValue(itemStack);
                        MediumQuiver.setCount(stack1, -1);
                    }
                }
            }
        }

        else if(!stack2.isEmpty()){
            if (predicate.test(itemStack)) {
                if(((PlayerEntity)(Object)this).level.isClientSide()){
                    cir.setReturnValue(ItemStack.EMPTY);
                }else {
                    int count = ItemNBTHelper.getInt(stack2, BigQuiver.TAG_COUNT, 0);
                    if (count > 0) {
                        cir.setReturnValue(itemStack);
                        BigQuiver.setCount(stack2, -1);
                    }
                }
            }
        }


    }
}
