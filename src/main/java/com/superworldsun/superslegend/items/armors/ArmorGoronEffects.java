package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.GoronArmorModel;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ArmorGoronEffects extends NonEnchantArmor
{
    private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();

    public ArmorGoronEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.GORON, slot, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    @Override
    public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
    {
        if (!MODELS_CACHE.containsKey(armorSlot))
        {
            MODELS_CACHE.put(armorSlot, new GoronArmorModel(armorSlot));
        }

        return (M) MODELS_CACHE.get(armorSlot);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type)
    {
        return SupersLegendMain.MOD_ID + ":textures/models/armor/goron_armor.png";
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "This Armor set is Hot!"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	if (!world.isClientSide)
    	{
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getItem() == ItemInit.GORON_TUNIC.get();

            if (isChestplateOn)
            {
                player.clearFire();
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        if (!(event.getEntity() instanceof PlayerEntity))
        {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getItem() == ItemInit.GORON_TUNIC.get();

        if (isChestplateOn)
        {
            if (event.getSource() == DamageSource.LAVA)
            {
                event.setAmount(event.getAmount() / 3);
            }
            if (event.getSource() == DamageSource.IN_FIRE)
            {
                event.setAmount(event.getAmount() / 3);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event)
    {
        if (!(event.getEntity() instanceof PlayerEntity))
        {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getItem() == ItemInit.GORON_TUNIC.get();

        if (isChestplateOn)
        {
            if (event.getSource() == DamageSource.HOT_FLOOR)
            {
                event.setCanceled(true);
            }
        }
    }
}