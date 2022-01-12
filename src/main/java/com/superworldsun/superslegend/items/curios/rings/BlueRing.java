package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
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
    public static void onLivingHurt(LivingHurtEvent event) {
        //Check if it is the Player who takes damage.
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            //Get the Ring as an ItemStack
            ItemStack stack =
                    CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.BLUE_RING.get(), player).map(
                            ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            //Check if player is wearing it.
            if (!stack.isEmpty()) {
                //Don't do a Check to see if the damage comes from DamageSource.GENERIC. I don't know what mob/block uses the "GENERIC" damage in the game so I normally do a (event.getSource != DamageSource.*Type*) if I don't want it to take less damage from something in particular.
                event.setAmount(event.getAmount() / 2);
            }
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