package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributesEvents
{
    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event)
    {
        //TODO, turn on when fixed for temp system
        //event.add(EntityType.PLAYER, AttributeInit.COLD_RESISTANCE.get());
        //event.add(EntityType.PLAYER, AttributeInit.HEAT_RESISTANCE.get());
        //event.add(EntityType.PLAYER, AttributeInit.HELL_HEAT_RESISTANCE.get());
    }
}
