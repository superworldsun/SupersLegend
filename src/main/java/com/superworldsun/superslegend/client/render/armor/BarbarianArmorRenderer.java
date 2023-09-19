package com.superworldsun.superslegend.client.render.armor;

import com.superworldsun.superslegend.client.model.armor.BarbarianArmorModel;
import com.superworldsun.superslegend.items.armors.BarbarianArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BarbarianArmorRenderer extends GeoArmorRenderer<BarbarianArmor> {

    public BarbarianArmorRenderer() {
        super(new BarbarianArmorModel());
    }
}
