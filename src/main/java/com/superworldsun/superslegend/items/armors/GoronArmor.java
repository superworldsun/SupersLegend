package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.api.IncomingDamageModifier;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GoronArmor extends NonEnchantArmor implements IncomingDamageModifier {
    public GoronArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide){
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.GORON_TUNIC.get();

            if (isChestplateOn)
            {
                player.clearFire();
            }
        }
    }
    //TODO, I want In Fire damage and lava to be exactly /3, and to be immune to Hot Floor damage
    @Override
    public boolean canModifyDamage(DamageSource damage) {
        return damage.is(DamageTypes.IN_FIRE) || damage.is(DamageTypes.LAVA) || damage.is(DamageTypes.HOT_FLOOR);
    }

    @Override
    public float getDamageModifier() {
        return -0.5f;
    }
}
