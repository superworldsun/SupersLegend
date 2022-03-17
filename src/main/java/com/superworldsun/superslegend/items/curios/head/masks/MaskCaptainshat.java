package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.ITameableSkeleton;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaskCaptainshat extends Item implements ICurioItem {
    public MaskCaptainshat(Properties properties) {
        super(properties);
    }

    @SubscribeEvent
    public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getTarget() == null) {
            return;
        }

        // Only works for skeletons
        if (!EntityTypeTags.SKELETONS.contains(event.getEntity().getType())) {
            return;
        }

        // Only works for mobs
        if (!(event.getEntity() instanceof MobEntity)) {
            return;
        }

        // Only if target has hat equipped
        ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_CAPTAINSHAT.get(), event.getTarget()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (stack0.isEmpty()) {
            return;
        }

        // Reset target
        ((MobEntity) event.getEntity()).setTarget(null);

        // Everything below works only for tameable skeletons
        if (!(event.getEntity() instanceof ITameableSkeleton)) {
            return;
        }

        ITameableSkeleton tameableSkeleton = (ITameableSkeleton) event.getEntity();

        // If has no owner, set owner
        if (!tameableSkeleton.hasOwner()) {
            tameableSkeleton.setOwner(event.getTarget());
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingUpdateEvent event) {
        // Everything below works only for tameable skeletons
        if (!(event.getEntity() instanceof ITameableSkeleton)) {
            return;
        }

        ITameableSkeleton tameableSkeleton = (ITameableSkeleton) event.getEntity();

        // If owner has no captain's hat, set no owner
        if(tameableSkeleton.getOwner() != null){
        ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_CAPTAINSHAT.get(), tameableSkeleton.getOwner()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (tameableSkeleton.hasOwner() && stack0.isEmpty()) {

            tameableSkeleton.setOwner(null);
        }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.YELLOW + "Even through death, a leader stands"));
    }
}