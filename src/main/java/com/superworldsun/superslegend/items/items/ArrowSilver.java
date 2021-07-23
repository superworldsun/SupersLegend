package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.arrows.ShockArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.SilverArrowEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ArrowSilver extends ArrowItem {


    public ArrowSilver(Properties p_i48531_1_) {
        super(p_i48531_1_);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        SilverArrowEntity arrow = new SilverArrowEntity(worldIn, shooter);
        return arrow;
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("An arrow that fights the undead"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}
