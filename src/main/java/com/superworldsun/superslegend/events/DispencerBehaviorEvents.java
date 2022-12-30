package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD)
public class DispencerBehaviorEvents {
	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		DispenserBlock.registerBehavior(ItemInit.FIRE_ARROW.get(), createArrowDispenceBehavior(EntityTypeInit.FIRE_ARROW));
		DispenserBlock.registerBehavior(ItemInit.ICE_ARROW.get(), createArrowDispenceBehavior(EntityTypeInit.ICE_ARROW));
		DispenserBlock.registerBehavior(ItemInit.SHOCK_ARROW.get(), createArrowDispenceBehavior(EntityTypeInit.SHOCK_ARROW));
		DispenserBlock.registerBehavior(ItemInit.ANCIENT_ARROW.get(), createArrowDispenceBehavior(EntityTypeInit.ANCIENT_ARROW));
		DispenserBlock.registerBehavior(ItemInit.SILVER_ARROW.get(), createArrowDispenceBehavior(EntityTypeInit.SILVER_ARROW));
	}

	private static <A extends AbstractArrowEntity> ProjectileDispenseBehavior createArrowDispenceBehavior(RegistryObject<EntityType<A>> entityTypeObject) {
		return new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World world, IPosition position, ItemStack stack) {
				A arrow = entityTypeObject.get().create(world);
				arrow.setPos(position.x(), position.y(), position.z());
				arrow.pickup = PickupStatus.ALLOWED;
				return arrow;
			}
		};
	}
}
