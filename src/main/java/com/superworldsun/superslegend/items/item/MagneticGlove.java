package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class MagneticGlove extends Item
{
    private static final int RANGE = 15;
    private static final int MAXIMUM_PULLED_ITEMS = 200;
    private static final double ITEM_PULL_SPEED = 0.7D;
    private static final double ARMOR_POINT_PULL_SPEED = 0.11D;
    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };
    public MagneticGlove(Properties properties)
    {
        super(properties);
    }

    //TODO Test to Make sure this still pulls other players in multiplayer but not the user

    //TODO Add some sound for when item is in use

    //TODO Model for offhand is inconsistent with mainhand?

    public static int getCooldown(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Cooldown");
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        stack.getOrCreateTag().putInt("Cooldown", cooldown);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            pullNearbyTargets(player);
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseTicks) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            pullNearbyTargets(player);
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.NONE;
    }

    private static void pullNearbyTargets(Player player) {
        Vec3 playerPos = player.position().add(0, 0.75, 0);
        AABB pullArea = new AABB(
                playerPos.x - RANGE, playerPos.y - RANGE, playerPos.z - RANGE,
                playerPos.x + RANGE, playerPos.y + RANGE, playerPos.z + RANGE
        );
        List<ItemEntity> itemEntityList = player.level().getEntitiesOfClass(ItemEntity.class, pullArea);
        List<LivingEntity> livingEntityList = player.level().getEntitiesOfClass(LivingEntity.class, pullArea);
        int pulled = 0;

        for (ItemEntity item : itemEntityList) {
            if (item.isAlive() && !item.hasPickUpDelay() && !item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                if (pulled++ >= MAXIMUM_PULLED_ITEMS) {
                    break;
                }

                pullEntity(item, playerPos, ITEM_PULL_SPEED);
            }
        }

        for (LivingEntity entity : livingEntityList) {
            if (!entity.isAlive() || entity == player) {
                continue;
            }

            int armorPoints = getArmorPoints(entity);
            if (armorPoints > 0) {
                pullEntity(entity, playerPos, ARMOR_POINT_PULL_SPEED * armorPoints);
            }
        }
    }

    private static int getArmorPoints(LivingEntity entity) {
        int points = 0;
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (entity.getItemBySlot(slot).is(TagInit.METALIC_ARMOR)) {
                points += slot == EquipmentSlot.CHEST ? 2 : 1;
            }
        }
        return points;
    }

    private static void pullEntity(net.minecraft.world.entity.Entity entity, Vec3 destination, double speed) {
        Vec3 direction = destination.subtract(entity.position().add(0, entity.getBbHeight() / 2.0D, 0));
        if (direction.lengthSqr() > 1.0D) {
            direction = direction.normalize();
        }

        entity.setDeltaMovement(direction.scale(speed));
        entity.hasImpulse = true;
        if (entity instanceof LivingEntity livingEntity) {
            // This forces the server's velocity onto remote players instead of
            // allowing their next movement packet to immediately overwrite it.
            livingEntity.hurtMarked = true;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Pulls items toward the player").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Hold Right-click to Pull items").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("Entities wearing metal armor can be pulled").withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}