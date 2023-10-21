package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PegasusBootsArmor extends NonEnchantArmor {
    public PegasusBootsArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    //TODO, going to change to check if the player is holding only Forward key, then change the speed boost from an effect, to an attribute modifier.
    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.PEGASUS_BOOTS.get();

        if (isBootsOn)
        {
            if (player.isInWater())
            {
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
            }
            else if (player.onGround() && player.isSprinting() && player.getFoodData().getFoodLevel() != 0)
            {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 9, 1, false, false));
                player.causeFoodExhaustion(0.04f);
            }
        }
    }
}
