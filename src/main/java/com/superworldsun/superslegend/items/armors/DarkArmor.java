package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

public class DarkArmor extends NonEnchantArmor {
    public DarkArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide){
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.DARK_CAP.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.DARK_TUNIC.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.DARK_LEGGINGS.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.DARK_BOOTS.get();
            if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn && !level.isDay())
            {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4, 1, false, false, false));
            }
        }
    }
}