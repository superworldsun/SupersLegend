package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MagicArmor extends NonEnchantArmor {
    private static final int UPKEEP_INTERVAL_TICKS = 10;
    private static final int UPKEEP_COST = 1;
    private static final int DAMAGE_COST = 12;

    public MagicArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    /**
     * Prevents the final health damage while leaving vanilla's hurt animation,
     * camera shake, sounds and knockback intact. Void damage is intentionally
     * never protected against.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || player.level().isClientSide
                || event.getAmount() <= 0.0F
                || event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)
                || !isFullSetEquipped(player)
                || RupeeWalletUtil.getStoredRupees(player) <= 0) {
            return;
        }

        RupeeWalletUtil.spend(player, DAMAGE_COST);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.15F);

        // LivingEntity continues processing the hit after LivingHurtEvent. A zero
        // amount prevents heart loss while retaining its normal impact response.
        event.setAmount(0.0F);
    }

    /** Runs once per player rather than once for every equipped armor piece. */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END
                || event.player.level().isClientSide
                || !event.player.isAlive()) {
            return;
        }

        Player player = event.player;
        if (!isFullSetEquipped(player)) {
            return;
        }

        if (RupeeWalletUtil.getStoredRupees(player) <= 0) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                    15, 3, false, false, false));
            return;
        }

        if (player.tickCount % UPKEEP_INTERVAL_TICKS == 0) {
            RupeeWalletUtil.spend(player, UPKEEP_COST);
        }
    }

    private static boolean isFullSetEquipped(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ItemInit.MAGIC_ARMOR_CAP.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemInit.MAGIC_ARMOR_TUNIC.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(ItemInit.MAGIC_ARMOR_LEGGINGS.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.MAGIC_ARMOR_BOOTS.get());
    }
}
