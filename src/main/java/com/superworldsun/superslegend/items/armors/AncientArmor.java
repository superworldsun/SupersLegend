package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.items.weapons.swords.GuardianSword;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class AncientArmor extends NonEnchantArmor {
    public AncientArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event)
    {
        // Check if is player doing the damage.
        if (event.getSource().getDirectEntity() instanceof Player)
        {
            // Get Player.
            Player player = (Player) event.getSource().getDirectEntity();

            // Check if player is wearing it armor and has a Guardian Weapon held
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.ANCIENT_HELMET.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.ANCIENT_CUIRASS.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.ANCIENT_GREAVES.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.ANCIENT_BOOTS.get();
            if (player.getMainHandItem().getItem() instanceof GuardianSword & isHelmetOn & isChestplateOn & isLeggingsOn & isBootsOn)
            {
                event.setAmount(event.getAmount() * 1.8f);
            }
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide) {
            int armorPartsEquipped = 0;

            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.ANCIENT_HELMET.get())
                armorPartsEquipped++;

            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.ANCIENT_CUIRASS.get())
                armorPartsEquipped++;

            if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.ANCIENT_GREAVES.get())
                armorPartsEquipped++;

            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.ANCIENT_BOOTS.get())
                armorPartsEquipped++;
            
                if (armorPartsEquipped > 1 && armorPartsEquipped < 4) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 0, false, false, false));
                } 
                else if (armorPartsEquipped == 4) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 1, false, false, false));
                }
        }
    }
}
