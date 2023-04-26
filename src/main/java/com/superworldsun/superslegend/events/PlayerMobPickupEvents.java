package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.*;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class PlayerMobPickupEvents
{

    private static final List<EntityType<?>> allowedTypes0 = new ArrayList<>();

    static {
        //No strength required
        allowedTypes0.add(EntityType.VEX);
        allowedTypes0.add(EntityType.SILVERFISH);
        allowedTypes0.add(EntityType.ENDERMITE);
        allowedTypes0.add(EntityType.BAT);
        allowedTypes0.add(EntityType.COD);
        allowedTypes0.add(EntityType.TROPICAL_FISH);
        allowedTypes0.add(EntityType.PUFFERFISH);
        allowedTypes0.add(EntityType.SALMON);
        allowedTypes0.add(EntityType.BEE);
        allowedTypes0.add(EntityType.CHICKEN);
        allowedTypes0.add(EntityType.PARROT);
        allowedTypes0.add(EntityType.RABBIT);
        allowedTypes0.add(EntityType.CAT);
        allowedTypes0.add(EntityType.OCELOT);
        allowedTypes0.add(EntityType.VEX);
        allowedTypes0.add(EntityType.FOX);
        allowedTypes0.add(EntityType.SNOW_GOLEM);
        allowedTypes0.add(EntityType.PHANTOM);
    }

    private static final List<EntityType<?>> allowedTypes1 = new ArrayList<>();

    static {
        //With Goron Bracelet
        allowedTypes1.add(EntityType.CAVE_SPIDER);
        allowedTypes1.add(EntityType.SLIME);
        allowedTypes1.add(EntityType.MAGMA_CUBE);
        allowedTypes1.add(EntityType.ENDERMAN);
        allowedTypes1.add(EntityType.SQUID);
        allowedTypes1.add(EntityType.WOLF);
        allowedTypes1.add(EntityType.SPIDER);
        allowedTypes1.add(EntityType.BLAZE);
        allowedTypes1.add(EntityType.DOLPHIN);
        allowedTypes1.add(EntityType.STRAY);
        allowedTypes1.add(EntityType.ZOMBIE_VILLAGER);
        allowedTypes1.add(EntityType.WANDERING_TRADER);
        allowedTypes1.add(EntityType.VILLAGER);
        allowedTypes1.add(EntityType.CREEPER);
        allowedTypes1.add(EntityType.WITCH);
        allowedTypes1.add(EntityType.ZOMBIE);
    }

    private static final List<EntityType<?>> allowedTypes2 = new ArrayList<>();

    static {
        //With Silver Gauntlets
        allowedTypes2.add(EntityType.PLAYER);
        allowedTypes2.add(EntityType.EVOKER);
        allowedTypes2.add(EntityType.PILLAGER);
        allowedTypes2.add(EntityType.VINDICATOR);
        allowedTypes2.add(EntityType.HUSK);
        allowedTypes2.add(EntityType.SKELETON_HORSE);
        allowedTypes2.add(EntityType.ZOMBIFIED_PIGLIN);
        allowedTypes2.add(EntityType.GUARDIAN);
        allowedTypes2.add(EntityType.SHEEP);
        allowedTypes2.add(EntityType.PANDA);
        allowedTypes2.add(EntityType.PIGLIN);
        allowedTypes2.add(EntityType.SHULKER);
        allowedTypes2.add(EntityType.TURTLE);
        allowedTypes2.add(EntityType.WITHER_SKELETON);
        allowedTypes2.add(EntityType.LLAMA);
        allowedTypes2.add(EntityType.TRADER_LLAMA);
        allowedTypes2.add(EntityType.DONKEY);
    }

    //When player Sneak+Right clicks a mob it will pick it up and then Right Click to drop
    @SubscribeEvent
    public static void onEntityInteractSpecific(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        if (player.getPassengers().isEmpty() && player.isCrouching() && allowedTypes0.contains(target.getType())) {
            LivingEntity rider = (LivingEntity) target;
            if (!rider.isVehicle()) {
                rider.startRiding(player, true);
            }
        }
        else if (!player.getPassengers().isEmpty() && !player.isCrouching()) {
            if (allowedTypes0.contains(target.getType())) {
                LivingEntity rider = (LivingEntity) target;
                rider.stopRiding();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        ItemStack level1 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GORONS_BRACELET.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack level2 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.SILVER_GAUNTLETS.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        ItemStack level3 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GOLDEN_GAUNTLETS.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if (level1.isEmpty()) {
            if (!player.getPassengers().isEmpty()) {
                for (Entity entity : player.getPassengers()) {
                    if (allowedTypes1.contains(entity.getType())) {
                        entity.stopRiding();
                    }
                }
            }
        }
        if (!level1.isEmpty() || level2.isEmpty()) {
            if (!player.getPassengers().isEmpty()) {
                for (Entity entity : player.getPassengers()) {
                    if (allowedTypes2.contains(entity.getType()) || allowedTypes1.contains(entity.getType())) {
                        entity.stopRiding();
                    }
                }
            }
        }
        if (level3.isEmpty()) {
            if (!player.getPassengers().isEmpty()) {
                for (Entity entity : player.getPassengers()) {
                    if (!allowedTypes0.contains(entity.getType())) {
                        entity.stopRiding();
                    }
                }
            }
        }
    }


    //Makes it so when you have a mob held you cant attack it or anything else
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.getPassengers().isEmpty()) {
            event.setCanceled(true);
        }
    }

    //makes it so the right click function isnt used when you right click, dosent work the best
    @SubscribeEvent
    public static void onPlayerInteractItem(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        if (!player.getPassengers().isEmpty()) {
            event.setCanceled(true);
        }
    }

    //Checks if the player is holding a chicken, if they do then fall slowly
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            boolean hasChickenPassenger = player.getPassengers().stream().anyMatch(passenger -> passenger instanceof ChickenEntity);
            if (hasChickenPassenger) {
                player.fallDistance = 0F;

                // slows fall speed
                if (player.getDeltaMovement().y < -0.1)
                {
                    Vector3d movement = player.getDeltaMovement();
                    player.setDeltaMovement(new Vector3d(movement.x, -0.08, movement.z));
                }
            }
        }
    }

    //removes players first person hand when holding mob
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && !player.getPassengers().isEmpty()) {
            event.setCanceled(true);
        }
    }

}
