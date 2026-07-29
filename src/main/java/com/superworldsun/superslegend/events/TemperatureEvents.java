package com.superworldsun.superslegend.events;

import java.util.UUID;

import com.google.common.util.concurrent.AtomicDouble;
import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.AttributeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class TemperatureEvents {
    private static final float UNDERGROUND_TEMPERATURE = 0.5F;
    private static final float NETHER_TEMPERATURE = 2.0F;

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        addColdResistance(event, Items.LEATHER_HELMET, 0.05F, EquipmentSlot.HEAD);
        addColdResistance(event, Items.LEATHER_CHESTPLATE, 0.15F, EquipmentSlot.CHEST);
        addColdResistance(event, Items.LEATHER_LEGGINGS, 0.1F, EquipmentSlot.LEGS);
        addColdResistance(event, Items.LEATHER_BOOTS, 0.05F, EquipmentSlot.FEET);
        // TODO:mod items should add their modifiers in their classes
        addHeatResistance(event, ItemInit.DESERT_VOE_HEADBAND.get(), 0.15F, EquipmentSlot.HEAD);
        addHeatResistance(event, ItemInit.DESERT_VOE_SPAULDER.get(), 0.4F, EquipmentSlot.CHEST);
        addHeatResistance(event, ItemInit.DESERT_VOE_TROUSERS.get(), 0.3F, EquipmentSlot.LEGS);
        addHeatResistance(event, ItemInit.DESERT_VOE_BOOTS.get(), 0.15F, EquipmentSlot.FEET);
        addColdResistance(event, ItemInit.SNOWQUILL_HEADDRESS.get(), 0.15F, EquipmentSlot.HEAD);
        addColdResistance(event, ItemInit.SNOWQUILL_TUNIC.get(), 0.4F, EquipmentSlot.CHEST);
        addColdResistance(event, ItemInit.SNOWQUILL_TROUSERS.get(), 0.3F, EquipmentSlot.LEGS);
        addColdResistance(event, ItemInit.SNOWQUILL_BOOTS.get(), 0.15F, EquipmentSlot.FEET);
        addHellHeatResistance(event, ItemInit.FLAMEBREAKER_HELMET.get(), 0.5F, EquipmentSlot.HEAD);
        addHellHeatResistance(event, ItemInit.FLAMEBREAKER_TUNIC.get(), 0.5F, EquipmentSlot.CHEST);
        addHellHeatResistance(event, ItemInit.FLAMEBREAKER_LEGGINGS.get(), 0.5F, EquipmentSlot.LEGS);
        addHellHeatResistance(event, ItemInit.FLAMEBREAKER_BOOTS.get(), 0.5F, EquipmentSlot.FEET);
        addHellHeatResistance(event, ItemInit.GORON_TUNIC.get(), 1.0F, EquipmentSlot.CHEST);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.START
                || event.player.level().isClientSide
                || !Config.isTemperatureEnabled()
                || event.player.isCreative()
                || event.player.isSpectator()) {
            return;
        }

        Player player = event.player;
        Level level = player.level();
        boolean isInNether = level.dimension() == Level.NETHER;
        float temperature = getTemperature(player);
        float damageFrequency = isInNether ? 1.0F : 10.0F;
        double coldResistance = player.getAttributeValue(AttributeInit.COLD_RESISTANCE.get()) - 1;
        double heatResistance = getHeatResistance(player);

        if (player.tickCount % (20 * damageFrequency) != 0) {
            return;
        }

        DamageSources damageSources = level.damageSources();

        if (coldResistance < 1.0D && temperature < 0.0F) {
            float dangerousColdTemperature = coldResistance < 0.0D ? 0.5F * (float)Math.abs(coldResistance)
                    : coldResistance > 0.0D ? -(float)coldResistance : 0.0F;

            if (temperature < dangerousColdTemperature) {
                player.hurt(damageSources.freeze(), 1.0F);
            }
        } else if (heatResistance < 1.0D && temperature > 1.0F) {
            float dangerousHeatTemperature = 1.0F + (heatResistance < 0.0D ? -0.5F * (float)Math.abs(heatResistance)
                    : heatResistance > 0.0D ? (float)heatResistance : 0.0F);

            if (temperature > dangerousHeatTemperature) {
                if (isInNether) {
                    player.setSecondsOnFire(1);
                    player.hurt(damageSources.onFire(), 1.0F);
                } else if (!isInNether) {
                    player.hurt(damageSources.onFire(), 1.0F);
                }
            }
        }
    }

    public static float getHeatResistance(Player player) {
        if (player.level().dimension() == Level.NETHER) {
            return (float) (player.hasEffect(MobEffects.FIRE_RESISTANCE) ? 1.0F : player.getAttributeValue(AttributeInit.HELL_HEAT_RESISTANCE.get()) - 1);
        } else {
            return (float) (player.getAttributeValue(AttributeInit.HEAT_RESISTANCE.get()) - 1);
        }
    }

    public static float getTemperature(Player player) {
        if (player.level().dimension() == Level.NETHER) {
            return NETHER_TEMPERATURE;
        }

        BlockPos playerPos = player.blockPosition();

        if (playerPos.getY() <= 40) {
            return UNDERGROUND_TEMPERATURE;
        }

        AtomicDouble temperature = getTemperatureAround(player);
        addTimeTemperatureBonus(player, temperature);
        addUndergroundTemperatureBonus(player, temperature);
        return temperature.floatValue();
    }

    private static void addUndergroundTemperatureBonus(Player player, AtomicDouble temperature) {
        if (player.blockPosition().getY() < 64) {
            float temperatureChange = (player.blockPosition().getY() - 40) / 24.0F;
            temperature.set((temperature.get() - UNDERGROUND_TEMPERATURE) * temperatureChange + UNDERGROUND_TEMPERATURE);
        }
    }

    private static void addTimeTemperatureBonus(Player player, AtomicDouble temperature) {
        long dayTime = player.level().getDayTime() % 24000;
        float maximumChange = 0.2F;
        float temperatureBonus = Mth.cos((float) ((dayTime - 7000) / 12000F * Math.PI)) * maximumChange;
        boolean inShade = !player.level().canSeeSky(player.blockPosition());
        boolean canAddTimeTemperatureBonus = temperatureBonus <= 0 || !inShade;

        if (canAddTimeTemperatureBonus) {
            temperature.addAndGet(temperatureBonus);
        }
    }

    private static AtomicDouble getTemperatureAround(Player player) {
        int calculationRange = 8;
        AtomicDouble temperature = new AtomicDouble();

        BlockPos.betweenClosed(player.blockPosition().offset(-calculationRange, 0, -calculationRange),
                player.blockPosition().offset(calculationRange, 0, calculationRange)).forEach(blockPos -> {
            Biome currentBiome = player.level().getBiome(blockPos).value();
            temperature.addAndGet(currentBiome.getModifiedClimateSettings().temperature());
        });

        int blocksCount = (calculationRange * 2 + 1) * (calculationRange * 2 + 1);
        temperature.set(temperature.get() / blocksCount);
        return temperature;
    }

    private static void addColdResistance(ItemAttributeModifierEvent event, Item item, float resistance, net.minecraft.world.entity.EquipmentSlot slotType) {
        if (event.getItemStack().getItem() == item && event.getSlotType() == slotType) {
            UUID modifierId = getAttributeModifierIdForSlot(slotType);
            event.addModifier(AttributeInit.COLD_RESISTANCE.get(), new AttributeModifier(modifierId, "Hardcoded Modifier", resistance, Operation.MULTIPLY_BASE));
        }
    }

    private static void addHeatResistance(ItemAttributeModifierEvent event, Item item, float resistance, net.minecraft.world.entity.EquipmentSlot slotType) {
        if (event.getItemStack().getItem() == item && event.getSlotType() == slotType) {
            UUID modifierId = getAttributeModifierIdForSlot(slotType);
            event.addModifier(AttributeInit.HEAT_RESISTANCE.get(), new AttributeModifier(modifierId, "Hardcoded Modifier", resistance, Operation.MULTIPLY_BASE));
        }
    }

    private static void addHellHeatResistance(ItemAttributeModifierEvent event, Item item, float resistance, net.minecraft.world.entity.EquipmentSlot slotType) {
        if (event.getItemStack().getItem() == item && event.getSlotType() == slotType) {
            UUID modifierId = getAttributeModifierIdForSlot(slotType);
            event.addModifier(AttributeInit.HELL_HEAT_RESISTANCE.get(), new AttributeModifier(modifierId, "Hardcoded Modifier", resistance, Operation.MULTIPLY_BASE));
        }
    }

    private static UUID getAttributeModifierIdForSlot(net.minecraft.world.entity.EquipmentSlot slotType) {
        switch (slotType) {
            case CHEST:
                return UUID.fromString("18232d9b-f2ab-4cea-b08e-c8fa5fd2e998");
            case FEET:
                return UUID.fromString("8a8bbcf6-5dd6-4061-92da-24fab53857ec");
            case HEAD:
                return UUID.fromString("99e134ee-24ef-464d-b633-f020161de704");
            case LEGS:
                return UUID.fromString("015147dc-5e50-4195-8c85-d8953cd9e38a");
            case OFFHAND:
                return UUID.fromString("103f5c43-7149-43da-b71b-d0bcbada5076");
            default:
                return UUID.fromString("163fad25-7e59-4e4b-bb77-0f85d1b8bd57");
        }
    }
}