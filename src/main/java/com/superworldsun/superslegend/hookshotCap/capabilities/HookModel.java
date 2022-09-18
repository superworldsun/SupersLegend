package com.superworldsun.superslegend.hookshotCap.capabilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class HookModel implements INBTSerializable<CompoundNBT> {

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

    public static HookModel get(PlayerEntity player) {
        return player.getCapability(HookCapability.INSTANCE).orElseThrow(() ->
                new IllegalArgumentException("Player " + player.getName().getString() + " does not have a Model!")
        );
    }

    public static HookModel get() {
        return Minecraft.getInstance().player.getCapability(HookCapability.INSTANCE).orElseThrow(() ->
                new IllegalArgumentException("Player does not have a Model!")
        );
    }



    // Serialise and Deserialise

    @Override
    public CompoundNBT serializeNBT() {

        CompoundNBT compound = new CompoundNBT();
        compound.putBoolean("hasHook", hasHook);
        compound.putBoolean("keyUpIsDown", keyUpIsDown);

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        hasHook = nbt.getBoolean("hasHook");
        keyUpIsDown = nbt.getBoolean("keyUpIsDown");
    }
}