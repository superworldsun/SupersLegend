package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.api.DamageReductionItem;
import com.superworldsun.superslegend.items.customclass.RingItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
public class RedLuckRing extends RingItem implements DamageReductionItem {
    public RedLuckRing(Properties properties) {
        super(new Properties());
    }

    @Override
    public boolean canReduceDamage(DamageSource damage) {
        return damage.is(DamageTypes.CACTUS) || damage.is(DamageTypes.SWEET_BERRY_BUSH) ||
                damage.is(DamageTypes.THORNS) || damage.is(DamageTypes.STING) ||
                damage.is(DamageTypes.STALAGMITE);
    }

    @Override
    public float getDamageReduction() {
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("1/2 Damage From spikes & thorns").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}