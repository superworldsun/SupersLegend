package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ArmorAncientEffects extends NonEnchantArmor
{

    public ArmorAncientEffects(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.YELLOW + "Armor made from Ancient technology"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Wearing the set grants defense"));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
    {
        boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.ANCIENT_HELMET);
        boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(ItemInit.ANCIENT_CUIRASS);
        boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().equals(ItemInit.ANCIENT_GREAVES);
        boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.ANCIENT_BOOTS);
        if (!world.isClientSide)
        {
            if(isHelmetOn&isChestplateOn || isHelmetOn&isLeggingsOn || isHelmetOn&isBootsOn ||
                    isChestplateOn&isLeggingsOn || isChestplateOn&isBootsOn || isLeggingsOn&isBootsOn)
            {
                player.addEffect(new EffectInstance(Effect.byId(11), 3, 0, false, false, false));
            }
        }

        if (!world.isClientSide)
        {
            if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
            {
                player.addEffect(new EffectInstance(Effect.byId(11), 3, 1, false, false, false));
            }
        }
    }
}