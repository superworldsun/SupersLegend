package com.superworldsun.superslegend.items.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class SacredShieldStorage implements Capability.IStorage<SacredShieldState> {

	@Override
	public INBT writeNBT(Capability<SacredShieldState> capability, SacredShieldState instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<SacredShieldState> capability, SacredShieldState instance, Direction side,
			INBT nbt) {
        instance.deserializeNBT((CompoundNBT) nbt);
	}

}
