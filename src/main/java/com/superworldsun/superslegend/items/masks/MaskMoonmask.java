package com.superworldsun.superslegend.items.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ArmorInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;


import net.minecraft.item.Item.Properties;

public class MaskMoonmask extends ArmorItem {

    private boolean isLeggings = false;

    public MaskMoonmask(IArmorMaterial material, EquipmentSlotType slot, int type) {
        super(material, slot, (new Item.Properties().tab(SupersLegendMain.RESOURCES)));
        if (type == 2) {
            isLeggings = true;
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return (!isLeggings ? SupersLegendMain.MOD_ID + ":textures/armor/moonmask_layer_1.png" : SupersLegendMain.MOD_ID + ":textures/armor/moonmask_layer_1.png");
    }

    /*public MaskMoonmask(IArmorMaterial material, EquipmentSlotType slot, int type){
        super(material, slot, (new Item.Properties().tab(SupersLegendMain.RESOURCES).fireResistant()));
        if (type == 2) {
            isLeggings = true;
        }
    }

    @Override
    public String getArmorTexture (ItemStack stack, Entity entity, EquipmentSlotType slot, String type){
        return (!isLeggings ? SupersLegendMain.MOD_ID + ":textures/armor/moonmask_layer_1.png" : SupersLegendMain.MOD_ID + ":textures/armor/moonmask_layer_1.png");
    }*/
}