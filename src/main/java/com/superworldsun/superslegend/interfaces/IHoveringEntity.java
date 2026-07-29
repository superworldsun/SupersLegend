package com.superworldsun.superslegend.interfaces;

public interface IHoveringEntity
{
    int increaseHoverTime();

    int getHoverTime();

    void setHoverTime(int time);

    void setHoverHeight(double height);

    double getHoverHeight();

    void setHovering(boolean hovering);

    boolean isHovering();

    void setHazardHovering(boolean hazardHovering);

    boolean isHazardHovering();

    void setBounceHovering(boolean bounceHovering);

    boolean isBounceHovering();

    void setStickyBouncePending(boolean stickyBouncePending);

    boolean isStickyBouncePending();

    void setWasWearingHoverBoots(boolean wearingHoverBoots);

    boolean wasWearingHoverBoots();

    void setWasOnStickyHoverBlock(boolean onStickyHoverBlock);

    boolean wasOnStickyHoverBlock();

    void setNormalHoverDeadline(int deadlineTick);

    int getNormalHoverDeadline();

    void setJumpedFromBlock(boolean state);

    boolean jumpedFromBlock();
}
