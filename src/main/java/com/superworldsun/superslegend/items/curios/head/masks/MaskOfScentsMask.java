package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class MaskOfScentsMask extends Item implements ICurioItem {
    private static final TagKey<Block> SCENT_DETECTABLE_MUSHROOMS = TagKey.create(
            Registries.BLOCK,
            new ResourceLocation(SupersLegendMain.MOD_ID, "mask_of_scents_detectable")
    );
    private static final String NEXT_SCAN_TIME = SupersLegendMain.MOD_ID + ":mask_of_scents_next_scan";
    private static final String NEXT_OINK_TIME = SupersLegendMain.MOD_ID + ":mask_of_scents_next_oink";
    private static final int DETECTION_RANGE = 20;
    private static final int SCAN_INTERVAL_TICKS = 10;
    private static final int CLOSE_OINK_INTERVAL_TICKS = 12;
    private static final int FAR_OINK_INTERVAL_TICKS = 50;

    public MaskOfScentsMask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!(livingEntity instanceof Player player) || player.level().isClientSide || !player.isAlive()) {
            return;
        }

        long gameTime = player.level().getGameTime();
        if (gameTime < player.getPersistentData().getLong(NEXT_SCAN_TIME)) {
            return;
        }
        player.getPersistentData().putLong(NEXT_SCAN_TIME, gameTime + SCAN_INTERVAL_TICKS);

        double mushroomDistance = findNearestMushroomDistance(player);
        if (mushroomDistance < 0.0D) {
            // Finding a new scent should produce feedback immediately instead of retaining an old cooldown.
            player.getPersistentData().putLong(NEXT_OINK_TIME, gameTime);
            return;
        }

        if (gameTime < player.getPersistentData().getLong(NEXT_OINK_TIME)) {
            return;
        }

        float proximity = Mth.clamp((float) (mushroomDistance / DETECTION_RANGE), 0.0F, 1.0F);
        int nextInterval = Mth.floor(Mth.lerp(proximity,
                CLOSE_OINK_INTERVAL_TICKS, FAR_OINK_INTERVAL_TICKS));
        player.getPersistentData().putLong(NEXT_OINK_TIME, gameTime + nextInterval);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PIG_AMBIENT, SoundSource.PLAYERS, 0.9F,
                0.95F + player.getRandom().nextFloat() * 0.1F);
    }

    private static double findNearestMushroomDistance(Player player) {
        BlockPos center = player.blockPosition();
        double nearestDistanceSquared = Double.MAX_VALUE;
        int rangeSquared = DETECTION_RANGE * DETECTION_RANGE;

        for (BlockPos position : BlockPos.betweenClosed(
                center.offset(-DETECTION_RANGE, -DETECTION_RANGE, -DETECTION_RANGE),
                center.offset(DETECTION_RANGE, DETECTION_RANGE, DETECTION_RANGE))) {
            double distanceSquared = position.distToCenterSqr(player.position());
            if (distanceSquared > rangeSquared || distanceSquared >= nearestDistanceSquared) {
                continue;
            }
            if (player.level().getBlockState(position).is(SCENT_DETECTABLE_MUSHROOMS)) {
                nearestDistanceSquared = distanceSquared;
            }
        }

        return nearestDistanceSquared == Double.MAX_VALUE ? -1.0D : Math.sqrt(nearestDistanceSquared);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("This mask is said to").withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.literal("Enhance ones Pig-like senses to smell fungi").withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.literal("Oinks more frequently as nearby fungi get closer").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
