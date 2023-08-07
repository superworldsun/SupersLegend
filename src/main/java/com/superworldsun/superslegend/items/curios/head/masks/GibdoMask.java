package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GibdoMask extends Item implements ICurioItem {
    public GibdoMask(Properties pProperties) {
        super(pProperties);
    }

    //TODO, dosent work/not finished
    /*@SubscribeEvent
    public static void onLivingSetAttackTarget(LivingChangeTargetEvent event) {
        if (event.getTarget() == null) {
            return;
        }

        if (!isEntityAffected(event.getEntity())) {
            return;
        }

        // Only works for mobs
        if (!(event.getEntity() instanceof Entity)) {
            return;
        }

        // Reset target if target has mask equipped
        ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GIBDOMASK.get(), event.getTarget()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (!stack0.isEmpty()) {
            //((Entity) event.getEntity()).setTarget(null);
        }
    }*/

    /*private static boolean isEntityAffected(LivingEntity entity) {
        return entity.getMobType() == CreatureAttribute.UNDEAD && entity.getType() != EntityType.WITHER && entity.getType() != EntityType.PHANTOM
                && !EntityTypeTags.SKELETONS.contains(entity.getType());
    }*/

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("A mask that will allow you to blend in").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.literal("with fellow undead monsters").withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
