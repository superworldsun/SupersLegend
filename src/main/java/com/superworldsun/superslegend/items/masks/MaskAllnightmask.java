package com.superworldsun.superslegend.items.masks;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.items.material.MaskMaterial;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;


import net.minecraft.item.Item.Properties;

public class MaskAllnightmask extends NonEnchantArmor

{
        public MaskAllnightmask(Properties properties)
        {
            super(MaskMaterial.INSTANCE, EquipmentSlotType.HEAD, properties);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
        {
            return SupersLegendMain.MOD_ID + ":textures/armor/giantsmask_layer_1.png";
        }

    /*@SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {
        return (A) new ModelAllnightmask(0);
    }*/


        @Override
        public void onArmorTick (ItemStack stack, World world, PlayerEntity player)
        {

            if (!world.isClientSide) {
                boolean isHelmeton = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.MASK_ALLNIGHTMASK);
                if (isHelmeton) player.addEffect(new EffectInstance(Effect.byId(16), 230, 0, false, false));
            }
            if (player.isSleeping()) {
                player.stopSleeping();
                player.displayClientMessage(new TranslationTextComponent(TextFormatting.GRAY + "You feel restless"), true);
            }
        }

        @Override
        public @Nonnull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers (@Nonnull EquipmentSlotType
        equipmentSlot){
        return HashMultimap.create();
    }


        @Override
        @OnlyIn(Dist.CLIENT)
        public void appendHoverText (ItemStack stack, World world, List < ITextComponent > list, ITooltipFlag flag)
        {
            super.appendHoverText(stack, world, list, flag);
            list.add(new StringTextComponent(TextFormatting.WHITE + "Cant sleep huh?"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Grants nightvision"));
        }
    }




