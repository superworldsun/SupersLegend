package com.superworldsun.superslegend.items.curios.charms;

import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class GoldenScale extends NonEnchantItem implements ICurioItem {
    public GoldenScale(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        if(!(livingEntity instanceof Player)) return;
        Player player = (Player) livingEntity;

        //Get the Charm as an ItemStack
        ItemStack charm =
                CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.SILVER_SCALE.get(), player).map(
                        ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        //Check if player is wearing it.
        if (!charm.isEmpty()) {
            if(!player.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && !player.isEyeInFluidType(ForgeMod.LAVA_TYPE.get()) && player.isInWater())
            {
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 415, 0, false, false, false));
            }
        }
    }
}
