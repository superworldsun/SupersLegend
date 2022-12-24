package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ArmorFlamebreakerEffects extends NonEnchantArmor
{


    public ArmorFlamebreakerEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.FLAMEBREAKER, slot, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Armor of the Gorons"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Wearing full set will make you fireproof"));
	}

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        if (!(event.getEntity() instanceof PlayerEntity))
        {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        int armorPartsEquipped = 0;

        if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.FLAMEBREAKER_HELMET.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.FLAMEBREAKER_TUNIC.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ItemInit.FLAMEBREAKER_LEGGINGS.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.FLAMEBREAKER_BOOTS.get())
            armorPartsEquipped++;

        if (armorPartsEquipped == 1)
        {
            if (event.getSource() == DamageSource.LAVA || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE)
            {
                event.setAmount(event.getAmount() / 1.25f);
            }
        }
        else if (armorPartsEquipped == 2)
        {
            if (event.getSource() == DamageSource.LAVA || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE)
            {
                event.setAmount(event.getAmount() / 1.5f);
            }
        }
        else if (armorPartsEquipped == 3)
        {
            if (event.getSource() == DamageSource.LAVA || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE)
            {
                event.setAmount(event.getAmount() / 1.75f);
            }
        }
        else if (armorPartsEquipped == 4)
        {
            if (event.getSource() == DamageSource.LAVA)
            {
                event.setAmount(event.getAmount() / 2f);
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
        int armorPartsEquipped = 0;

        if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.FLAMEBREAKER_HELMET.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.FLAMEBREAKER_TUNIC.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ItemInit.FLAMEBREAKER_LEGGINGS.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.FLAMEBREAKER_BOOTS.get())
            armorPartsEquipped++;

        if (armorPartsEquipped == 4)
        {
            if (event.getSource() == DamageSource.HOT_FLOOR || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE)
            {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isClientSide)
        {
        		boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().getItem() == ItemInit.FLAMEBREAKER_HELMET.get();
                boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getItem() == ItemInit.FLAMEBREAKER_TUNIC.get();
                boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().getItem() == ItemInit.FLAMEBREAKER_LEGGINGS.get();
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().getItem() == ItemInit.FLAMEBREAKER_BOOTS.get();
                if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
                	{
                		player.clearFire();
                	}
        }
    }
}