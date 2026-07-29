package com.superworldsun.superslegend.interfaces;

/**
 * Synced custom animation state carried by every player.
 *
 * <p>Gameplay code should normally use {@code PlayerAnimationUtil} instead of
 * casting to this interface directly.</p>
 */
public interface IPlayerAnimationState {
    int superslegend$getAnimationFlags();

    void superslegend$setAnimationFlags(int animationFlags);
}
