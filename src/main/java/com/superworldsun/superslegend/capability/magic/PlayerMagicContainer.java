package com.superworldsun.superslegend.capability.magic;

import com.superworldsun.superslegend.api.MagicContainer;

public class PlayerMagicContainer implements MagicContainer {
	private float magic = getMaxMagic();

	@Override
	public float getMagic() {
		return magic;
	}

	@Override
	public float setMagic(float value) {
		return magic;
	}

	@Override
	public float getMaxMagic() {
		return 20F;
	}
}
