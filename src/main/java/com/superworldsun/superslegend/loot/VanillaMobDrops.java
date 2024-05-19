package com.superworldsun.superslegend.loot;

import java.util.Collection;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class VanillaMobDrops {

    @SubscribeEvent
    public static void customLootMonsterEntity(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        ResourceLocation entityId = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof Player player) {
            RandomSource random = player.getRandom();
            // This should make it so any type of monster from other mods should also drop
            // rupees occasionally
            if (!entityId.getNamespace().equals("minecraft"))
                dropModdedMonsterLoot(event, entity, random);
            if (entity instanceof Monster)
                dropMonsterLoot(event, entity, random);
            if (entity instanceof Blaze)
                dropBlazeLoot(event, entity, random);
            if (entity instanceof CaveSpider)
                dropCaveSpiderLoot(event, entity, random);
            if (entity instanceof Creeper)
                dropCreeperLoot(event, entity, random);
            if (entity instanceof Drowned)
                dropDrownedLoot(event, entity, random);
            if (entity instanceof ElderGuardian)
                dropElderGuardianLoot(event, entity, random);
            if (entity instanceof EnderMan)
                dropEndermanLoot(event, entity, random);
            if (entity instanceof Endermite)
                dropEndermiteLoot(event, entity, random);
            if (entity instanceof Evoker)
                dropEvokerLoot(event, entity, random);
            if (entity instanceof Ghast)
                dropGhastLoot(event, entity, random);
            if (entity instanceof Guardian)
                dropGuardianLoot(event, entity, random);
            if (entity instanceof Husk)
                dropHuskLoot(event, entity, random);
            if (entity instanceof MagmaCube)
                dropMagmaCubeLoot(event, entity, random);
            if (entity instanceof Phantom)
                dropPhantomLoot(event, entity, random);
            if (entity instanceof Pillager)
                dropPillagerLoot(event, entity, random);
            if (entity instanceof Ravager)
                dropRavagerLoot(event, entity, random);
            if (entity instanceof Shulker)
                dropShulkerLoot(event, entity, random);
            if (entity instanceof Silverfish)
                dropSilverfishLoot(event, entity, random);
            if (entity instanceof Skeleton)
                dropSkeletonLoot(event, entity, random);
            if (entity instanceof Slime && !(entity instanceof MagmaCube))
                dropSlimeLoot(event, entity, random);
            if (entity instanceof Spider)
                dropSpiderLoot(event, entity, random);
            if (entity instanceof Stray)
                dropStrayLoot(event, entity, random);
            if (entity instanceof Vindicator)
                dropVindicatorLoot(event, entity, random);
            if (entity instanceof Witch)
                dropWitchLoot(event, entity, random);
            if (entity instanceof WitherSkeleton)
                dropWitherSkeletonLoot(event, entity, random);
            if (entity instanceof ZombieVillager)
                dropZombieVillagerLoot(event, entity, random);
            if (entity instanceof ZombifiedPiglin)
                dropZombifiedPiglinLoot(event, entity, random);
            if (entity instanceof Zombie)
                dropZombieLoot(event, entity, random);
        }
    }

    private static void dropZombieLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(7) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(17) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropZombifiedPiglinLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(13) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(70) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
    }

    private static void dropZombieVillagerLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(7) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(12) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropWitherSkeletonLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(3) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(40) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
    }

    private static void dropWitchLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(2) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(4) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(20) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
    }

    private static void dropVindicatorLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(2) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 3));
        if (random.nextInt(5) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(25) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
    }

    private static void dropStrayLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(7) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 3));
        if (random.nextInt(12) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropSpiderLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 3));
        if (random.nextInt(10) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropSlimeLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(9) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 3));
        if (random.nextInt(11) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(15) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.GREEN_JELLY.get(), 1));
        if (random.nextInt(65) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_JELLY.get(), 1));
    }

    private static void dropSkeletonLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(5) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 3));
        if (random.nextInt(16) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(75) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
    }

    private static void dropSilverfishLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(4) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 3));
        if (random.nextInt(15) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropShulkerLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(5) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 3));
        if (random.nextInt(8) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(65) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
    }

    private static void dropRavagerLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(5) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 3));
        if (random.nextInt(8) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(65) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
    }

    private static void dropPillagerLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(3) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(5) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(60) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
    }

    private static void dropPhantomLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(5) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(10) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(45) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
    }

    private static void dropMagmaCubeLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(8) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(10) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(90) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
        if (random.nextInt(20) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RED_JELLY.get(), 1));
        if (random.nextInt(60) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_JELLY.get(), 1));
    }

    private static void dropHuskLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(12) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropGuardianLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(4) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(55) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
    }

    private static void dropGhastLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(2) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(4) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(35) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
    }

    private static void dropEvokerLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(3) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(25) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_WISDOM_SHARD.get(), 1));
    }

    private static void dropEndermiteLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(3) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(7) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropEndermanLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(3) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), 4));
        if (random.nextInt(8) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropElderGuardianLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(14) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RED_RUPEE.get(), 1));
        if (random.nextInt(1) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
    }

    private static void dropDrownedLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
        if (random.nextInt(14) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(70) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
    }

    private static void dropCreeperLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(4) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
        if (random.nextInt(7) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void dropCaveSpiderLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
        if (random.nextInt(14) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(50) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_COURAGE_SHARD.get(), 1));
    }

    private static void dropBlazeLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(6) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
        if (random.nextInt(10) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
        if (random.nextInt(60) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.TRIFORCE_POWER_SHARD.get(), 1));
    }

    private static void dropMonsterLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(45) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.UNAPPRAISED_RING.get(), random.nextInt(3)));
    }

    private static void dropModdedMonsterLoot(LivingDropsEvent event, LivingEntity entity, RandomSource random) {
        if (random.nextInt(7) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.RUPEE.get(), random.nextInt(3)));
        if (random.nextInt(14) == 0)
            addDrop(event.getDrops(), entity, new ItemStack(ItemInit.BLUE_RUPEE.get(), 1));
    }

    private static void addDrop(Collection<ItemEntity> items, LivingEntity entity, ItemStack stack) {
        items.add(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack));
    }
}