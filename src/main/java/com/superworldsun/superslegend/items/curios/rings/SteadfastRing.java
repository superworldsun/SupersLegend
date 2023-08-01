package com.superworldsun.superslegend.items.curios.rings;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.RingItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class SteadfastRing extends RingItem {
    public SteadfastRing(Properties properties) {
        super(new Properties());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        UUID knockbackResistanceId = UUID.fromString("72ee71a7-a9c3-4d36-82dd-40b416def38b");
        map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(knockbackResistanceId, "Curio knockback resistance", 0.4f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Get knocked back less").withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}