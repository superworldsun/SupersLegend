package com.superworldsun.superslegend.items.armors;

import java.util.function.Consumer;

import com.superworldsun.superslegend.client.model.armor.ArmorModelPart;
import com.superworldsun.superslegend.client.render.armor.GeoArmorRendererExtension;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class DesertVoeHelmetItem extends DesertVoeArmorItem {
    public DesertVoeHelmetItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        GeoArmorRendererExtension<DesertVoeHelmetItem> extention = new GeoArmorRendererExtension<>("desert_voe");
        extention.setModelName("desert_voe_helmet");
        extention.showModelParts(ArmorModelPart.BODY);
        consumer.accept(extention);
    }
}
