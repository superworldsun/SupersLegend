package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.api.DamageReductionItem;
import com.superworldsun.superslegend.items.customclass.RingItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class BlueRing extends RingItem implements DamageReductionItem {
    public BlueRing(Properties properties) {
        super(new Item.Properties());
    }

    @Override
    public boolean canReduceDamage(DamageSource damage) {
        return true;
    }

    @Override
    public float getDamageReduction() {
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Damage taken is 1/2").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}