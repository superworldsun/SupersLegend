package com.superworldsun.superslegend.hookshotCap.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class HookStorage implements Capability.IStorage<HookModel> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<HookModel> capability, HookModel instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<HookModel> capability, HookModel instance, Direction side, INBT nbt) {
        instance.deserializeNBT((CompoundNBT) nbt);
    }
}