package com.superworldsun.superslegend.items.curios.hands;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.HandItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class GoronBracelet extends HandItem
{
	public GoronBracelet()
	{
		super(new Properties());
	}

	private static final List<EntityType<?>> allowedTypes = new ArrayList<>();

	static {
		allowedTypes.add(EntityType.CAVE_SPIDER);
		allowedTypes.add(EntityType.SLIME);
		allowedTypes.add(EntityType.MAGMA_CUBE);
		allowedTypes.add(EntityType.ENDERMAN);
		allowedTypes.add(EntityType.SQUID);
		allowedTypes.add(EntityType.WOLF);
		allowedTypes.add(EntityType.SPIDER);
		allowedTypes.add(EntityType.BLAZE);
		allowedTypes.add(EntityType.DOLPHIN);
		allowedTypes.add(EntityType.STRAY);
		allowedTypes.add(EntityType.ZOMBIE_VILLAGER);
		allowedTypes.add(EntityType.WANDERING_TRADER);
		allowedTypes.add(EntityType.VILLAGER);
		allowedTypes.add(EntityType.CREEPER);
		allowedTypes.add(EntityType.WITCH);
		allowedTypes.add(EntityType.ZOMBIE);
	}

	@SubscribeEvent
	public static void onEntityInteractSpecific(PlayerInteractEvent.EntityInteract event) {
		PlayerEntity player = event.getPlayer();
		Entity target = event.getTarget();
		if (event.getEntityLiving() instanceof PlayerEntity) {
			// Get the HandItem as an ItemStack
			ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GORONS_BRACELET.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			// Check if player is wearing it.
			if (!stack.isEmpty())
			{
				if (player.getPassengers().isEmpty() && player.isCrouching() && allowedTypes.contains(target.getType())) {
					LivingEntity rider = (LivingEntity) target;
					if (!rider.isVehicle()) {
						rider.startRiding(player, true);
					}
				}
				else if (!player.getPassengers().isEmpty() && !player.isCrouching()) {
					if (allowedTypes.contains(target.getType())) {
						LivingEntity rider = (LivingEntity) target;
						rider.stopRiding();
					}
				}
			}
		}
	}

	/*@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		ItemStack stack1 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GORONS_BRACELET.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

		if (stack1.isEmpty()) {
			if (!player.getPassengers().isEmpty()) {
				for (Entity entity : player.getPassengers()) {
					if (allowedTypes.contains(entity.getType())) {
						entity.stopRiding();
					}
				}
			}
		}
	}*/

	/*public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
		LivingEntity livingEntity = slotContext.getWearer();
		if (livingEntity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) livingEntity;
			Entity target = player.getVehicle();
			if (target != null) {
				if (allowedTypes.contains(target.getType())) {
					LivingEntity rider = (LivingEntity) target;
					rider.stopRiding();
				}
			} else {
				System.out.println("Player has no passengers.");
			}
		}
	}*/

	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		//list.add(new StringTextComponent(TextFormatting.BLUE + "Damage taken is 1/2"));
	}
}