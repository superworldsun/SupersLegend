package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.mobs.GoldSkulltulaEntity;
import com.superworldsun.superslegend.entities.statue.ElegyStatueEntity;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEntityAttributes {
    private ModEntityAttributes() {
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.GOLD_SKULLTULA.get(), GoldSkulltulaEntity.createAttributes().build());
        event.put(EntityTypeInit.ELEGY_STATUE.get(), ElegyStatueEntity.createAttributes().build());
    }
}
