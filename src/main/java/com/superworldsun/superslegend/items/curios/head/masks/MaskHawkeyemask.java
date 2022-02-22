package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaskHawkeyemask extends Item implements ICurioItem
{
	public MaskHawkeyemask(Properties properties) {
		super(properties);
	}
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "A Mask often used by snipers"));
	}
	
	@SubscribeEvent
	public static void onFovUpdate(FOVUpdateEvent event)
	{
		boolean hasMask = event.getEntity().getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.MASK_HAWKEYEMASK.get();
		boolean usingBow = event.getEntity().getUseItem().getItem() instanceof BowItem;
		
		if (hasMask && usingBow)
		{
			event.setNewfov(event.getFov() - 3.0F);
		}
	}
}