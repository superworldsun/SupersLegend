package com.superworldsun.superslegend.items.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class SacredShieldCapability {
	@CapabilityInject(SacredShieldState.class)
	public static Capability<SacredShieldState> INSTANCE;
}
