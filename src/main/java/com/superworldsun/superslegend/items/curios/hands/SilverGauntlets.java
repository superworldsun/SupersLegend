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
public class SilverGauntlets extends HandItem
{
	public SilverGauntlets()
	{
		super(new Properties());
	}

	private static final List<EntityType<?>> allowedTypes = new ArrayList<>();

	static {
		//With Brace
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
		//With Silver Gauntlets
		allowedTypes.add(EntityType.PLAYER);
		allowedTypes.add(EntityType.EVOKER);
		allowedTypes.add(EntityType.PILLAGER);
		allowedTypes.add(EntityType.VINDICATOR);
		allowedTypes.add(EntityType.HUSK);
		allowedTypes.add(EntityType.SKELETON_HORSE);
		allowedTypes.add(EntityType.ZOMBIFIED_PIGLIN);
		allowedTypes.add(EntityType.GUARDIAN);
		allowedTypes.add(EntityType.SHEEP);
		allowedTypes.add(EntityType.PANDA);
		allowedTypes.add(EntityType.PIGLIN);
		allowedTypes.add(EntityType.SHULKER);
		allowedTypes.add(EntityType.TURTLE);
		allowedTypes.add(EntityType.WITHER_SKELETON);
		allowedTypes.add(EntityType.LLAMA);
		allowedTypes.add(EntityType.TRADER_LLAMA);
		allowedTypes.add(EntityType.DONKEY);
	}

	@SubscribeEvent
	public static void onEntityInteractSpecific(PlayerInteractEvent.EntityInteract event) {
		PlayerEntity player = event.getPlayer();
		Entity target = event.getTarget();
		if (event.getEntityLiving() instanceof PlayerEntity) {
			// Get the Ring as an ItemStack
			ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.SILVER_GAUNTLETS.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

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
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		//list.add(new StringTextComponent(TextFormatting.BLUE + "Damage taken is 1/2"));
	}
}