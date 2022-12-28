package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.HoverBootsModel;
import com.superworldsun.superslegend.interfaces.IHoveringEntity;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.util.PlayerUtil;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class HoverBoots extends NonEnchantArmor
{
	private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();
	
	public HoverBoots(Properties properties)
	{
		super(ArmourInit.HOVER_BOOTS, EquipmentSlotType.FEET, properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
	{
		if (!MODELS_CACHE.containsKey(armorSlot))
		{
			MODELS_CACHE.put(armorSlot, new HoverBootsModel());
		}
		
		return (M) MODELS_CACHE.get(armorSlot);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type)
	{
		return SupersLegendMain.MOD_ID + ":textures/models/armor/hover_boots.png";
	}

	//true = no sprinting
	//false = must be sprinting to hover
	static boolean disableSprintingChecks = true;

	@SubscribeEvent
	public static void onJump(LivingEvent.LivingJumpEvent event) {
		if (event.getEntityLiving() instanceof PlayerEntity) {
			IHoveringEntity entity = (IHoveringEntity)event.getEntityLiving();
			entity.setJumpedFromBlock(true);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		// Prevent applying changes 2 times per tick
		if (event.phase == Phase.START)
		{
			return;
		}

		// Only if we have boots
		if (event.player.getItemBySlot(EquipmentSlotType.FEET).getItem() != ItemInit.HOVER_BOOTS.get())
		{
			return;
		}
		
		IHoveringEntity hoveringPlayer = (IHoveringEntity) event.player;

		// Second variable (checkHeight) is responsible, how many blocks under the player's foot
		// must be clear (not solid) to active hover "mode"
		if (!PlayerUtil.solidGroundCheck(event.player, 1)) {
			boolean ok = false;

			if (disableSprintingChecks) {
				ok = true;
			} else {
				if (event.player.isSprinting()) {
					ok = true;
				}
			}

			if (ok && !hoveringPlayer.jumpedFromBlock()) {
				// 20 ticks are 1 seconds
				if (hoveringPlayer.increaseHoverTime() < 22)
				{
					double motionY = event.player.getDeltaMovement().y;

					if (motionY < 0 && event.player.getY() <= hoveringPlayer.getHoverHeight() && !event.player.isOnGround())
					{
						// Prevent movement downwards
						event.player.setDeltaMovement(event.player.getDeltaMovement().x, 0, event.player.getDeltaMovement().z);
						// Reset fall distance, we are not falling
						event.player.fallDistance = 0;

						Vector3d v = event.player.getDeltaMovement();
						event.player.setDeltaMovement(v.x, v.y * -0.1D, v.z);

						if(event.player.tickCount % 2 == 0) {
							event.player.playSound(SoundInit.HOVER_BOOTS.get(), 1F, 1F);
						}
					}

					// Prevent falling
					if (event.player.getY() < hoveringPlayer.getHoverHeight())
					{
						event.player.setBoundingBox(event.player.getBoundingBox().move(0, hoveringPlayer.getHoverHeight() - event.player.getY(), 0));
					}
				}
			}
		}

		// If sprinting checks are enabled, checks if the player stops sprinting
		if (!event.player.isSprinting() && !disableSprintingChecks){
			hoveringPlayer.setHoverTime(100);
		}
		
		// If we are on ground
		if (event.player.isOnGround())
		{
			// Reset timer for boots
			hoveringPlayer.setHoverTime(0);
			hoveringPlayer.setHoverHeight((int) Math.round(event.player.getY()));
			hoveringPlayer.setJumpedFromBlock(false);
		}
	}
}