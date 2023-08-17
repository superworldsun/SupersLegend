package com.superworldsun.superslegend.loot;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class VanillaMobDrops {

    @SubscribeEvent
    public static void customLootMonsterEntity(LivingDropsEvent event) {
        Random random = new Random();

        // This should make it so any type of monster from other mods should also drop rupees occasionally
        if (event.getSource().getEntity() instanceof Player && event.getEntity() instanceof Monster
                && !(event.getEntity() instanceof Blaze) && !(event.getEntity() instanceof CaveSpider)
                && !(event.getEntity() instanceof Creeper) && !(event.getEntity() instanceof Drowned)
                && !(event.getEntity() instanceof ElderGuardian) && !(event.getEntity() instanceof EnderMan)
                && !(event.getEntity() instanceof Endermite) && !(event.getEntity() instanceof EnderDragon)
                && !(event.getEntity() instanceof Evoker) && !(event.getEntity() instanceof Ghast)
                && !(event.getEntity() instanceof Guardian) && !(event.getEntity() instanceof Husk)
                && !(event.getEntity() instanceof Illusioner) && !(event.getEntity() instanceof MagmaCube)
                && !(event.getEntity() instanceof Phantom) && !(event.getEntity() instanceof Pillager)
                && !(event.getEntity() instanceof Ravager) && !(event.getEntity() instanceof Shulker)
                && !(event.getEntity() instanceof Silverfish) && !(event.getEntity() instanceof Skeleton)
                && !(event.getEntity() instanceof Slime) && !(event.getEntity() instanceof Spider)
                && !(event.getEntity() instanceof Stray) && !(event.getEntity() instanceof Vindicator)
                && !(event.getEntity() instanceof Witch) && !(event.getEntity() instanceof WitherBoss)
                && !(event.getEntity() instanceof WitherSkeleton) && !(event.getEntity() instanceof ZombieVillager)
                && !(event.getEntity() instanceof ZombifiedPiglin) && !(event.getEntity() instanceof Zombie)) {
            if (random.nextInt(7) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
            if (random.nextInt(14) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootRingDrop(LivingDropsEvent event) {
        Random random = new Random();

        // This should make it so any type of monster from other mods should also drop rupees occasionally
        if (event.getSource().getEntity() instanceof Player && event.getEntity() instanceof Monster) {
            if (random.nextInt(45) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.UNAPPRAISED_RING.get(), random.nextInt(3)));
        }
    }

    @SubscribeEvent
    public static void customLootBlaze(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Blaze)) {
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
            if (random.nextInt(10) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(60) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootCaveSpider(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof CaveSpider)) {
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
            if (random.nextInt(14) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(50) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootCreeper(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Creeper)) {
            if (random.nextInt(4) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
            if (random.nextInt(7) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootDrowned(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Drowned)) {
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
            if (random.nextInt(14) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(70) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootElderGuardian(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof ElderGuardian)) {
            if (random.nextInt(14) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RED_RUPEE.get(), 1));
            if (random.nextInt(1) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootEnderman(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof EnderMan)) {
            if (random.nextInt(3) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(8) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootEndermite(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Endermite)) {
            if (random.nextInt(3) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(7) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootEvoker(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Evoker)) {
            if (random.nextInt(3) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(25) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootGhast(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Ghast)) {
            if (random.nextInt(2) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(4) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(35) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootGuardian(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Guardian)) {
            if (random.nextInt(4) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(55) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootHusk(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Husk)) {
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(12) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootMagmaCube(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof MagmaCube)) {
            if (random.nextInt(8) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(10) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(90) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
            if (random.nextInt(20) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RED_JELLY.get(), 1));
            if (random.nextInt(60) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_JELLY.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootPhantom(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Phantom)) {
            if (random.nextInt(5) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(10) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(45) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootPillager(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Pillager)) {
            if (random.nextInt(3) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(5) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(60) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootRavager(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Ravager)) {
            if (random.nextInt(5) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 3));
            if (random.nextInt(8) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(65) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootShulker(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Shulker)) {
            if (random.nextInt(5) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 3));
            if (random.nextInt(8) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(65) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootSilverfish(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Silverfish)) {
            if (random.nextInt(4) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 3));
            if (random.nextInt(15) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootSkeleton(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Skeleton)) {
            if (random.nextInt(5) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 3));
            if (random.nextInt(16) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(75) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootSlime(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player
                && (event.getEntity() instanceof Slime && !(event.getEntity() instanceof MagmaCube))) {
            if (random.nextInt(9) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 3));
            if (random.nextInt(11) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(15) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.GREEN_JELLY.get(), 1));
            if (random.nextInt(65) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_JELLY.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootSpider(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Spider)) {
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 3));
            if (random.nextInt(10) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootStray(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Stray)) {
            if (random.nextInt(7) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 3));
            if (random.nextInt(12) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootVindicator(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Vindicator)) {
            if (random.nextInt(2) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 3));
            if (random.nextInt(5) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(25) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootWitch(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Witch)) {
            if (random.nextInt(2) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(4) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(20) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootWitherSkeleton(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof WitherSkeleton)) {
            if (random.nextInt(3) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(40) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootZombieVillager(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof ZombieVillager)) {
            if (random.nextInt(7) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(12) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootZombifiedPig(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof ZombifiedPiglin)) {
            if (random.nextInt(6) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(13) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
            if (random.nextInt(70) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
        }
    }

    @SubscribeEvent
    public static void customLootZombie(LivingDropsEvent event) {
        Random random = new Random();

        if (event.getSource().getEntity() instanceof Player && (event.getEntity() instanceof Zombie)) {
            if (random.nextInt(7) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RUPEE.get(), 4));
            if (random.nextInt(17) == 0)
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        }
    }
}
