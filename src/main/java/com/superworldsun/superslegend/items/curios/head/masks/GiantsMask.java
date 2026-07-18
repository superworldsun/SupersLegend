package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.items.item.MagicCape;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

public class GiantsMask extends Item implements ICurioItem {
    public GiantsMask(Properties pProperties) {
        super(pProperties);
    }
    private static final float MANA_COST = 0.01F;


    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        MagicProvider.spendMagic((Player) livingEntity, MANA_COST);
        if (!(livingEntity instanceof Player)) {
            return;}
        Player player = (Player) livingEntity;
        boolean hasMagic = MagicProvider.hasMagic(player, MagicCape.MANA_COST);

        if (hasMagic)
        {
            ScaleData height = ScaleTypes.HEIGHT.getScaleData(livingEntity);
            height.setTargetScale(6.22f);
            height.setScaleTickDelay(20);
            ScaleData width = ScaleTypes.WIDTH.getScaleData(livingEntity);
            width.setTargetScale(6.22f);
            width.setScaleTickDelay(20);
            ScaleData reach = ScaleTypes.BLOCK_REACH.getScaleData(livingEntity);
            reach.setTargetScale(2f);
            reach.setScaleTickDelay(20);
            ScaleData ereach = ScaleTypes.ENTITY_REACH.getScaleData(livingEntity);
            ereach.setTargetScale(2f);
            ereach.setScaleTickDelay(20);
            ScaleData motion = ScaleTypes.MOTION.getScaleData(livingEntity);
            motion.setTargetScale(3f);
            motion.setScaleTickDelay(20);
            ScaleData jump = ScaleTypes.JUMP_HEIGHT.getScaleData(livingEntity);
            jump.setTargetScale(1.5f);
            jump.setScaleTickDelay(20);
            ScaleData fall = ScaleTypes.FALLING.getScaleData(livingEntity);
            fall.setTargetScale(0.4f);
            fall.setScaleTickDelay(20);
            ScaleData step = ScaleTypes.STEP_HEIGHT.getScaleData(livingEntity);
            step.setTargetScale(0.8f);
            step.setScaleTickDelay(20);
            ScaleData mining = ScaleTypes.MINING_SPEED.getScaleData(livingEntity);
            mining.setTargetScale(2f);
            mining.setScaleTickDelay(20);
        }
        else
        if (!hasMagic)
        {
            ScaleData height = ScaleTypes.HEIGHT.getScaleData(livingEntity);
            height.setTargetScale(1f);
            height.setScaleTickDelay(20);
            ScaleData width = ScaleTypes.WIDTH.getScaleData(livingEntity);
            width.setTargetScale(1f);
            width.setScaleTickDelay(20);
            ScaleData reach = ScaleTypes.BLOCK_REACH.getScaleData(livingEntity);
            reach.setTargetScale(1f);
            reach.setScaleTickDelay(20);
            ScaleData ereach = ScaleTypes.ENTITY_REACH.getScaleData(livingEntity);
            ereach.setTargetScale(1f);
            ereach.setScaleTickDelay(20);
            ScaleData motion = ScaleTypes.MOTION.getScaleData(livingEntity);
            motion.setTargetScale(1f);
            motion.setScaleTickDelay(20);
            ScaleData jump = ScaleTypes.JUMP_HEIGHT.getScaleData(livingEntity);
            jump.setTargetScale(1f);
            jump.setScaleTickDelay(20);
            ScaleData fall = ScaleTypes.FALLING.getScaleData(livingEntity);
            fall.setTargetScale(1f);
            fall.setScaleTickDelay(20);
            ScaleData step = ScaleTypes.STEP_HEIGHT.getScaleData(livingEntity);
            step.setTargetScale(1f);
            step.setScaleTickDelay(20);
            ScaleData mining = ScaleTypes.MINING_SPEED.getScaleData(livingEntity);
            mining.setTargetScale(1f);
            mining.setScaleTickDelay(20);
        }
    }

    //TODO Make sure stats are fine tuned
    @Override
    public void onEquip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.onEquip(identifier, index, livingEntity, stack);
        if (!(livingEntity instanceof Player)) {
            return;
        }
        Player player = (Player) livingEntity;
        boolean hasMagic = MagicProvider.hasMagic(player, MagicCape.MANA_COST);
        if (hasMagic) {
            ScaleData height = ScaleTypes.HEIGHT.getScaleData(livingEntity);
            height.setTargetScale(6.22f);
            height.setScaleTickDelay(20);
            ScaleData width = ScaleTypes.WIDTH.getScaleData(livingEntity);
            width.setTargetScale(6.22f);
            width.setScaleTickDelay(20);
            ScaleData reach = ScaleTypes.BLOCK_REACH.getScaleData(livingEntity);
            reach.setTargetScale(2f);
            reach.setScaleTickDelay(20);
            ScaleData ereach = ScaleTypes.ENTITY_REACH.getScaleData(livingEntity);
            ereach.setTargetScale(2f);
            ereach.setScaleTickDelay(20);
            ScaleData motion = ScaleTypes.MOTION.getScaleData(livingEntity);
            motion.setTargetScale(3f);
            motion.setScaleTickDelay(20);
            ScaleData jump = ScaleTypes.JUMP_HEIGHT.getScaleData(livingEntity);
            jump.setTargetScale(1.5f);
            jump.setScaleTickDelay(20);
            ScaleData fall = ScaleTypes.FALLING.getScaleData(livingEntity);
            fall.setTargetScale(0.4f);
            fall.setScaleTickDelay(20);
            ScaleData step = ScaleTypes.STEP_HEIGHT.getScaleData(livingEntity);
            step.setTargetScale(0.8f);
            step.setScaleTickDelay(20);
            ScaleData mining = ScaleTypes.MINING_SPEED.getScaleData(livingEntity);
            mining.setTargetScale(2f);
            mining.setScaleTickDelay(20);
        }
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.onUnequip(identifier, index, livingEntity, stack);

        ScaleData height = ScaleTypes.HEIGHT.getScaleData(livingEntity);
        height.setTargetScale(1f);
        height.setScaleTickDelay(20);
        ScaleData width = ScaleTypes.WIDTH.getScaleData(livingEntity);
        width.setTargetScale(1f);
        width.setScaleTickDelay(20);
        ScaleData reach = ScaleTypes.BLOCK_REACH.getScaleData(livingEntity);
        reach.setTargetScale(1f);
        reach.setScaleTickDelay(20);
        ScaleData ereach = ScaleTypes.ENTITY_REACH.getScaleData(livingEntity);
        ereach.setTargetScale(1f);
        ereach.setScaleTickDelay(20);
        ScaleData motion = ScaleTypes.MOTION.getScaleData(livingEntity);
        motion.setTargetScale(1f);
        motion.setScaleTickDelay(20);
        ScaleData jump = ScaleTypes.JUMP_HEIGHT.getScaleData(livingEntity);
        jump.setTargetScale(1f);
        jump.setScaleTickDelay(20);
        ScaleData fall = ScaleTypes.FALLING.getScaleData(livingEntity);
        fall.setTargetScale(1f);
        fall.setScaleTickDelay(20);
        ScaleData step = ScaleTypes.STEP_HEIGHT.getScaleData(livingEntity);
        step.setTargetScale(1f);
        step.setScaleTickDelay(20);
        ScaleData mining = ScaleTypes.MINING_SPEED.getScaleData(livingEntity);
        mining.setTargetScale(1f);
        mining.setScaleTickDelay(20);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("Within this mask lies the might of a giant").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Awaken the giants power and abilities").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.literal("at the cost of magic").withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
