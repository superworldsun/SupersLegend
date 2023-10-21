package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClimbingGearArmor extends NonEnchantArmor {
    public ClimbingGearArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    //TODO, optimize this code like flamebreaker and others to count parts, maybe give each amount a different speed
    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player)
    {
        super.onArmorTick(stack, level, player);
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.CLIMBERS_BANDANNA.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.CLIMBING_GEAR.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.CLIMBING_PANTS.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.CLIMBING_BOOTS.get();
            if (isHelmetOn & isChestplateOn || isHelmetOn & isLeggingsOn || isHelmetOn & isBootsOn ||
                    isChestplateOn & isLeggingsOn || isChestplateOn & isBootsOn || isLeggingsOn & isBootsOn) {
                if (!player.isSpectator() && player.horizontalCollision && player.zza > 0 && !player.isInWaterOrRain()) {
                    player.setDeltaMovement(player.getDeltaMovement().x(), 0.05, player.getDeltaMovement().z());
                    player.fallDistance = 0F;
                }
            }
            if (isHelmetOn & isChestplateOn & isLeggingsOn & isBootsOn) {
                if (!player.isSpectator() && player.horizontalCollision && player.zza > 0 && !player.isInWaterOrRain()) {
                    player.setDeltaMovement(player.getDeltaMovement().x(), 0.1, player.getDeltaMovement().z());
                    player.fallDistance = 0F;
            }
        }
    }
}
