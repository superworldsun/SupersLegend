package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class NayrusLove extends Item {
    float MANA_COST = 8.0F;
    public NayrusLove(Properties builder) {
        super(builder);
    }

    // TODO DD CHARGE BEFORE USE

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean hasMana = MagicProvider.hasMagic(player, MANA_COST);
        if (hasMana) {

            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.NAYRUS_LOVE_CAST.get(), SoundSource.PLAYERS, 1f, 1f);

            MagicProvider.spendMagic(player, MANA_COST);

            //TODO cant get particle to work
            Random rand = new Random(); // Create a new instance of java.util.Random
            for (int i = 0; i < 45; i++) {
                player.getCommandSenderWorld().addParticle(ParticleTypes.TOTEM_OF_UNDYING,
                        player.getX() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                        player.getY() + rand.nextFloat() * 3 - 2,
                        player.getZ() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                        0, 0.105D, 0);
            }

            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300, 0, false, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 99, false, false, false));
            player.getCooldowns().addCooldown(this, 100);
        } else {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR.get(), SoundSource.PLAYERS, 1f, 1f);

        }

        return InteractionResultHolder.pass(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Grants invincibility").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Right-click to use for 14 seconds of invincibility").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
            tooltip.add(Component.literal("Uses Magic on use").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

}