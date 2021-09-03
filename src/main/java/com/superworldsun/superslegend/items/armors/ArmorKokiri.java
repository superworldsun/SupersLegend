package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.client.model.ModelAllnightmask;
import com.superworldsun.superslegend.client.model.ModelKokiriBoots;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArmorKokiri extends NonEnchantArmor
{

    public ArmorKokiri(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.KOKIRI, slot, properties);
    }

    /*@SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType feety, A _default)
    {
        return (A) new ModelKokiriBoots(0);
    }*/

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "Traditional Heros Garb"));
    }
}