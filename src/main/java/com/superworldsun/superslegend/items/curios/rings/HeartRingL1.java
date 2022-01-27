package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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
public class HeartRingL1 extends Item implements ICurioItem {

    public HeartRingL1(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @SubscribeEvent
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {

        if(!(livingEntity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) livingEntity;

        //Get the Ring as an ItemStack
        ItemStack ring =
                CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.HEART_RING_1.get(), player).map(
                        ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        //Check if player is wearing it.
        if (!ring.isEmpty()) {

            if (player.getHealth() < player.getMaxHealth() && player.tickCount % 600 == 0)
            {
                player.heal(2.0f);
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
        list.add(new StringTextComponent(TextFormatting.RED + "Slowly recover lost Hearts"));
    }
}