package com.superworldsun.superslegend.capability.hookshot;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public class HookModel implements INBTSerializable<CompoundTag> {
    private boolean hasHook;
    private boolean keyUpIsDown = false;

    public boolean getkeyUpIsDown() {
        return keyUpIsDown;
    }

    public void setkeyUpIsDown(boolean bool) {
        keyUpIsDown = bool;
    }

    public boolean getHasHook() {
        return hasHook;
    }

    public void setHasHook(boolean bool) {
        hasHook = bool;
    }

    public static HookModel get(Player player) {
        return player.getCapability(HookProvider.HOOK_CAPABILITY).orElseThrow(() ->
                new IllegalArgumentException("Player " + player.getName().getString() + " does not have a Model!")
        );
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("hasHook", hasHook);
        compound.putBoolean("keyUpIsDown", keyUpIsDown);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        hasHook = nbt.getBoolean("hasHook");
        keyUpIsDown = nbt.getBoolean("keyUpIsDown");
    }
}