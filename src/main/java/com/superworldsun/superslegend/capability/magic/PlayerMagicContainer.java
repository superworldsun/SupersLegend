package com.superworldsun.superslegend.capability.magic;

import com.superworldsun.superslegend.api.MagicContainer;

public class PlayerMagicContainer implements MagicContainer {
	private float magic = getMaxMagic();

	@Override
	public float getMagic() {
		return magic;
	}

	@Override
	public void setMagic(float value) {
		magic = value;
	}

	@Override
	public float getMaxMagic() {
		return 20F;
	}
}
