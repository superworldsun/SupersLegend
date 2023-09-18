package com.superworldsun.superslegend.client.render.armor;

import com.superworldsun.superslegend.client.model.armor.ZoraArmorArmorModel;
import com.superworldsun.superslegend.items.armors.ZoraArmorArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ZoraArmorArmorRenderer extends GeoArmorRenderer<ZoraArmorArmor> {

    public ZoraArmorArmorRenderer() {
        super(new ZoraArmorArmorModel());
    }
}
