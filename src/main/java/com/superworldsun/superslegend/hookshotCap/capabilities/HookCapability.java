package com.superworldsun.superslegend.hookshotCap.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class HookCapability {
    @CapabilityInject(HookModel.class)
    public static Capability<HookModel> INSTANCE;

}