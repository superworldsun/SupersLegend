package com.superworldsun.superslegend.items.curios.rings;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.UUID;

public class RedRing extends Item implements ICurioItem {
    private static final ResourceLocation REDRING_TEXTURE  = new ResourceLocation(SupersLegendMain.MOD_ID,
            "textures/entity/amulet.png");
    private Object model;

    public RedRing(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }




            @Deprecated
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {

                if (!livingEntity.isAlive() && livingEntity.tickCount % 19 == 0) {
                    livingEntity.addEffect(new EffectInstance(Effects.DIG_SPEED, 20, 0, true, true));
                }
            }

            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                                UUID uuid) {
                Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
                atts.put(Attributes.MOVEMENT_SPEED,
                        new AttributeModifier(uuid, SupersLegendMain.MOD_ID + ":speed_bonus", 0.1,
                                AttributeModifier.Operation.MULTIPLY_TOTAL));
                atts.put(Attributes.ARMOR,
                        new AttributeModifier(uuid, SupersLegendMain.MOD_ID + ":armor_bonus", 2,
                                AttributeModifier.Operation.ADDITION));
                return atts;
            }

            @Deprecated
            public ICurio.DropRule getDropRule(LivingEntity livingEntity) {
                return ICurio.DropRule.ALWAYS_KEEP;
            }

            @Deprecated
            public ICurio.SoundInfo getEquipSound(SlotContext slotContext) {
                return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
            }

            @Deprecated
            public boolean canEquipFromUse(SlotContext slot) {
                return true;
            }


    @Deprecated
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return true;
    }
}