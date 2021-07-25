package com.superworldsun.superslegend.api;

import com.superworldsun.superslegend.mana.Mana;

public class ManaAPI extends Mana {

    public static final float maxMana = 20.0F;
    public static float mana = maxMana;

    @Override
    public float getMana()
    {
        return mana;
    }

    @Override
    public float getMaxMana()
    {
        return maxMana;
    }

    @Override
    public void spendMana(float amount)
    {
        // Can't go below 0
        mana = Math.max(0, mana - amount);
    }

    @Override
    public void setMana(float amount)
    {
        // Only between 0 and maximum
        mana = Math.max(0, Math.min(maxMana, amount));
    }

    @Override
    public void restoreMana(float amount)
    {
        // Can't go above maximum
        mana = Math.min(maxMana, mana + amount);
    }
}
