package com.superworldsun.superslegend.items.curios.hands;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class GoronBracelet extends StrengthHandItem {
	public GoronBracelet() {
		super(new Properties(), 1);
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