package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.arrows.BombArrowEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ArrowBomb extends ArrowItem {


    public ArrowBomb(Properties p_i48531_1_) {
        super(p_i48531_1_);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        BombArrowEntity arrow = new BombArrowEntity(worldIn, shooter);
        return arrow;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("An arrow with an explosive tip."));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}
