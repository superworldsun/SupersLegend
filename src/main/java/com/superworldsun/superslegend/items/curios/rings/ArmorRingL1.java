package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.RingItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class ArmorRingL1 extends RingItem {
    public ArmorRingL1(Properties properties) {
        super(new Properties());
    }

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event)
    {
        // Check if is player doing the damage.
        if (event.getSource().getDirectEntity() instanceof Player)
        {
            // Get Player.
            Player player = (Player) event.getSource().getDirectEntity();
            // Get the Ring as an ItemStack
            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.ARMOR_RING_L1.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            // Check if player is wearing it. Check if Sword Item.
            if (!stack.isEmpty() && player.getMainHandItem().getItem() instanceof SwordItem)
            {
                event.setAmount(event.getAmount() / 1.5f);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) { // Check if the entity taking damage is a Player
            Player player = (Player) event.getEntity();

            // Get the Ring as an ItemStack
            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.ARMOR_RING_L1.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            // Check if player is wearing it.
            if (!stack.isEmpty()) {
                // Reduce the damage taken by half
                event.setAmount(event.getAmount() / 1.5f);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("1.5x Sword Attack & Damage Reduced").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}