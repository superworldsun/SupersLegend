package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.entities.projectiles.arrows.PoisonArrowEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class PoisonArrowItem extends ArrowItem {
    public PoisonArrowItem(Properties properties)
    {
        super(properties.stacksTo(64));
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        PoisonArrowEntity arrow = new PoisonArrowEntity(worldIn, shooter);
        return arrow;
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("An arrow tipped with poison that makes it especially lethal."));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}

