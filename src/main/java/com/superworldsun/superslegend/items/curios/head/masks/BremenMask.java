package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.ai.FollowBremenMaskGoal;
import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.BremenMaskSoundMessage;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class BremenMask extends Item implements IMaskAbility, ICurioItem {
    private static final UUID SLOW_MODIFIER_ID = UUID.fromString("7176f8ab-df6b-4065-9232-3c314fadb655");

    public BremenMask(Properties pProperties) {
        super(pProperties);
    }

    // Adds goal for following players with Bremen mask into every animal
    @SubscribeEvent
    public static void onEntityConstructing(EntityJoinLevelEvent event)
    {
        if (event.getEntity() instanceof Animal)
        {
            Animal animal = (Animal) event.getEntity();

            if (!(animal.getNavigation() instanceof GroundPathNavigation) && !(animal.getNavigation() instanceof FlyingPathNavigation))
            {
                return;
            }

            animal.goalSelector.addGoal(3, new FollowBremenMaskGoal(animal, 1.2D, false));
        }
    }

    @SubscribeEvent
    public static void onPlayerStartsTracking(PlayerEvent.StartTracking event) {
        if (!(event.getEntity() instanceof ServerPlayer listener)
                || !(event.getTarget() instanceof Player marchingPlayer)
                || !IMaskAbility.PLAYERS_USING_MASKS.contains(marchingPlayer)
                || CuriosApi.getCuriosHelper()
                .findFirstCurio(marchingPlayer, ItemInit.MASK_BREMANMASK.get()).isEmpty()) {
            return;
        }

        NetworkDispatcher.network_channel.send(
                PacketDistributor.PLAYER.with(() -> listener),
                new BremenMaskSoundMessage(marchingPlayer.getId(), true)
        );
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        if (livingEntity.getType() != EntityType.PLAYER)
            return;

        if (isPlayerUsingAbility((Player) livingEntity) && livingEntity.isSprinting())
        {
            livingEntity.setSprinting(false);
        }
    }

    @Override
    public void startUsingAbility(Player player)
    {
        boolean wasAlreadyUsing = isPlayerUsingAbility(player);

        // -0.3 is 30% slower
        AttributeModifier modifier = new AttributeModifier(SLOW_MODIFIER_ID, "Bremen Mask Slow", -0.3, AttributeModifier.Operation.MULTIPLY_BASE);
        AttributeInstance movespeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movespeed.getModifier(SLOW_MODIFIER_ID) == null)
        {
            movespeed.addTransientModifier(modifier);
        }
        IMaskAbility.super.startUsingAbility(player);

        if (!player.level().isClientSide && !wasAlreadyUsing) {
            syncMarchSound(player, true);
        }
    }

    @Override
    public void stopUsingAbility(Player player)
    {
        boolean wasUsingAbility = isPlayerUsingAbility(player);
        FollowBremenMaskGoal.stopFollowing(player);

        AttributeModifier modifier = player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(SLOW_MODIFIER_ID);
        AttributeInstance movespeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (modifier != null)
        {
            movespeed.removeModifier(modifier);
        }

        IMaskAbility.super.stopUsingAbility(player);

        if (!player.level().isClientSide && wasUsingAbility) {
            syncMarchSound(player, false);
        }
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.onUnequip(identifier, index, livingEntity, stack);
        if (livingEntity instanceof Player player && isPlayerUsingAbility(player)) {
            stopUsingAbility(player);
        }
    }

    private static void syncMarchSound(Player player, boolean playing) {
        NetworkDispatcher.network_channel.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new BremenMaskSoundMessage(player.getId(), playing)
        );
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("A mask animals would love!").withStyle(ChatFormatting.WHITE));
        //tooltip.add(Component.literal("Hold '" + keybind + "' to have animals follow you").withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
