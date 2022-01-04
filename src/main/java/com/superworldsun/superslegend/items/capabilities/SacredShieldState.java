package com.superworldsun.superslegend.items.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class SacredShieldState implements INBTSerializable<CompoundNBT> {
	
	private int ticksToRegenerate;	
	

	public int getTicksToRegenerate() {
		return ticksToRegenerate;
	}

	public void setTicksToRegenerate(int ticksToRegenerate) {
		this.ticksToRegenerate = ticksToRegenerate;
	}
	
	public static SacredShieldState get(ItemStack item) {
		return item.getCapability(SacredShieldCapability.INSTANCE).orElseThrow(() ->
			new IllegalArgumentException("Item " + item.getDisplayName().getString() + " does not have a State!")
		);		
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("ticksToRegenerate", ticksToRegenerate);
		return compound;
		
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		ticksToRegenerate = nbt.getInt("ticksToRegenerate");
	}


}
