package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
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

public class ArmorBarbarianEffects extends NonEnchantArmor
{


    public ArmorBarbarianEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.barbarian, slot, properties);
    }

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Armor once worn by warriors from the Faron region"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Wearing the set grants strength"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.BARBARIAN_HELMET);
        boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(ItemInit.BARBARIAN_ARMOR);
        boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().equals(ItemInit.BARBARIAN_LEG_WRAPS);
        boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.BARBARIAN_BOOTS);
        if (!world.isClientSide)
        {
            if(isHelmetOn&isChestplateOn || isHelmetOn&isLeggingsOn || isHelmetOn&isBootsOn ||
               isChestplateOn&isLeggingsOn || isChestplateOn&isBootsOn || isLeggingsOn&isBootsOn)
            {
                player.addEffect(new EffectInstance(Effect.byId(5), 3, 0, false, false, false));
            }
        }
        if (!world.isClientSide)
        {
                if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
                	{
                		player.addEffect(new EffectInstance(Effect.byId(5), 3, 1, false, false, false));
                	}
        }
    }
}