package com.superworldsun.superslegend.items.capabilities;

import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class SacredShieldProvider implements ICapabilitySerializable<CompoundNBT> {
	private final SacredShieldState shieldState;
    private final LazyOptional<SacredShieldState> optional;
    
    public SacredShieldProvider(SacredShieldState shieldState) {
    	this.shieldState = shieldState;
    	optional = LazyOptional.of(() -> shieldState);
    }

    public void invalidate() {
        optional.invalidate();
    }

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == SacredShieldCapability.INSTANCE) return optional.cast();
		
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		return shieldState.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		shieldState.deserializeNBT(nbt);
	}

}
