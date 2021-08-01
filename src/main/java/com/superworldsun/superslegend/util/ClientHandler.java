package com.superworldsun.superslegend.util;


import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.ClawshotItem;
import com.superworldsun.superslegend.items.HookshotItem;
import com.superworldsun.superslegend.items.LongshotItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

import static com.superworldsun.superslegend.items.ClawshotItem.spriteC;
import static com.superworldsun.superslegend.items.HookshotItem.sprite;
import static com.superworldsun.superslegend.items.LongshotItem.spriteL;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {
    /**
     * All this is to be able to change the Sprite of the Hooks. We are making the items predicate.
     * Here you can tell when to change the Sprite.
     * In the .json of the models you can see how the model changes.
     */
    public static void setupClient() {
    registerFishingRodModelProperties(ItemInit.HOOKSHOT.get());
    registerFishingRodModelProperties(ItemInit.LONGSHOT.get());
    registerFishingRodModelProperties(ItemInit.CLAWSHOT.get());

    }

    public static void registerFishingRodModelProperties(Item hookshot) {

        ItemModelsProperties.register(hookshot, new ResourceLocation("cast"), (heldStack, world, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                boolean isMainhand = livingEntity.getMainHandItem() == heldStack;
                boolean isOffHand = livingEntity.getOffhandItem() == heldStack;
                if (livingEntity.getMainHandItem().getItem() instanceof HookshotItem || livingEntity.getMainHandItem().getItem() instanceof LongshotItem || livingEntity.getMainHandItem().getItem() instanceof ClawshotItem) {
                    isOffHand = false;
                }
                return (isMainhand || isOffHand) && livingEntity instanceof PlayerEntity && (sprite || spriteL || spriteC) ? 1.0F : 0.0F;
            }
        });
    }
}