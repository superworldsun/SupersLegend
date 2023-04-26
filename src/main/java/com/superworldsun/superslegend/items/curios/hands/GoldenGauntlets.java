package com.superworldsun.superslegend.items.curios.hands;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.HandItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class GoldenGauntlets extends HandItem
{
	public GoldenGauntlets()
	{
		super(new Properties());
	}

	//TODO make a check so that it dosent work on any boss type mob, that way players cant pick up bosses from other mods
	//Works on all mobs except ElderGuardian, Wither, EnderDragon
	@SubscribeEvent
	public static void onEntityInteractSpecific(PlayerInteractEvent.EntityInteract event) {
		PlayerEntity player = event.getPlayer();
		LivingEntity target = (LivingEntity) event.getTarget();
		// Get the Ring as an ItemStack
		ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GOLDEN_GAUNTLETS.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		// Check if player is wearing it.
		if (!stack.isEmpty()) {
			// Check if the target is not an elder guardian, wither, or ender dragon
			if (!target.getType().equals(EntityType.ELDER_GUARDIAN) &&
					!target.getType().equals(EntityType.WITHER) &&
					!target.getType().equals(EntityType.ENDER_DRAGON)) {
				if (player.getPassengers().isEmpty() && player.isCrouching()) {
					LivingEntity rider = target;
					if (!rider.isVehicle()) {
						rider.startRiding(player, true);
					}
				} else if (!player.getPassengers().isEmpty() && !player.isCrouching()) {
					LivingEntity rider = target;
					rider.stopRiding();
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