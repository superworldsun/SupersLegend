package com.superworldsun.superslegend.util.events;


import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.HeartEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.util.Random;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModEventHandler {


    /**Function that spawns a HeartEntity when killing an entity.
     */
    @SubscribeEvent
    public void onEntityKilledSpawnHeart(LivingDeathEvent event){
        Random random = new Random();
        //Check if player killed the entity
        if (event.getSource().getEntity() instanceof PlayerEntity) {
            Entity entity = event.getEntity();
            PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
            World level = player.level;
            if(!level.isClientSide) { //Here you should put something to make it appear with X%.
                double d0 = (double) (random.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (random.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (random.nextFloat() * 0.5F) + 0.25D;
                HeartEntity heartEntity = new HeartEntity(level, entity.getX() + d0, entity.getY() + d1, entity.getZ() + d2);
                heartEntity.value= 1;
                level.addFreshEntity(heartEntity);
            }
        }
    }

    @SubscribeEvent
    public static void interModComms(InterModEnqueueEvent e){
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").size(1).build());
    }

}