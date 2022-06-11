package com.superworldsun.superslegend.items.armors;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.ModelRocsCape;
import com.superworldsun.superslegend.interfaces.IJumpingEntity;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.DoubleJumpMessage;
import com.superworldsun.superslegend.registries.ArmourInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class RocsCape extends NonEnchantArmor
{
	@OnlyIn(Dist.CLIENT)
	private Object modelCache;
	
	public RocsCape()
	{
		super(ArmourInit.ROCSCAPE, EquipmentSlotType.CHEST, new Item.Properties().tab(SupersLegendMain.APPAREL));
	}
	
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
	{
		if (!(modelCache instanceof ModelRocsCape))
		{
			modelCache = new ModelRocsCape(0);
		}
		
		return (A) modelCache;
	}
	
	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity)
	{
		return stack.getOrCreateTag().getBoolean("canFly");
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{
		boolean canFly = stack.getOrCreateTag().getBoolean("canFly");
		
		if (!canFly && player.isOnGround())
		{
			stack.getOrCreateTag().putBoolean("canFly", true);
		}
	}
	
	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
	{
		boolean canFly = flightTicks < 40;
		
		if (!canFly)
		{
			stack.getOrCreateTag().putBoolean("canFly", false);
		}
		
		return canFly;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "Wearing this will grant better ground mobility"));
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event)
	{
		if (event.getEntityLiving().getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof RocsCape)
		{
			event.setDistance(event.getDistance() - 4F);
		}
	}
	
	@SubscribeEvent
	public static void onLivingJump(LivingJumpEvent event)
	{
		if (event.getEntityLiving().getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof RocsCape)
		{
			Vector3d jumpBonusVector = new Vector3d(0, 0.1D, 0);
			event.getEntityLiving().setDeltaMovement(event.getEntityLiving().getDeltaMovement().add(jumpBonusVector));
		}
	}
	
	private static boolean canDoubleJump;
	private static boolean hasReleasedJumpKey;
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		Minecraft client = Minecraft.getInstance();
		ClientPlayerEntity player = client.player;
		
		if (event.phase == TickEvent.Phase.END && player != null && player.input != null)
		{
			if ((player.isOnGround() || player.onClimbable()) && !player.isInWater())
			{
				hasReleasedJumpKey = false;
				canDoubleJump = true;
			}
			else if (!player.input.jumping)
			{
				hasReleasedJumpKey = true;
			}
			else if (!player.abilities.flying && canDoubleJump && hasReleasedJumpKey)
			{
				canDoubleJump = false;
				
				if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof RocsCape)
				{
					NetworkDispatcher.networkChannel.sendToServer(new DoubleJumpMessage());
					((IJumpingEntity) player).doubleJump();
				}
			}
		}
	}
}