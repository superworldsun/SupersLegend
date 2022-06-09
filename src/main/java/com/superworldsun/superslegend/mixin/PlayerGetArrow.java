package com.superworldsun.superslegend.mixin;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.superworldsun.superslegend.items.AmmoContainerItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PlayerEntity.class)
public class PlayerGetArrow
{
	@Inject(method = "getProjectile", at = @At(value = "HEAD"), cancellable = true)
	private void checkQuiver(ItemStack weapon, CallbackInfoReturnable<ItemStack> callbackInfo)
	{
		if (!(weapon.getItem() instanceof ShootableItem))
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) (Object) this;
		ShootableItem shootableItem = (ShootableItem) weapon.getItem();
		
		CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curios ->
		{
			for (int i = 0; i < curios.getSlots(); i++)
			{
				ItemStack curioStack = curios.getStackInSlot(i);
				
				if (!curioStack.isEmpty() && curioStack.getItem() instanceof AmmoContainerItem)
				{
					AmmoContainerItem quiverItem = (AmmoContainerItem) curioStack.getItem();
					Pair<ItemStack, Integer> quiverContents = quiverItem.getContents(curioStack);
					
					if (quiverContents == null)
					{
						continue;
					}
					
					int arrowsCount = quiverContents.getRight();
					
					if (arrowsCount == 0)
					{
						continue;
					}
					
					ItemStack arrowsStack = quiverContents.getLeft();
					
					// if our weapon can shoot items inside of the quiver
					if (shootableItem.getSupportedHeldProjectiles().test(arrowsStack))
					{
						if (player.level.isClientSide())
						{
							callbackInfo.setReturnValue(ItemStack.EMPTY);
						}
						else
						{
							callbackInfo.setReturnValue(arrowsStack);
						}
						
						return;
					}
				}
			}
		});
	}
}
