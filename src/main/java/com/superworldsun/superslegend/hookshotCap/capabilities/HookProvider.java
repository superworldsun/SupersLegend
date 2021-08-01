package com.superworldsun.superslegend.hookshotCap.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HookProvider implements ICapabilitySerializable<CompoundNBT> {
    private final HookModel skillModel;
    private final LazyOptional<HookModel> optional;

    public HookProvider(HookModel skillModel) {
        this.skillModel = skillModel;
        optional = LazyOptional.of(() -> skillModel);
    }

    public void invalidate() {
        optional.invalidate();
    }

    // Get Capability

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == HookCapability.INSTANCE) return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return skillModel.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        skillModel.deserializeNBT(nbt);
    }
}