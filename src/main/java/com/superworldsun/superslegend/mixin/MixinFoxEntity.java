package com.superworldsun.superslegend.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

@Mixin(FoxEntity.class)
public abstract class MixinFoxEntity extends AnimalEntity
{
	// This constructor is fake and never used
	protected MixinFoxEntity()
	{
		super(null, null);
	}
	
	//@Inject(method = "trusts", at = @At("HEAD"), cancellable = true)
	private void injectTrusts(UUID uuid, CallbackInfoReturnable<Boolean> ci)
	{
		PlayerEntity player = level.getPlayerByUUID(uuid);
		
		if (player != null && player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.MASK_KEATONMASK.get())
		{
			ci.setReturnValue(true);
		}
	}
}
