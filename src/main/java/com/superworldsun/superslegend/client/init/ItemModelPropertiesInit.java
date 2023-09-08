package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemModelPropertiesInit {
    public static void addCustomItemProperties(){
        makeBow(ItemInit.FAIRY_BOW.get());
        makeBow(ItemInit.HEROS_BOW.get());
        makeBow(ItemInit.LYNEL_BOW_X3.get());
        makeBow(ItemInit.LYNEL_BOW_X5.get());

        //makeShield(ItemInit.DEKU_SHIELD.get());
    }

    private static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() -
                        p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }

    //TODO, fix shields so they can render their blocking model
    /*private static void makeShield(Item item) {
        ItemProperties.register(Items.SHIELD, new ResourceLocation("blocking"), (p_174575_, p_174576_, p_174577_, p_174578_) -> {
            return p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F;
        });
    }*/
}
