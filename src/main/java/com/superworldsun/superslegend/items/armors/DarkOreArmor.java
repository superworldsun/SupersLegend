package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkOreArmor extends NonEnchantArmor {
    public DarkOreArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String layer = slot == EquipmentSlot.LEGS ? "2" : "1";
        return SupersLegendMain.MOD_ID + ":textures/armor/dark_ore_armor_layer_" + layer + ".png";
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, level, entity, itemSlot, isSelected);
        Player player = (Player) entity;
        if (player.tickCount % 20 == 0) { // Check if 20 ticks have passed
            if (level.isDay()) {
                if (entity instanceof Player && player.getMainHandItem().equals(stack) || player.getOffhandItem().equals(stack)) {
                    if (level.canSeeSky(player.blockPosition())) {
                        stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    }
                }
            }
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.tickCount % 20 == 0) { // Check if 20 ticks have passed
            if (level.isDay()) {
                    if (level.canSeeSky(player.blockPosition())) {
                        stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(getEquipmentSlot()));
                    }
            }
        }
    }
}
