package com.superworldsun.superslegend.items.items;

import java.util.List;

import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.RocksFeatherMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RocsFeather extends Item {
    public RocsFeather(Properties properties) {
        super(properties);
        MinecraftForge.EVENT_BUS.register(new DoubleJumpHandler());
    }


    public void jump(PlayerEntity player) {
        player.fallDistance = 0;

        double upwardsMotion = 0.5;
        if (player.hasEffect(Effects.JUMP)) {
            upwardsMotion += 0.1 * (player.getEffect(Effects.JUMP).getAmplifier() + 1);
        }
        upwardsMotion *= 1.5D;

        Vector3d motion = player.getDeltaMovement();
        double motionMultiplier = player.isSprinting() ? 0.5D : 0;
        float direction = (float) (player.getYHeadRot() * Math.PI / 180);
        player.setDeltaMovement(player.getDeltaMovement().add(
                -Math.sin(direction) * motionMultiplier,
                upwardsMotion - motion.y,
                Math.cos(direction) * motionMultiplier)
        );

        player.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(player);

        player.awardStat(Stats.JUMP);
        if (player.isSprinting()) {
            player.causeFoodExhaustion(0.2F);
        } else {
            player.causeFoodExhaustion(0.05F);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.WHITE + "Holding this will grant better ground mobility"));
    }

    private class DoubleJumpHandler {

        @OnlyIn(Dist.CLIENT)
        private boolean canDoubleJump;

        @OnlyIn(Dist.CLIENT)
        private boolean hasReleasedJumpKey;

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        @SuppressWarnings("unused")
        public void onClientTick(TickEvent.ClientTickEvent event) {
            ClientPlayerEntity player = Minecraft.getInstance().player;

            if (event.phase == TickEvent.Phase.END && player != null && player.input != null) {
                if ((player.isOnGround() || player.onClimbable()) && !player.isInWater()) {
                    hasReleasedJumpKey = false;
                    canDoubleJump = true;
                } else if (!player.input.jumping) {
                    hasReleasedJumpKey = true;
                } else if (!player.abilities.flying && canDoubleJump && hasReleasedJumpKey) {
                    canDoubleJump = false;
                    if (player.getMainHandItem().getItem().equals(RocsFeather.this)) {
                        NetworkDispatcher.networkChannel.sendToServer(new RocksFeatherMessage());
                        jump(player);
                    }
                }
            }
        }
    }

}