package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.HeartEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class HeartDropEvents {
    private static final int HEART_HEALING = 1;
    private static final double HEART_DROP_CHANCE = 0.30;

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (SpawnerMobDropProtection.isSpawnerMob(event.getEntity())) {
            return;
        }
        if (event.getSource().getEntity() instanceof Player && event.getEntity() instanceof Enemy) {
            Entity entity = event.getEntity();
            Player player = (Player) event.getSource().getEntity();
            Level level = player.level();
            Random random = new Random();

            if (!level.isClientSide && random.nextDouble() <= HEART_DROP_CHANCE) {
                double xOffset = random.nextFloat() * 0.5F - 0.25F;
                double yOffset = random.nextFloat() * 0.5F;
                double zOffset = random.nextFloat() * 0.5F - 0.25F;
                HeartEntity heart = new HeartEntity(level, entity.getX() + xOffset, entity.getY() + yOffset, entity.getZ() + zOffset);
                heart.value = HEART_HEALING;
                level.addFreshEntity(heart);
            }
        }
    }
}
