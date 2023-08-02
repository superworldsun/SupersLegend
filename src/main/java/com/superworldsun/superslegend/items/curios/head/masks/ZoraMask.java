package com.superworldsun.superslegend.items.curios.head.masks;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.shaders.Effect;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ZoraMask extends Item implements ICurioItem
{
    private static final UUID ZORA_WATER_MODIFIER_ID = UUID.fromString("734af1f7-e76b-47f0-ba52-5a1a12a9edd2");
    float manaCost = 0.03F;

    public ZoraMask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        Player player = (Player) livingEntity;

        if(player.isUnderWater())
        {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 0, false, false));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        UUID swimSpeedId = UUID.fromString("a1376302-aa12-4076-a7a5-f1e4c96b4bca");
        map.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(swimSpeedId, "Zora swim speed", 1.5f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        // Prevent applying changes 2 times per tick
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }

        // Only if we have the mask
        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_ZORAMASK.get(), event.player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if (!maskStack.isEmpty())
        {
            if (event.player.isInWater())
            {
                if (event.player.isSwimming())
                {
                    // +60%
                    addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_WATER_MODIFIER_ID, 0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                }
                else
                {
                    removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_WATER_MODIFIER_ID);
                }
            }
        }
        else
        {
            removeModifier(event.player, Attributes.MOVEMENT_SPEED, ZORA_WATER_MODIFIER_ID);
            removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_WATER_MODIFIER_ID);
        }
    }


    private static void removeModifier(Player player, Attribute attribute, UUID id)
    {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null)
        {
            attributeInstance.removeModifier(modifier);
        }
    }

    private static void addOrReplaceModifier(Player player, Attribute attribute, UUID id, float amount, AttributeModifier.Operation operation)
    {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null && modifier.getAmount() != amount)
        {
            attributeInstance.removeModifier(modifier);
            modifier = new AttributeModifier(id, id.toString(), amount, operation);
        }
        else if (modifier == null)
        {
            modifier = new AttributeModifier(id, id.toString(), amount, operation);
        }

        if (modifier != null && !attributeInstance.hasModifier(modifier))
        {
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("The face of a Zora").withStyle(ChatFormatting.DARK_BLUE));
        tooltip.add(Component.literal("You can swim with the grace of a Zora").withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
