package com.superworldsun.superslegend.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;

public class LanzanitePickaxeItem extends PickaxeItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final Multimap<Attribute, AttributeModifier> attribute;
    public LanzanitePickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.attackSpeed = attackSpeedIn;
        this.attackDamage = attackDamageIn;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.<Attribute, AttributeModifier>builder();
        attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.attribute = attributeBuilder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot)
    {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attribute : super.getDefaultAttributeModifiers(equipmentSlot);
    }

}