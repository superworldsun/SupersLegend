package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class ArmorRingL2 extends Item implements ICurioItem {

    public ArmorRingL2(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        //Check if is player doing the damage.
        if (event.getSource().getDirectEntity() instanceof PlayerEntity) {

            //Get Player.
            PlayerEntity player = (PlayerEntity) event.getSource().getDirectEntity();

            //Get the Ring as an ItemStack
            ItemStack stack =
                    CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.ARMOR_RING_L2.get(), player).map(
                            ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            //Check if player is wearing it. Check if Sword Item.
            if (!stack.isEmpty() && player.getMainHandItem().getItem() instanceof SwordItem) {
                event.setAmount(event.getAmount() / 2.0f);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        //Check if it is the Player who takes damage.
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            //Get the Ring as an ItemStack
            ItemStack stack =
                    CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.ARMOR_RING_L2.get(), player).map(
                            ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            //Check if player is wearing it.
            if (!stack.isEmpty()) {
                //Don't do a Check to see if the damage comes from DamageSource.GENERIC. I don't know what mob/block uses the "GENERIC" damage in the game so I normally do a (event.getSource != DamageSource.*Type*) if I don't want it to take less damage from something in particular.
                event.setAmount(event.getAmount() / 2.0f);
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

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.BLUE + "2x Sword Attack & Damage Reduced"));
    }
}