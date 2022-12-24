package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.ZoraArmorModel;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public class ArmorZoraEffects extends NonEnchantArmor
{
    private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();

    public ArmorZoraEffects(EquipmentSlotType slot, Properties properties)
    {
        super(ArmourInit.ZORA, slot, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    @Override
    public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
    {
        if (!MODELS_CACHE.containsKey(armorSlot))
        {
                MODELS_CACHE.put(armorSlot, new ZoraArmorModel(armorSlot));
        }

        return (M) MODELS_CACHE.get(armorSlot);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type)
    {
            return SupersLegendMain.MOD_ID + ":textures/models/armor/zora_armor.png";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "Swimming with the fishes"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isClientSide){
                boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.ZORA_TUNIC.get();
                if(isChestplateOn)
                	{
                	if(player.isInWater()) 
                	{
                		player.addEffect(new EffectInstance(Effect.byId(13), 10, 0, false, false, false));
                	}
                	
                }
        }
    }

}