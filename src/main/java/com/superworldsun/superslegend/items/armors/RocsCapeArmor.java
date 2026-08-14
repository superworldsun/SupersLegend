package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.armor.GeoArmorRendererExtension;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;
import java.util.List;

/** Wearable chest armor renderer for Roc's Cape. */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RocsCapeArmor extends NonEnchantArmor implements GeoItem {
    private static final String CAN_FLY_TAG = "canFly";
    private static final int MAX_FLIGHT_TICKS = 40;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public RocsCapeArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return stack.getOrCreateTag().getBoolean(CAN_FLY_TAG);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        boolean canContinue = flightTicks < MAX_FLIGHT_TICKS;
        if (!canContinue) {
            stack.getOrCreateTag().putBoolean(CAN_FLY_TAG, false);
        }
        return canContinue;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.onGround() && !stack.getOrCreateTag().getBoolean(CAN_FLY_TAG)) {
            stack.getOrCreateTag().putBoolean(CAN_FLY_TAG, true);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.literal("Grants better aerial and ground mobility")
                .withStyle(ChatFormatting.WHITE));
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity().getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST)
                .getItem() instanceof RocsCapeArmor) {
            event.setDistance(Math.max(0.0F, event.getDistance() - 4.0F));
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity().getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST)
                .getItem() instanceof RocsCapeArmor) {
            Vec3 movement = event.getEntity().getDeltaMovement();
            event.getEntity().setDeltaMovement(movement.add(0.0D, 0.1D, 0.0D));
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        GeoArmorRendererExtension<RocsCapeArmor> extension =
                new GeoArmorRendererExtension<RocsCapeArmor>("back/rocs_cape")
                        .setAnimationName("kokiri_armor");
        extension.setTextureProvider((wearer, stack, slot) -> new ResourceLocation(
                SupersLegendMain.MOD_ID, "textures/curio/rocs_cape.png"));
        consumer.accept(extension);
    }

    private PlayState animationPredicate(AnimationState<RocsCapeArmor> state) {
        state.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
