package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.mana.ManaProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class MaskZoramask extends Item implements ICurioItem {

    float manaCost = 0.03F;

    public MaskZoramask(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "The face of a Zora"));
        list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "You can swim with the grace of a Zora"));
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        World world = livingEntity.level;
        PlayerEntity player = (PlayerEntity) livingEntity;
        boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;


        if (player.isSwimming() && hasMana && player.isAlive()) {
            ManaProvider.get(player).spendMana(manaCost);
            player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 4, 0, true, true, true));
        }

    }


}