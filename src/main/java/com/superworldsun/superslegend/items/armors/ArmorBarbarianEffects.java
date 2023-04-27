package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.*;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public class ArmorBarbarianEffects extends NonEnchantArmor
{
    private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();

    public ArmorBarbarianEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.BARBARIAN, slot, properties);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
    {
        if (MODELS_CACHE.isEmpty())
        {
            MODELS_CACHE.put(EquipmentSlotType.HEAD, new BarbarianHelmetModel());
            MODELS_CACHE.put(EquipmentSlotType.CHEST, new BarbarianChestModel());
            MODELS_CACHE.put(EquipmentSlotType.LEGS, new BarbarianLegWrapsModel());
            MODELS_CACHE.put(EquipmentSlotType.FEET, new BarbarianBootsModel());
        }

        return (M) MODELS_CACHE.get(armorSlot);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return SupersLegendMain.MOD_ID + ":textures/models/armor/barbarian_armor.png";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
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