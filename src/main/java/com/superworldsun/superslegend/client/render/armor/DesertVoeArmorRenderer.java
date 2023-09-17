package com.superworldsun.superslegend.client.render.armor;

import com.superworldsun.superslegend.client.model.armor.DesertVoeArmorModel;
import com.superworldsun.superslegend.items.armors.DesertVoeArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DesertVoeArmorRenderer extends GeoArmorRenderer<DesertVoeArmorItem> {

    //TODO, in block bench file, helmet_body should be apart of helmet, but be attached to chest bone.
    // leggings_body should be apart of leggings, but attached to body bone

    public DesertVoeArmorRenderer() {
        super(new DesertVoeArmorModel());
    }
}
