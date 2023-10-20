package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MagicArmor extends NonEnchantArmor {
    public MagicArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    //When the player takes damage it takes 12 rupees from the players inventory
    //TODO Add some sound effect for the damage
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.MAGIC_ARMOR_CAP.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.MAGIC_ARMOR_TUNIC.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.MAGIC_ARMOR_LEGGINGS.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.MAGIC_ARMOR_BOOTS.get();
            if (isHelmetOn && isChestplateOn && isLeggingsOn && isBootsOn) {
                int rupeesTaken = 0;
                for (int i = 0; i < player.getInventory().getContainerSize() && rupeesTaken < 12; i++) {
                    ItemStack armorStack = player.getInventory().getItem(i);
                    if (armorStack.getItem() == ItemInit.RUPEE.get()) {
                        int stackSize = armorStack.getCount();
                        if (stackSize >= 12 - rupeesTaken) {
                            armorStack.shrink(12 - rupeesTaken);
                            rupeesTaken = 12;
                        } else {
                            armorStack.setCount(0);
                            rupeesTaken += stackSize;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide) {
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.MAGIC_ARMOR_CAP.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.MAGIC_ARMOR_TUNIC.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.MAGIC_ARMOR_LEGGINGS.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.MAGIC_ARMOR_BOOTS.get();
            if (isHelmetOn & isChestplateOn & isLeggingsOn & isBootsOn) {
                MobEffectInstance effect = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
                if (effect != null && effect.getAmplifier() == 100)
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 14, 99, false, false, false));
                else {
                    boolean hasRupees = false;
                    for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                        ItemStack armorStack = player.getInventory().getItem(i);
                        if (armorStack.getItem() == ItemInit.RUPEE.get()) {
                            armorStack.shrink(1);
                            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 100, false, false, false));
                            hasRupees = true;
                            break;
                        }
                    }
                    if (!hasRupees) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 3, false, false, false));
                    }
                }
            }
        }
    }
}
