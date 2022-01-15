package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class RedRing extends Item implements ICurioItem {
    private static final ResourceLocation REDRING_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID,
            "textures/entity/amulet.png");
    private Object model;

    public RedRing(Properties p_i48487_1_) {
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
                    CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.BLUE_RING.get(), player).map(
                            ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            //Check if player is wearing it. Check if Sword Item.
            if (!stack.isEmpty() && player.getMainHandItem().getItem() instanceof SwordItem) {
                event.setAmount(event.getAmount() * 2);
            }
        }
    }

}