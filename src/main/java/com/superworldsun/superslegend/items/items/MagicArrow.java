package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.mana.ManaHelper;
import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public abstract class MagicArrow extends ArrowItem {
	private static final float MANA_COST = 4F;

	public MagicArrow() {
		super(new Item.Properties().tab(ItemGroupInit.RESOURCES).stacksTo(1));
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, PlayerEntity player) {
		return true;
	}

	@Override
	public AbstractArrowEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
		boolean isPlayer = shooter instanceof PlayerEntity;

		if (!isPlayer) {
			return new ArrowEntity(world, shooter);
		}

		PlayerEntity player = (PlayerEntity) shooter;
		boolean hasMana = ManaHelper.hasMana(player, MANA_COST);

		if (hasMana) {
			ManaHelper.spendMana(player, MANA_COST);
			return createMagicArrow(world, stack, shooter);
		} else {
			return new ArrowEntity(world, shooter);
		}
	}

	@SubscribeEvent
	public static void onArrowNock(ArrowNockEvent event) {
		if (!(event.getEntity() instanceof PlayerEntity)) {
			return;
		}

		PlayerEntity player = (PlayerEntity) event.getEntity();
		ItemStack projectile = event.getEntityLiving().getProjectile(event.getBow());

		if (projectile.getItem() instanceof MagicArrow) {
			boolean hasRegularArrows = player.inventory.contains(new ItemStack(Items.ARROW));
			boolean isCreative = player.isCreative();

			if (!isCreative && !hasRegularArrows) {
				event.setAction(new ActionResult<ItemStack>(ActionResultType.FAIL, event.getBow()));
			}
		}
	}

	@SubscribeEvent
	public static void onArrowLoose(ArrowLooseEvent event) {
		ItemStack arrowsStack = event.getEntityLiving().getProjectile(event.getBow());
		boolean isPlayerCreative = event.getPlayer().isCreative();
		boolean isShootingMagicArrows = arrowsStack.getItem() instanceof MagicArrow;

		if (!isPlayerCreative && isShootingMagicArrows) {
			spendRegularArrows(event);
		}
	}

	private static void spendRegularArrows(ArrowLooseEvent event) {
		int regularArrowsSlot = event.getPlayer().inventory.findSlotMatchingUnusedItem(new ItemStack(Items.ARROW));
		ItemStack regularArrowsStack = event.getPlayer().inventory.getItem(regularArrowsSlot);
		regularArrowsStack.shrink(1);
	}

	protected abstract AbstractArrowEntity createMagicArrow(World world, ItemStack stack, LivingEntity shooter);
}
