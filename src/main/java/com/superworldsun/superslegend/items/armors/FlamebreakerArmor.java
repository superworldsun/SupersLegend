package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class FlamebreakerArmor extends NonEnchantArmor {
    public FlamebreakerArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    //TODO, these events dont work anymore, reduce damage depending on amount of armor worn
    /*@SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }
        Player player = (Player) event.getEntity();
        int armorPartsEquipped = 0;

        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.FLAMEBREAKER_HELMET.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.FLAMEBREAKER_TUNIC.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.FLAMEBREAKER_LEGGINGS.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.FLAMEBREAKER_BOOTS.get())
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
    }*/

    /*@SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event)
    {
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }
        Player player = (Player) event.getEntity();
        int armorPartsEquipped = 0;

        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.FLAMEBREAKER_HELMET.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.FLAMEBREAKER_TUNIC.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.FLAMEBREAKER_LEGGINGS.get())
            armorPartsEquipped++;

        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.FLAMEBREAKER_BOOTS.get())
            armorPartsEquipped++;

        if (armorPartsEquipped == 4)
        {
            if (event.getSource() == DamageSource.HOT_FLOOR || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE)
            {
                event.setCanceled(true);
            }
        }
    }*/

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide)
        {
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.FLAMEBREAKER_HELMET.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.FLAMEBREAKER_TUNIC.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.FLAMEBREAKER_LEGGINGS.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.FLAMEBREAKER_BOOTS.get();
            if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
            {
                player.clearFire();
            }
        }
    }
}
