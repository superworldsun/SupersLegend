package com.superworldsun.superslegend.client.render.armor;

import com.superworldsun.superslegend.client.model.armor.DesertVoeArmorModel;
import com.superworldsun.superslegend.client.model.armor.KokiriArmorModel;
import com.superworldsun.superslegend.items.armors.DesertVoeArmorItem;
import com.superworldsun.superslegend.items.armors.KokiriArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class KokiriArmorRenderer extends GeoArmorRenderer<KokiriArmor> {

    public KokiriArmorRenderer() {
        super(new KokiriArmorModel());
    }
}
