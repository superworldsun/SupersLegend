package com.superworldsun.superslegend.util.events;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class EntityEventHandler
{
	// @SubscribeEvent
	// public void onpigjump(LivingEvent.LivingJumpEvent event)
	// {
	// if (event.getEntityLiving() instanceof PigEntity)
	// {
	// event.getEntityLiving().addEffect(new EffectInstance(Effects.LEVITATION, 1000, 5));
	// event.getEntityLiving().playSound(SoundInit.PIGFLY.get(), 0.25F, 1.0F);
	// }
	// }
	
	// Dosent work yet
	// @SubscribeEvent
	// public static void interModEnqueue(InterModEnqueueEvent e)
	// {
	// InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.MODIFY_TYPE, () -> new SlotTypeMessage.Builder("ring").size(1));
	// }
	
	// @SubscribeEvent
	// public void cooldown(LivingEntityUseItemEvent.Start event)
	// {
	// if (event.getEntityLiving() instanceof PlayerEntity)
	// {
	// PlayerEntity player = (PlayerEntity) event.getEntityLiving();
	// // The above player-check is better than using Minecraft.getInstance().player, as it ensures multiplayer compatibility
	// if (PotionUtils.getPotion(event.getItem()) == Potion.byName("minecraft:night_vision"))
	// {
	// event.setCanceled(true);
	// }
	// }
	// }
	
	// BROKEN, DOSENT WORK PROPERLY, AND CRASHES GAME WHEN OPENED FROM BUILT JAR
	// @SubscribeEvent
	// public void addToInventory(GuiScreenEvent event)
	// {
	// if (event.getGui() instanceof InventoryScreen)
	// {
	// Button openSelectButton = new Button((event.getGui().width - ((InventoryScreen) event.getGui()).getXSize()) / 2 + 2,
	// (event.getGui().height - ((InventoryScreen) event.getGui()).getYSize()) / 2 - 16, 16, 16, new StringTextComponent("SL"),
	// a -> NetworkDispatcher.networkChannel.sendToServer(new SelectInteractionMessage(0, true)));
	// ((List<Widget>) ObfuscationReflectionHelper.getPrivateValue(Screen.class, event.getGui(), "buttons")).add(openSelectButton);
	// ((List<IGuiEventListener>) ObfuscationReflectionHelper.getPrivateValue(Screen.class, event.getGui(), "children")).add(openSelectButton);
	// }
	// }
}
