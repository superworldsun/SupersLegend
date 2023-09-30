package com.superworldsun.superslegend.client.render.armor;

import com.superworldsun.superslegend.client.model.armor.DesertVoeArmorModel;
import com.superworldsun.superslegend.client.model.armor.SnowQuillArmorModel;
import com.superworldsun.superslegend.items.armors.DesertVoeArmorItem;
import com.superworldsun.superslegend.items.armors.SnowquillArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SnowQuillArmorRenderer extends GeoArmorRenderer<SnowquillArmor> {

    public SnowQuillArmorRenderer() {
        super(new SnowQuillArmorModel());
    }
}
