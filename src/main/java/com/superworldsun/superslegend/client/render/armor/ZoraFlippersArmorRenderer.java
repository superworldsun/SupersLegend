package com.superworldsun.superslegend.client.render.armor;

import com.superworldsun.superslegend.client.model.armor.ZoraArmorArmorModel;
import com.superworldsun.superslegend.client.model.armor.ZoraFlippersArmorModel;
import com.superworldsun.superslegend.items.armors.ZoraArmorArmor;
import com.superworldsun.superslegend.items.armors.ZoraFlippersArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ZoraFlippersArmorRenderer extends GeoArmorRenderer<ZoraFlippersArmor> {

    public ZoraFlippersArmorRenderer() {
        super(new ZoraFlippersArmorModel());
    }
}
