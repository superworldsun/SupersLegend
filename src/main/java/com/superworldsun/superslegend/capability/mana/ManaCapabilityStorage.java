package com.superworldsun.superslegend.capability.mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaCapabilityStorage implements IStorage<ManaCapability> {
	@Override
	public CompoundNBT writeNBT(Capability<ManaCapability> capability, ManaCapability instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putFloat("mana", instance.getMana());
		return nbt;
	}

	@Override
	public void readNBT(Capability<ManaCapability> capability, ManaCapability instance, Direction side, INBT inbt) {
		CompoundNBT nbt = (CompoundNBT) inbt;
		instance.setMana(nbt.getFloat("mana"));
	}
}
