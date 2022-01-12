package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class BlueRing extends Item implements ICurioItem {
    private static final ResourceLocation REDRING_TEXTURE  = new ResourceLocation(SupersLegendMain.MOD_ID,
            "textures/entity/amulet.png");
    private Object model;

    public BlueRing(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
            if (event.getSource() == DamageSource.GENERIC) {
                event.setAmount(event.getAmount() / 2);
            }
    }


            @Deprecated
            public ICurio.DropRule getDropRule(LivingEntity livingEntity) {
                return ICurio.DropRule.ALWAYS_KEEP;
            }

            @Deprecated
            public ICurio.SoundInfo getEquipSound(SlotContext slotContext) {
                return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
            }
}