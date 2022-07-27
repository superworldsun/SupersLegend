package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.KokiriArmorModel;
import com.superworldsun.superslegend.client.model.armor.KokiriBootsModel;
import com.superworldsun.superslegend.client.model.armor.ZorasFlippersModel;
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
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public class ArmorFlippersEffects extends NonEnchantArmor
{
    private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();

    public ArmorFlippersEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.FLIPPERS, slot, properties);
    }

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.AQUA + "Provides the ability to swim like a Zora"));
	}

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    @Override
    public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
    {
        if (!MODELS_CACHE.containsKey(armorSlot))
        {
            if (armorSlot == EquipmentSlotType.FEET)
            {
                MODELS_CACHE.put(armorSlot, new ZorasFlippersModel());
            }
        }

        return (M) MODELS_CACHE.get(armorSlot);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type)
    {
        if (armorSlot == EquipmentSlotType.FEET)
            return SupersLegendMain.MOD_ID + ":textures/models/armor/zoras_flippers.png";
        else
            return SupersLegendMain.MOD_ID + ":textures/models/armor/zoras_flippers.png";
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isClientSide){
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.ZORA_FLIPPERS);
                if(isBootsOn)
                	{
                	if(player.isInWater()&&player.isSprinting()) 
                	{
                	    //todo add potion back
                		//player.addEffect(new EffectInstance(PotionList.zoras_grace_effect, 8, 0, false, false));
                	}
                	if(!player.isSprinting()) 
                	{
                		//player.removeEffect(PotionList.zoras_grace_effect);
                	}
                }
        }
    }
}