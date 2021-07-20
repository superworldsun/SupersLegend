package com.superworldsun.superslegend.items.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.material.MaskMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class MaskMoonmask extends ArmorItem
{

    public MaskMoonmask(Properties properties)
    {
        super(MaskMaterial.INSTANCE, EquipmentSlotType.HEAD, properties);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return SupersLegendMain.MOD_ID + ":textures/armor/giantsmask_layer_1.png";
    }

    /*private boolean isLeggings = false;

    public MaskMoonmask(IArmorMaterial material, EquipmentSlotType slot, int type) {
        super(material, slot, (new Item.Properties().tab(SupersLegendMain.RESOURCES)));
        if (type == 2) {
            isLeggings = true;
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return (!isLeggings ? SupersLegendMain.MOD_ID + ":textures/armor/moonmask_layer_1.png" : SupersLegendMain.MOD_ID + ":textures/armor/moonmask_layer_1.png");
    }*/
}