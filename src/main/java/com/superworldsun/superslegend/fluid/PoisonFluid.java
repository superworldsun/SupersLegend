package com.superworldsun.superslegend.fluid;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.curios.head.masks.DekuMask;
import com.superworldsun.superslegend.registries.FluidInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PoisonFluid extends CustomFluid {
    private static final ResourceLocation STILL_TEXTURE = texture("block/poison_still");
    private static final ResourceLocation FLOWING_TEXTURE = texture("block/poison_flowing");
    private static final ResourceLocation OVERLAY_TEXTURE = texture("block/poison_overlay");

    private static final int EFFECT_DURATION = 20;
    private static final int MAXIMUM_DURATION_FOR_REAPPLICATION = 5;
    private static final int DAMAGE_INTERVAL_TICKS = 25;

    public PoisonFluid() {
        super(FluidType.Properties.create()
                        .descriptionId("fluid_type.superslegend.poison")
                        .density(512)
                        .viscosity(1024)
                        .fallDistanceModifier(0.0F)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                        .canConvertToSource(true),
                STILL_TEXTURE, FLOWING_TEXTURE, OVERLAY_TEXTURE, 0xFFFFFFFF,
                new Vector3f(0.30F, 0.075F, 0.36F), 12.0F);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) {
            return;
        }

        if (entity instanceof Player player && DekuMask.isDekuMaskEquipped(player)) {
            entity.removeEffect(MobEffects.POISON);
            return;
        }

        if (entity.getFluidTypeHeight(FluidInit.POISON_TYPE.get()) <= 0.0D) {
            return;
        }

        MobEffectInstance poison = entity.getEffect(MobEffects.POISON);
        if (poison == null || poison.getDuration() < MAXIMUM_DURATION_FOR_REAPPLICATION) {
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, EFFECT_DURATION));
        }

        // Vanilla Poison stops at half a heart. Damage from the liquid itself
        // retains the Poison cadence while allowing prolonged immersion to kill.
        if (entity.tickCount % DAMAGE_INTERVAL_TICKS == 0) {
            entity.hurt(entity.damageSources().magic(), 1.0F);
        }
    }

    @SubscribeEvent
    public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEffectInstance().getEffect() == MobEffects.POISON
                && event.getEntity() instanceof Player player
                && DekuMask.isDekuMaskEquipped(player)) {
            event.setResult(Event.Result.DENY);
        }
    }

    private static ResourceLocation texture(String path) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, path);
    }
}
