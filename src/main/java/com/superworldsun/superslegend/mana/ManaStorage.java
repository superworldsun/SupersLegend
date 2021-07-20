package com.superworldsun.superslegend.mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaStorage implements IStorage<IMana>
{
	@Override
	public CompoundNBT writeNBT(Capability<IMana> capability, IMana instance, Direction side)
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putFloat("Mana", instance.getMana());
		return nbt;
	}
	
	@Override
	public void readNBT(Capability<IMana> capability, IMana instance, Direction side, INBT inbt)
	{
		CompoundNBT nbt = (CompoundNBT) inbt;
		instance.setMana(nbt.getFloat("Mana"));
	}
}
