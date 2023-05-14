package com.superworldsun.superslegend.items.armors;

import java.util.HashMap;
import java.util.Map;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.MagicArmorModel;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MagicArmor extends NonEnchantArmor
{
	private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();

	//TODO make it so when the player has 0 rupees it changes the texture of the armor
	public MagicArmor(EquipmentSlotType slot, Properties properties)
	{
		super(ArmourInit.MAGIC, slot, properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "Magic Armor"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants invicibility from rupee"));
		list.add(new StringTextComponent(TextFormatting.RED + "Slows user when rupee are depleted"));
	}

	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	@Override
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
	{
		if (!MODELS_CACHE.containsKey(armorSlot))
		{
			MODELS_CACHE.put(armorSlot, new MagicArmorModel(armorSlot));
		}

		return (M) MODELS_CACHE.get(armorSlot);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return SupersLegendMain.MOD_ID + ":textures/models/armor/magic_armor.png";
	}

	//When the player takes damage it takes 12 rupees from the players inventory
	//TODO Add some sound effect for the damage
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntity();
			boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().getItem() == ItemInit.MAGIC_ARMOR_CAP.get();
			boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getItem() == ItemInit.MAGIC_ARMOR_TUNIC.get();
			boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().getItem() == ItemInit.MAGIC_ARMOR_LEGGINGS.get();
			boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().getItem() == ItemInit.MAGIC_ARMOR_BOOTS.get();
			if (isHelmetOn && isChestplateOn && isLeggingsOn && isBootsOn) {
				int rupeesTaken = 0;
				for (int i = 0; i < player.inventory.getContainerSize() && rupeesTaken < 12; i++) {
					ItemStack armorStack = player.inventory.getItem(i);
					if (armorStack.getItem().getItem() == ItemInit.RUPEE.get()) {
						int stackSize = armorStack.getCount();
						if (stackSize >= 12 - rupeesTaken) {
							armorStack.shrink(12 - rupeesTaken);
							rupeesTaken = 12;
						} else {
							armorStack.setCount(0);
							rupeesTaken += stackSize;
						}
					}
				}
			}
		}
	}

	//Slowly takes a rupee per 10 ticks to give invincibility, when no rupees the player becomes slow and cant walk.
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (!world.isClientSide) {
			boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().getItem() == ItemInit.MAGIC_ARMOR_CAP.get();
			boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getItem() == ItemInit.MAGIC_ARMOR_TUNIC.get();
			boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().getItem() == ItemInit.MAGIC_ARMOR_LEGGINGS.get();
			boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().getItem() == ItemInit.MAGIC_ARMOR_BOOTS.get();
			if (isHelmetOn & isChestplateOn & isLeggingsOn & isBootsOn) {
				EffectInstance effect = player.getEffect(Effects.DAMAGE_RESISTANCE);
				if (effect != null && effect.getAmplifier() == 100)
					player.addEffect(new EffectInstance(Effect.byId(11), 14, 99, false, false));
				else {
					boolean hasRupees = false;
					for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
						ItemStack armorStack = player.inventory.getItem(i);
						if (armorStack.getItem().getItem() == ItemInit.RUPEE.get()) {
							armorStack.shrink(1);
							player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 10, 100, false, false, false));
							hasRupees = true;
							break;
						}
					}
					if (!hasRupees) {
						player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10, 3, false, false, false));
					}
				}
			}
		}
	}


}