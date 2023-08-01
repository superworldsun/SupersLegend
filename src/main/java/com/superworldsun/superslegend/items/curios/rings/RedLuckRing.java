package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.RingItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class RedLuckRing extends RingItem {
    public RedLuckRing(Properties properties) {
        super(new Properties());
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) { // Check if the entity taking damage is a Player
            Player player = (Player) event.getEntity();

            // Get the Ring as an ItemStack
            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.RED_LUCK_RING.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            // Check if player is wearing it.

            //TODO fix this part of code.
            /*if (event.getSource() == DamageSource.CACTUS || event.getSource() == DamageSource.SWEET_BERRY_BUSH || event.getSource() == DamageSource.thorns(player)
                    || event.getSource() == DamageSource.thorns(player.getEntity()) || event.getSource() == DamageSource.thorns(event.getEntity()))
            {
                event.setAmount(event.getAmount() / 2);
            }*/
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("1/2 Damage From Traps").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}