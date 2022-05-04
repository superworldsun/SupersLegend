package com.superworldsun.superslegend.items.curios.rings;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.RingItem;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class SteadfastRing extends RingItem
{
	public SteadfastRing()
	{
		super(new Item.Properties());
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
		UUID knockbackResistanceId = UUID.fromString("72ee71a7-a9c3-4d36-82dd-40b416def38b");
		map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(knockbackResistanceId, "Curio knockback resistance", 0.4f, AttributeModifier.Operation.ADDITION));
		return map;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Get knocked back less"));
	}
}