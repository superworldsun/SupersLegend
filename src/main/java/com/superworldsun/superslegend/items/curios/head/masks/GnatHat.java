package com.superworldsun.superslegend.items.curios.head.masks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;

public class GnatHat extends Item implements ICurioItem {
    public GnatHat(Properties pProperties) {
        super(pProperties);
    }

    //TODO, would be funny to make it so animals such as foxes and chickens would attack the player when small, low prio.

    //TODO Make sure stats are fine tuned
    @Override
    public void onEquip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.onEquip(identifier, index, livingEntity, stack);
        ScaleData height = ScaleTypes.HEIGHT.getScaleData(livingEntity);
        height.setScaleTickDelay(20);
        height.setTargetScale(0.1f);
        ScaleData width = ScaleTypes.WIDTH.getScaleData(livingEntity);
        width.setScaleTickDelay(20);
        width.setTargetScale(0.1f);
        ScaleData reach = ScaleTypes.BLOCK_REACH.getScaleData(livingEntity);
        reach.setScaleTickDelay(20);
        reach.setTargetScale(0.4f);
        ScaleData ereach = ScaleTypes.ENTITY_REACH.getScaleData(livingEntity);
        ereach.setScaleTickDelay(20);
        ereach.setTargetScale(0.4f);
        ScaleData motion = ScaleTypes.MOTION.getScaleData(livingEntity);
        motion.setScaleTickDelay(20);
        motion.setTargetScale(0.25f);
        ScaleData jump = ScaleTypes.JUMP_HEIGHT.getScaleData(livingEntity);
        jump.setScaleTickDelay(20);
        jump.setTargetScale(1f);
        ScaleData step = ScaleTypes.STEP_HEIGHT.getScaleData(livingEntity);
        step.setScaleTickDelay(20);
        step.setTargetScale(1f);
        ScaleData mining = ScaleTypes.MINING_SPEED.getScaleData(livingEntity);
        mining.setScaleTickDelay(20);
        mining.setTargetScale(0.1f);
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.onUnequip(identifier, index, livingEntity, stack);
        ScaleData height = ScaleTypes.HEIGHT.getScaleData(livingEntity);
        height.setTargetScale(1f);
        ScaleData width = ScaleTypes.WIDTH.getScaleData(livingEntity);
        width.setTargetScale(1f);
        ScaleData reach = ScaleTypes.BLOCK_REACH.getScaleData(livingEntity);
        reach.setTargetScale(1f);
        ScaleData ereach = ScaleTypes.ENTITY_REACH.getScaleData(livingEntity);
        ereach.setTargetScale(1f);
        ScaleData motion = ScaleTypes.MOTION.getScaleData(livingEntity);
        motion.setTargetScale(1f);
        ScaleData jump = ScaleTypes.JUMP_HEIGHT.getScaleData(livingEntity);
        jump.setTargetScale(1f);
        ScaleData step = ScaleTypes.STEP_HEIGHT.getScaleData(livingEntity);
        step.setTargetScale(1f);
        ScaleData mining = ScaleTypes.MINING_SPEED.getScaleData(livingEntity);
        mining.setTargetScale(1f);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("Shrink down to the size of a Gnat").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.literal("You wont be as strong shrunk down").withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
