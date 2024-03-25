package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.entities.projectiles.bombs.BombEntity;
import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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

public class BombItem extends NonEnchantItem {
    public BombItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack bombStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                BombEntity bomb = new BombEntity(EntityTypeInit.BOMB.get(), level);
                bomb.setPos(player.getX(), player.getY(), player.getZ());
                level.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                level.addFreshEntity(bomb);
            } else {
                BombEntity bombEntity = new BombEntity(player, level);
                float pitch = 0;
                float throwingForce = 0.7F;
                bombEntity.shootFromRotation(player, player.xRotO, player.yRotO, pitch, throwingForce, 0.9F);
                level.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                level.addFreshEntity(bombEntity);
            }
            if (!player.isCreative()) {
                bombStack.shrink(1);
            }
        }
        return InteractionResultHolder.consume(bombStack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Overpower your enemies with an explosive blast").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Bombs can be used to destroy walls and obstacles").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal("Right-Click to throw Bomb").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("Sneak+Right-Click to Drop Bomb").withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
