package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.ITameableSkeleton;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaskCaptainshat extends Item implements ICurioItem
{
	public MaskCaptainshat(Properties properties) {
		super(properties);
	}
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Even through death, a leader stands"));
	}
	
	@SubscribeEvent
	public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event)
	{
		if (event.getTarget() == null)
		{
			return;
		}
		
		// Only works for skeletons
		if (!EntityTypeTags.SKELETONS.contains(event.getEntity().getType()))
		{
			return;
		}
		
		// Only works for mobs
		if (!(event.getEntity() instanceof MobEntity))
		{
			return;
		}
		
		// Only if target has hat equipped
		if (event.getTarget().getItemBySlot(EquipmentSlotType.HEAD).getItem() != ItemInit.MASK_CAPTAINSHAT.get())
		{
			return;
		}
		
		// Reset target
		((MobEntity) event.getEntity()).setTarget(null);
		
		// Everything below works only for tameable skeletons
		if (!(event.getEntity() instanceof ITameableSkeleton))
		{
			return;
		}
		
		ITameableSkeleton tameableSkeleton = (ITameableSkeleton) event.getEntity();
		
		// If has no owner, set owner
		if (!tameableSkeleton.hasOwner())
		{
			tameableSkeleton.setOwner(event.getTarget());
		}
	}
	
	@SubscribeEvent
	public static void onLivingTick(LivingUpdateEvent event)
	{
		// Everything below works only for tameable skeletons
		if (!(event.getEntity() instanceof ITameableSkeleton))
		{
			return;
		}
		
		ITameableSkeleton tameableSkeleton = (ITameableSkeleton) event.getEntity();
		
		// If owner has no captain's hat, set no owner
		if (tameableSkeleton.hasOwner() && tameableSkeleton.getOwner().getItemBySlot(EquipmentSlotType.HEAD).getItem() != ItemInit.MASK_CAPTAINSHAT.get())
		{
			tameableSkeleton.setOwner(null);
		}
	}
}