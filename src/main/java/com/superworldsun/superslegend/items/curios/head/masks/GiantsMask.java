package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.GiantGroundParticleMessage;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GiantsMask extends Item implements ICurioItem {
    private static final float MANA_COST_PER_TICK = 0.01F;
    private static final int MAGIC_DRAIN_INTERVAL = 5;
    private static final int TRANSITION_TICKS = 20;
    private static final float GIANT_SIZE = 6.22F;
    private static final float GIANT_BLOCK_REACH = 3.0F;
    private static final float GIANT_ENTITY_REACH = 3.0F;
    private static final float GIANT_MOTION = 3.0F;
    private static final float GIANT_JUMP_HEIGHT = 1.5F;
    private static final float GIANT_FALLING = 0.4F;
    private static final float GIANT_STEP_HEIGHT = 0.8F;
    private static final float GIANT_MINING_SPEED = 2.0F;
    private static final double MINIMUM_PARTICLE_MOVEMENT_SQR = 1.0E-3D;
    private static final double MAXIMUM_GROUND_EFFECT_MOVEMENT_SQR = 4.0D;
    private static final float MAXIMUM_PARTICLE_SCALE = 2.4F;
    private static final int WALK_PARTICLE_INTERVAL = 6;
    private static final int SPRINT_PARTICLE_INTERVAL = 4;
    private static final int WALK_PARTICLE_COUNT = 2;
    private static final int SPRINT_PARTICLE_COUNT = 3;
    private static final Map<Player, Vec3> SERVER_PREVIOUS_POSITIONS = new WeakHashMap<>();
    private static final Map<Player, Integer> SERVER_LAST_LANDING_TICKS = new WeakHashMap<>();

    public GiantsMask(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        // Pehkui synchronizes server-side scale changes to the wearer and every
        // tracking client. A client must never derive another player's scale
        // from its local magic capability, because only the owner receives
        // that capability's magic updates.
        if (player.level().isClientSide) {
            return;
        }

        if (player.tickCount % MAGIC_DRAIN_INTERVAL == 0
                && MagicProvider.hasMagic(player, MANA_COST_PER_TICK)) {
            MagicProvider.spendMagic(player, MANA_COST_PER_TICK * MAGIC_DRAIN_INTERVAL);
        }

        boolean giant = MagicProvider.hasMagic(player, MANA_COST_PER_TICK);
        applyTransformation(player, giant);
        Vec3 currentPosition = player.position();
        Vec3 previousPosition = SERVER_PREVIOUS_POSITIONS.put(player, currentPosition);
        if (giant && previousPosition != null) {
            handleGiantGroundEffects(player, currentPosition.subtract(previousPosition));
        }
    }

    @Override
    public void onEquip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.onEquip(identifier, index, livingEntity, stack);
        if (livingEntity instanceof Player player && !player.level().isClientSide) {
            applyTransformation(player, MagicProvider.hasMagic(player, MANA_COST_PER_TICK));
        }
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.onUnequip(identifier, index, livingEntity, stack);
        if (livingEntity.level().isClientSide) {
            return;
        }

        if (livingEntity instanceof Player player) {
            SERVER_PREVIOUS_POSITIONS.remove(player);
            SERVER_LAST_LANDING_TICKS.remove(player);
        }
        applyTransformation(livingEntity, false);
    }

    public static boolean isGiantTransformationActive(Player player) {
        return ScaleTypes.WIDTH.getScaleData(player).getTargetScale() > 1.0F
                && CuriosApi.getCuriosHelper()
                .findEquippedCurio(ItemInit.MASK_GIANTSMASK.get(), player)
                .isPresent();
    }

    public static boolean hasTransformationMagic(Player player) {
        return MagicProvider.hasMagic(player, MANA_COST_PER_TICK);
    }

    public static void playHeavyFootstep(Player player) {
        if (player.level().isClientSide
                || !(player.level() instanceof ServerLevel serverLevel)
                || !isGiantTransformationActive(player)
                || SERVER_LAST_LANDING_TICKS.getOrDefault(player, -1) == player.tickCount) {
            return;
        }

        float currentScale = ScaleTypes.WIDTH.getScaleData(player).getScale();
        float giantProgress = Math.max(0.0F, Math.min(1.0F, (currentScale - 1.0F) / (GIANT_SIZE - 1.0F)));
        if (giantProgress <= 0.05F) {
            return;
        }

        float volume = 1.8F * (0.3F + 0.7F * giantProgress);
        float pitch = 0.54F + (player.getRandom().nextFloat() - 0.5F) * 0.05F;
        serverLevel.playSound(
                null,
                player.getX(),
                player.getBoundingBox().minY,
                player.getZ(),
                SoundEvents.WARDEN_STEP,
                SoundSource.PLAYERS,
                volume,
                pitch
        );
    }

    private static void applyTransformation(LivingEntity livingEntity, boolean giant) {
        setScale(ScaleTypes.HEIGHT.getScaleData(livingEntity), giant ? GIANT_SIZE : 1.0F);
        setScale(ScaleTypes.WIDTH.getScaleData(livingEntity), giant ? GIANT_SIZE : 1.0F);
        setScale(ScaleTypes.BLOCK_REACH.getScaleData(livingEntity), giant ? GIANT_BLOCK_REACH : 1.0F);
        setScale(ScaleTypes.ENTITY_REACH.getScaleData(livingEntity), giant ? GIANT_ENTITY_REACH : 1.0F);
        setScale(ScaleTypes.MOTION.getScaleData(livingEntity), giant ? GIANT_MOTION : 1.0F);
        setScale(ScaleTypes.JUMP_HEIGHT.getScaleData(livingEntity), giant ? GIANT_JUMP_HEIGHT : 1.0F);
        setScale(ScaleTypes.FALLING.getScaleData(livingEntity), giant ? GIANT_FALLING : 1.0F);
        setScale(ScaleTypes.STEP_HEIGHT.getScaleData(livingEntity), giant ? GIANT_STEP_HEIGHT : 1.0F);
        setScale(ScaleTypes.MINING_SPEED.getScaleData(livingEntity), giant ? GIANT_MINING_SPEED : 1.0F);
    }

    private static void setScale(ScaleData scaleData, float targetScale) {
        if (Float.compare(scaleData.getTargetScale(), targetScale) == 0) {
            return;
        }

        scaleData.setScaleTickDelay(TRANSITION_TICKS);
        scaleData.setTargetScale(targetScale);
    }

    private static void handleGiantGroundEffects(Player player, Vec3 movement) {
        if (!(player.level() instanceof ServerLevel serverLevel)
                || !player.onGround()
                || player.isInWater()
                || player.isCrouching()
                || player.isPassenger()) {
            return;
        }

        double horizontalSpeedSqr = movement.x * movement.x + movement.z * movement.z;
        if (horizontalSpeedSqr <= MINIMUM_PARTICLE_MOVEMENT_SQR
                || horizontalSpeedSqr > MAXIMUM_GROUND_EFFECT_MOVEMENT_SQR) {
            return;
        }

        boolean sprinting = player.isSprinting();
        float currentScale = ScaleTypes.WIDTH.getScaleData(player).getScale();
        float giantProgress = Math.max(0.0F, Math.min(1.0F, (currentScale - 1.0F) / (GIANT_SIZE - 1.0F)));
        if (giantProgress <= 0.05F) {
            return;
        }

        BlockPos groundPosition = BlockPos.containing(
                player.getX(),
                player.getBoundingBox().minY - 0.05D,
                player.getZ()
        );
        BlockState groundState = serverLevel.getBlockState(groundPosition);
        if (groundState.isAir()) {
            return;
        }

        int particleInterval = sprinting ? SPRINT_PARTICLE_INTERVAL : WALK_PARTICLE_INTERVAL;
        if (player.tickCount % particleInterval != 0) {
            return;
        }

        double horizontalSpeed = Math.sqrt(horizontalSpeedSqr);
        double backwardOffset = (sprinting ? 0.55D : 0.35D) * giantProgress;
        double particleX = player.getX() - movement.x / horizontalSpeed * backwardOffset;
        double particleZ = player.getZ() - movement.z / horizontalSpeed * backwardOffset;
        double horizontalSpread = (sprinting ? 0.8D : 0.55D) * giantProgress;
        double upwardSpread = (sprinting ? 0.18D : 0.12D) * giantProgress;
        double particleSpeed = (sprinting ? 0.1D : 0.06D) * giantProgress;
        int particleCount = Math.max(
                1,
                Math.round((sprinting ? SPRINT_PARTICLE_COUNT : WALK_PARTICLE_COUNT) * giantProgress)
        );

        sendGroundParticles(
                player,
                groundState,
                groundPosition,
                particleX,
                player.getBoundingBox().minY + 0.08D,
                particleZ,
                particleCount,
                (float) horizontalSpread,
                (float) upwardSpread,
                (float) particleSpeed,
                getParticleScale(giantProgress)
        );
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || player.level().isClientSide
                || event.getDistance() < 0.75F
                || !isGiantTransformationActive(player)
                || !(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockPos groundPosition = BlockPos.containing(
                player.getX(),
                player.getBoundingBox().minY - 0.05D,
                player.getZ()
        );
        BlockState groundState = serverLevel.getBlockState(groundPosition);
        if (groundState.isAir()) {
            return;
        }

        float currentScale = ScaleTypes.WIDTH.getScaleData(player).getScale();
        float giantProgress = Math.max(0.0F, Math.min(1.0F, (currentScale - 1.0F) / (GIANT_SIZE - 1.0F)));
        if (giantProgress <= 0.05F) {
            return;
        }

        float impactStrength = Math.min(1.0F, event.getDistance() / 8.0F);
        SERVER_LAST_LANDING_TICKS.put(player, player.tickCount);
        int particleCount = 2 + Math.round(3.0F * impactStrength);
        sendGroundParticles(
                player,
                groundState,
                groundPosition,
                player.getX(),
                player.getBoundingBox().minY + 0.08D,
                player.getZ(),
                particleCount,
                0.65F + 0.35F * impactStrength,
                0.12F + 0.1F * impactStrength,
                0.07F + 0.06F * impactStrength,
                getParticleScale(giantProgress)
        );

        serverLevel.playSound(
                null,
                player.getX(),
                player.getBoundingBox().minY,
                player.getZ(),
                SoundEvents.WARDEN_STEP,
                SoundSource.PLAYERS,
                2.4F + 1.2F * impactStrength,
                0.44F + player.getRandom().nextFloat() * 0.04F
        );
    }

    private static float getParticleScale(float giantProgress) {
        return 1.0F + (MAXIMUM_PARTICLE_SCALE - 1.0F) * giantProgress;
    }

    private static void sendGroundParticles(Player player,
                                            BlockState groundState,
                                            BlockPos groundPosition,
                                            double x,
                                            double y,
                                            double z,
                                            int count,
                                            float horizontalSpread,
                                            float upwardSpread,
                                            float speed,
                                            float particleScale) {
        NetworkDispatcher.network_channel.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new GiantGroundParticleMessage(
                        groundState,
                        groundPosition,
                        x,
                        y,
                        z,
                        count,
                        horizontalSpread,
                        upwardSpread,
                        speed,
                        particleScale
                )
        );
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("Within this mask lies the might of a giant").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Awaken the giants power and abilities").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.literal("at the cost of magic").withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
