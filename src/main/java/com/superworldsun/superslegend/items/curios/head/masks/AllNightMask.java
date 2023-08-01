package com.superworldsun.superslegend.items.curios.head.masks;

import com.mojang.blaze3d.shaders.Effect;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.awt.*;
import java.util.List;

public class AllNightMask extends Item implements ICurioItem {
    public AllNightMask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        Player player = (Player) livingEntity;
        //TODO, dont know how to check if world.isClientSide atm
        if (player.isAlive()) {
            ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_ALLNIGHTMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            if (!stack0.isEmpty()) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 230, 0, false, false));
            }
        }

        if (player.isSleeping())
        {
            player.stopSleeping();
            //TODO, display client message
            //player.displayClientMessage(new TextComponent(ChatFormatting.GRAY + "You feel restless"), true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Cant sleep huh?").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.literal("Grants night vision").withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
