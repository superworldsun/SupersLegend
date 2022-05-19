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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class SwimmersRing extends RingItem
{
	public SwimmersRing()
	{
		super(new Item.Properties());
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
		UUID swimSpeedId = UUID.fromString("4a6e5d8a-a02c-46ff-ae9f-cb8e68ad2c0d");
		map.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(swimSpeedId, "Curio swim speed", 0.5f, AttributeModifier.Operation.ADDITION));
		return map;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.BLUE + "Swimming speed increased"));
	}
}