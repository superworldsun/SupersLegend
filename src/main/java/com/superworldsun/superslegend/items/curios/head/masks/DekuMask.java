package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.player.PlayerTransformationModels;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)

public class DekuMask extends Item implements ICurioItem, IPlayerModelChanger, IEntityResizer {
    private static final ResourceLocation PLAYER_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/deku_player.png");

    public DekuMask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float getScale(Player player) {
        return 0.65F;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public PlayerModel<AbstractClientPlayer> getPlayerModel(AbstractClientPlayer player) {
        return PlayerTransformationModels.getDeku();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ResourceLocation getPlayerTexture(AbstractClientPlayer player) {
        return PLAYER_TEXTURE;
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_DEKUMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (!stack0.isEmpty()) {
            if (event.getSource().is(DamageTypes.LAVA)) {
                event.setAmount(event.getAmount() * 4);
            } else if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
                event.setAmount(event.getAmount() * 2);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("The face of a Deku").withStyle(ChatFormatting.DARK_GREEN));
        tooltip.add(Component.literal("Your wooden skin is unlikely to be poisoned").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.literal("Your wooden body will burn faster").withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
