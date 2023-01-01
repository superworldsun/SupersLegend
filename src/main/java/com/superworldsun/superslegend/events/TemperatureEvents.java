package com.superworldsun.superslegend.events;

import java.util.UUID;

import com.google.common.util.concurrent.AtomicDouble;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.config.SupersLegendConfig;
import com.superworldsun.superslegend.registries.AttributeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class TemperatureEvents {
	private static final DamageSource HEAT_DAMAGE = new DamageSource("heat").bypassArmor();
	private static final DamageSource COLD_DAMAGE = new DamageSource("cold").bypassArmor();
	private static final float UNDERGROUND_TEMPERATURE = 0.5F;
	private static final float NETHER_TEMPERATURE = 2.0F;

	@SubscribeEvent
	public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
		addColdResistance(event, Items.LEATHER_HELMET, 0.05F, EquipmentSlotType.HEAD);
		addColdResistance(event, Items.LEATHER_CHESTPLATE, 0.15F, EquipmentSlotType.CHEST);
		addColdResistance(event, Items.LEATHER_LEGGINGS, 0.1F, EquipmentSlotType.LEGS);
		addColdResistance(event, Items.LEATHER_BOOTS, 0.05F, EquipmentSlotType.FEET);
		// TODO:mod items should add their modifiers in their classes
		addHeatResistance(event, ItemInit.DESERT_VOE_HEADBAND.get(), 0.15F, EquipmentSlotType.HEAD);
		addHeatResistance(event, ItemInit.DESERT_VOE_SPAULDER.get(), 0.4F, EquipmentSlotType.CHEST);
		addHeatResistance(event, ItemInit.DESERT_VOE_TROUSERS.get(), 0.3F, EquipmentSlotType.LEGS);
		addHeatResistance(event, ItemInit.DESERT_VOE_BOOTS.get(), 0.15F, EquipmentSlotType.FEET);
		addColdResistance(event, ItemInit.SNOWQUILL_HEADDRESS.get(), 0.15F, EquipmentSlotType.HEAD);
		addColdResistance(event, ItemInit.SNOWQUILL_TUNIC.get(), 0.4F, EquipmentSlotType.CHEST);
		addColdResistance(event, ItemInit.SNOWQUILL_TROUSERS.get(), 0.3F, EquipmentSlotType.LEGS);
		addColdResistance(event, ItemInit.SNOWQUILL_BOOTS.get(), 0.15F, EquipmentSlotType.FEET);
		addHellHeatResistance(event, ItemInit.FLAMEBREAKER_HELMET.get(), 0.5F, EquipmentSlotType.HEAD);
		addHellHeatResistance(event, ItemInit.FLAMEBREAKER_TUNIC.get(), 0.5F, EquipmentSlotType.CHEST);
		addHellHeatResistance(event, ItemInit.FLAMEBREAKER_LEGGINGS.get(), 0.5F, EquipmentSlotType.LEGS);
		addHellHeatResistance(event, ItemInit.FLAMEBREAKER_BOOTS.get(), 0.5F, EquipmentSlotType.FEET);
		addHellHeatResistance(event, ItemInit.GORON_TUNIC.get(), 1.0F, EquipmentSlotType.CHEST);
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.phase == Phase.START) {
			return;
		}

		if (!SupersLegendConfig.getInstance().temperature()) {
			return;
		}

		boolean isInNether = event.player.level.dimension() == World.NETHER;
		float temperature = getTemperature(event.player);
		float damageFrequency = isInNether ? 1.0F : 10.0F;
		double coldResistance = event.player.getAttributeValue(AttributeInit.COLD_RESISTANCE.get()) - 1;
		double heatResistance = getHeatResistance(event.player);

		if (event.player.tickCount % (20 * damageFrequency) != 0) {
			return;
		}

		if (coldResistance < 1.0D && temperature < 0.0F) {
			float dangerousColdTemperature = 0.0F;

			if (coldResistance < 0.0D) {
				dangerousColdTemperature += 0.5D * Math.abs(coldResistance);
			} else if (coldResistance > 0.0D) {
				dangerousColdTemperature -= coldResistance;
			}

			if (temperature < dangerousColdTemperature) {
				event.player.hurt(COLD_DAMAGE, 1.0F);
			}
		} else if (heatResistance < 1.0D && temperature > 1.0F) {
			float dangerousHeatTemperature = 1.0F;

			if (heatResistance < 0.0D) {
				dangerousHeatTemperature -= 0.5D * Math.abs(coldResistance);
			} else if (heatResistance > 0.0D) {
				dangerousHeatTemperature += heatResistance;
			}

			if (temperature > dangerousHeatTemperature) {
				if (isInNether) {
					event.player.setSecondsOnFire(1);
					event.player.hurt(DamageSource.ON_FIRE, 1.0F);
				} else {
					event.player.hurt(HEAT_DAMAGE, 1.0F);
				}
			}
		}
	}

	public static float getHeatResistance(PlayerEntity player) {
		if (player.level.dimension() == World.NETHER) {
			return (float) (player.hasEffect(Effects.FIRE_RESISTANCE) ? 1.0F : player.getAttributeValue(AttributeInit.HELL_HEAT_RESISTANCE.get()) - 1);
		} else {
			return (float) (player.getAttributeValue(AttributeInit.HEAT_RESISTANCE.get()) - 1);
		}
	}

	public static float getTemperature(PlayerEntity player) {
		if (player.level.dimension() == World.NETHER) {
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

	private static void addUndergroundTemperatureBonus(PlayerEntity player, AtomicDouble temperature) {
		if (player.blockPosition().getY() < 64) {
			float temperatureChange = (player.blockPosition().getY() - 40) / 24.0F;
			temperature.set((temperature.get() - UNDERGROUND_TEMPERATURE) * temperatureChange + UNDERGROUND_TEMPERATURE);
		}
	}

	private static void addTimeTemperatureBonus(PlayerEntity player, AtomicDouble temperature) {
		long dayTime = player.level.getDayTime() % 24000;
		float maximumChange = 0.2F;
		float temperatureBonus = MathHelper.cos((float) ((dayTime - 7000) / 12000F * Math.PI)) * maximumChange;
		boolean inShade = !player.level.canSeeSky(player.blockPosition());
		boolean canAddTimeTemperatureBonus = temperatureBonus <= 0 || !inShade;

		if (canAddTimeTemperatureBonus) {
			temperature.addAndGet(temperatureBonus);
		}
	}

	private static AtomicDouble getTemperatureAround(PlayerEntity player) {
		int calculationRange = 8;
		AtomicDouble temperature = new AtomicDouble();

		BlockPos.betweenClosed(player.blockPosition().offset(-calculationRange, 0, -calculationRange),
				player.blockPosition().offset(calculationRange, 0, calculationRange)).forEach(blockPos -> {
					Biome currentBiome = player.level.getBiome(blockPos);
					temperature.addAndGet(currentBiome.getTemperature(blockPos));
				});

		int blocksCount = (calculationRange * 2 + 1) * (calculationRange * 2 + 1);
		temperature.set(temperature.get() / blocksCount);
		return temperature;
	}

	private static void addColdResistance(ItemAttributeModifierEvent event, Item item, float resistance, EquipmentSlotType slotType) {
		if (event.getItemStack().getItem() == item && event.getSlotType() == slotType) {
			UUID modifierId = getAttributeModifierIdForSlot(slotType);
			event.addModifier(AttributeInit.COLD_RESISTANCE.get(), new AttributeModifier(modifierId, "Hardcoded Modifier", resistance, Operation.MULTIPLY_BASE));
		}
	}

	private static void addHeatResistance(ItemAttributeModifierEvent event, Item item, float resistance, EquipmentSlotType slotType) {
		if (event.getItemStack().getItem() == item && event.getSlotType() == slotType) {
			UUID modifierId = getAttributeModifierIdForSlot(slotType);
			event.addModifier(AttributeInit.HEAT_RESISTANCE.get(), new AttributeModifier(modifierId, "Hardcoded Modifier", resistance, Operation.MULTIPLY_BASE));
		}
	}

	private static void addHellHeatResistance(ItemAttributeModifierEvent event, Item item, float resistance, EquipmentSlotType slotType) {
		if (event.getItemStack().getItem() == item && event.getSlotType() == slotType) {
			UUID modifierId = getAttributeModifierIdForSlot(slotType);
			event.addModifier(AttributeInit.HELL_HEAT_RESISTANCE.get(), new AttributeModifier(modifierId, "Hardcoded Modifier", resistance, Operation.MULTIPLY_BASE));
		}
	}

	private static UUID getAttributeModifierIdForSlot(EquipmentSlotType slotType) {
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
