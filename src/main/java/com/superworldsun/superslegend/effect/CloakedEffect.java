package com.superworldsun.superslegend.effect;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.items.item.MagicCape;
import com.superworldsun.superslegend.registries.EffectInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class CloakedEffect extends MobEffect {
    public CloakedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB02828);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity instanceof Player)) {
            return;
        }

        Player player = (Player) livingEntity;
        boolean hasMana = MagicProvider.hasMagic(player, MagicCape.MANA_COST);
        boolean hasCape = player.getInventory().contains(new ItemStack(ItemInit.MAGIC_CAPE.get()));

        if (!hasCape || !hasMana) {
            player.removeEffect(this);
            return;
        }

        MagicProvider.spendMagic(player, MagicCape.MANA_COST);
    }

    @Override
    public boolean isDurationEffectTick(int tick, int effectAmplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event) {
        if (event.getEntity().hasEffect(EffectInit.CLOAKED.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingSetAttackTarget(LivingChangeTargetEvent event) {
        boolean isTargetCloked = event.getEntity() != null && event.getEntity().hasEffect(EffectInit.CLOAKED.get());

        if (isTargetCloked) {
            Entity entity = event.getEntity();
            entity.startSeenByPlayer(null);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
        if (event.getEntity().hasEffect(EffectInit.CLOAKED.get())) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean isClientPlayerUsingLens = minecraft.player.isUsingItem()
                    && minecraft.player.getItemInHand(minecraft.player.getUsedItemHand()).getItem() == ItemInit.LENS_OF_TRUTH.get();

            if (!isClientPlayerUsingLens) {
                event.setCanceled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onHandRender(RenderHandEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean isClientPlayerCloaked = minecraft.player.hasEffect(EffectInit.CLOAKED.get());
        if (isClientPlayerCloaked) {
            event.setCanceled(true);
        }
    }
}