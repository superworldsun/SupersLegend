package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GibdoMask extends Item implements ICurioItem
{
	public GibdoMask(Properties properties) {
		super(properties);
	}
	
	@SubscribeEvent
	public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event)
	{
		if (event.getTarget() == null)
		{
			return;
		}
		
		if (!isEntityAffected(event.getEntityLiving()))
		{
			return;
		}
		
		// Only works for mobs
		if (!(event.getEntityLiving() instanceof MobEntity))
		{
			return;
		}
		
		// Reset target if target has mask equipped
		ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_DEKUMASK.get(), event.getTarget()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		if(!stack0.isEmpty()) {
			((MobEntity) event.getEntityLiving()).setTarget(null);
		}
	}
	
	private static boolean isEntityAffected(LivingEntity entity)
	{
		return entity.getMobType() == CreatureAttribute.UNDEAD && entity.getType() != EntityType.WITHER && entity.getType() != EntityType.PHANTOM
				&& !EntityTypeTags.SKELETONS.contains(entity.getType());
	}
}
