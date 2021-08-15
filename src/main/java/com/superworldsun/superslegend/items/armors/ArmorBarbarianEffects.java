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
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ArmorBarbarianEffects extends NonEnchantArmor
{


    public ArmorBarbarianEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.BARBARIAN, slot, properties);
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
        int armorPartsEquipped = 0;

        if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.BARBARIAN_HELMET.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.BARBARIAN_ARMOR.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ItemInit.BARBARIAN_LEG_WRAPS.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.BARBARIAN_BOOTS.get())
            armorPartsEquipped++;

        if (!world.isClientSide)
        {
            if (armorPartsEquipped > 1 && armorPartsEquipped < 4)
            {
                player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 3, 0, false, false, false));
            }

            if (armorPartsEquipped == 4)
            {
                player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 3, 1, false, false, false));
            }
        }
    }
}