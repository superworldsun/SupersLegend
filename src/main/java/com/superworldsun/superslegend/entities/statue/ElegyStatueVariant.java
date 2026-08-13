package com.superworldsun.superslegend.entities.statue;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.resources.ResourceLocation;

/**
 * Visual/behavioral form of an Elegy statue. Add future transformation
 * statues here without changing the entity persistence or ownership system.
 */
public enum ElegyStatueVariant {
    PLAYER(0, "elegy_of_emptyness_statue_player"),
    DEKU(1, "elegy_of_emptyness_statue_deku");

    private final int networkId;
    private final ResourceLocation model;
    private final ResourceLocation texture;

    ElegyStatueVariant(int networkId, String assetName) {
        this.networkId = networkId;
        this.model = new ResourceLocation(SupersLegendMain.MOD_ID, "geo/objects/" + assetName + ".geo.json");
        this.texture = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/" + assetName + ".png");
    }

    public int networkId() {
        return networkId;
    }

    public ResourceLocation model() {
        return model;
    }

    public ResourceLocation texture() {
        return texture;
    }

    public static ElegyStatueVariant byNetworkId(int id) {
        for (ElegyStatueVariant variant : values()) {
            if (variant.networkId == id) {
                return variant;
            }
        }
        return PLAYER;
    }
}
