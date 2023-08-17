package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.registries.EffectInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class MagicCape extends Item {
    public static final float MANA_COST = 0.058F;
    public MagicCape(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack capeStack = player.getItemInHand(hand);
        boolean hasMana = MagicProvider.hasMagic(player, MANA_COST);
        boolean hasEffect = player.hasEffect(EffectInit.CLOAKED.get());

        if (hasEffect) {
            player.removeEffect(EffectInit.CLOAKED.get());
            player.getCooldowns().addCooldown(this, 8);
            //addSmokeParticles(player);
            player.playSound(SoundInit.MAGIC_CAPE_OFF.get(), 1f, 1f);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, capeStack);
        } else if (hasMana) {
            player.getCooldowns().addCooldown(this, 8);
            player.addEffect(new MobEffectInstance(EffectInit.CLOAKED.get(), Integer.MAX_VALUE, 0, false, false, true));
            player.playSound(SoundInit.MAGIC_CAPE_OFF.get(), 1f, 1f);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, capeStack);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS.FAIL, capeStack);
    }

    //TODO, Random random causes a crash, not sure why.
    /*private void addSmokeParticles(Player player) {
        Random random = (Random) player.level().random;

        for (int i = 0; i < 45; i++) {
            double particleX = player.getX() + (random.nextBoolean() ? -1 : 1) * Math.pow(random.nextFloat(), 2) * 2;
            double particleY = player.getY() + random.nextFloat() * 3 - 2;
            double particleZ = player.getZ() + (random.nextBoolean() ? -1 : 1) * Math.pow(random.nextFloat(), 2) * 2;
            player.level().addParticle(ParticleTypes.SMOKE, particleX, particleY, particleZ, 0, 0.105D, 0);
        }
    }*/

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Allows you to slip through many obstacles easier").withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal("Grants invincibility & invisibility").withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.literal("Right-click to use").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.literal("Uses Magic on use").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Invisible doesn't work on other players [WIP]").withStyle(ChatFormatting.DARK_RED));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
