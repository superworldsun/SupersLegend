package com.superworldsun.superslegend.util.events;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEventHandler {

    @SubscribeEvent
    public void onpigjump(LivingEvent.LivingJumpEvent event)
    {
        if(event.getEntityLiving() instanceof PigEntity) {

                event.getEntityLiving().addEffect(new EffectInstance(Effects.LEVITATION, 1000, 5));
                event.getEntityLiving().playSound(SoundInit.PIGFLY.get(), 0.25F, 1.0F);
        }
    }

    @SubscribeEvent
    public void cooldown(LivingEntityUseItemEvent.Start event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            //The above player-check is better than using Minecraft.getInstance().player, as it ensures multiplayer compatibility
            if (PotionUtils.getPotion(event.getItem()) == Potion.byName("minecraft:night_vision")) {
                            event.setCanceled(true);
                        }
                    }
    }
}
