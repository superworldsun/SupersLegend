package com.superworldsun.superslegend.entities.ai;

import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FollowBremenMaskGoal extends Goal {
    private static final TargetingConditions RECRUITING_TARGETS = TargetingConditions.forNonCombat()
            .range(10.0D)
            .ignoreInvisibilityTesting()
            .ignoreLineOfSight();
    private static final Map<UUID, List<UUID>> FOLLOW_LINES = new HashMap<>();
    private static final Map<UUID, ChickTransformationState> CHICK_TRANSFORMATIONS = new HashMap<>();

    private static final int MAX_FOLLOWERS = 20;
    private static final int REQUIRED_BABY_CHICKS = 10;
    private static final int CHICK_TRANSFORM_INTERVAL = 20;
    private static final int TRAIL_DELAY_TICKS = 6;
    private static final int PATH_UPDATE_INTERVAL = 2;
    private static final double MINIMUM_SPACING = 0.55D;
    private static final double EXTRA_BODY_SPACING = 0.1D;
    private static final double JOINED_LINE_BUFFER = 0.45D;
    private static final double LINE_MOVEMENT_SPEED = 0.31D;
    private static final double CATCH_UP_SPEED_MULTIPLIER = 1.65D;
    private static final double MAX_PLAYER_DISTANCE = 64.0D;

    protected final Animal mob;
    private final double speedModifier;
    private final Deque<Vec3> leaderTrail = new ArrayDeque<>();
    protected Player player;
    private UUID linePlayerId;
    private UUID previousLeaderId;
    private int calmDown;
    private int pathUpdateCooldown;
    private boolean joinedLine;
    private boolean isRunning;

    public FollowBremenMaskGoal(Animal mob, double speed, boolean canScare) {
        this.mob = mob;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));

        if (!(mob.getNavigation() instanceof GroundPathNavigation)
                && !(mob.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type '" + mob.getType() + "'");
        }
    }

    @Override
    public boolean canUse() {
        if (!(mob.level() instanceof ServerLevel)) {
            return false;
        }

        if (calmDown > 0) {
            --calmDown;
            return false;
        }

        player = mob.level().getNearestPlayer(RECRUITING_TARGETS, mob);
        return player != null && shouldFollow(player) && canJoinLine(player);
    }

    @Override
    public boolean canContinueToUse() {
        return player != null
                && linePlayerId != null
                && player.isAlive()
                && mob.isAlive()
                && player.level() == mob.level()
                && mob.distanceToSqr(player) <= MAX_PLAYER_DISTANCE * MAX_PLAYER_DISTANCE
                && shouldFollow(player);
    }

    private boolean shouldFollow(Player player) {
        ItemStack maskStack = CuriosApi.getCuriosHelper()
                .findEquippedCurio(ItemInit.MASK_BREMANMASK.get(), player)
                .map(ImmutableTriple::getRight)
                .orElse(ItemStack.EMPTY);
        return maskStack.getItem() == ItemInit.MASK_BREMANMASK.get()
                && ((IMaskAbility) maskStack.getItem()).isPlayerUsingAbility(player);
    }

    @Override
    public void start() {
        joinLine();
        leaderTrail.clear();
        previousLeaderId = null;
        pathUpdateCooldown = 0;
        joinedLine = false;
        isRunning = true;
    }

    @Override
    public void stop() {
        boolean keepLinePosition = canRemainInLine();
        if (!keepLinePosition) {
            leaveLine();
        }
        player = null;
        previousLeaderId = null;
        leaderTrail.clear();
        mob.getNavigation().stop();
        mob.setSprinting(false);
        calmDown = keepLinePosition ? 0 : 100;
        joinedLine = false;
        isRunning = false;
    }

    private boolean canRemainInLine() {
        return player != null
                && linePlayerId != null
                && player.getUUID().equals(linePlayerId)
                && player.isAlive()
                && mob.isAlive()
                && player.level() == mob.level()
                && mob.distanceToSqr(player) <= MAX_PLAYER_DISTANCE * MAX_PLAYER_DISTANCE
                && shouldFollow(player);
    }

    @Override
    public void tick() {
        Entity leader = getLeader();
        if (leader == null) {
            mob.getNavigation().stop();
            mob.setSprinting(false);
            return;
        }

        updateChickTransformations((ServerLevel) mob.level(), linePlayerId);

        UUID leaderId = leader.getUUID();
        if (!leaderId.equals(previousLeaderId)) {
            leaderTrail.clear();
            previousLeaderId = leaderId;
        }

        double distanceToLeaderSqr = mob.distanceToSqr(leader);
        double lineSpacing = Math.max(MINIMUM_SPACING,
                (mob.getBbWidth() + leader.getBbWidth()) * 0.5D + EXTRA_BODY_SPACING);
        double joinedLineDistance = lineSpacing + JOINED_LINE_BUFFER;
        if (!joinedLine && distanceToLeaderSqr <= joinedLineDistance * joinedLineDistance) {
            joinedLine = true;
            leaderTrail.clear();
        }

        boolean catchingUp = !joinedLine;
        mob.setSprinting(catchingUp);
        mob.getLookControl().setLookAt(leader,
                mob.getMaxHeadYRot() + 20.0F,
                mob.getMaxHeadXRot());

        if (catchingUp) {
            moveTo(leader.position(), getNormalizedNavigationSpeed(CATCH_UP_SPEED_MULTIPLIER));
            return;
        }

        leaderTrail.addLast(leader.position());
        while (leaderTrail.size() > TRAIL_DELAY_TICKS) {
            leaderTrail.removeFirst();
        }
        Vec3 target = leaderTrail.getFirst();

        boolean isCrowdingLeader = distanceToLeaderSqr <= lineSpacing * lineSpacing;
        if (isCrowdingLeader) {
            mob.getNavigation().stop();
            return;
        }

        moveTo(target, getNormalizedNavigationSpeed(1.0D));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private void joinLine() {
        UUID playerId = player.getUUID();
        if (playerId.equals(linePlayerId)) {
            List<UUID> existingLine = FOLLOW_LINES.get(playerId);
            if (existingLine != null) {
                cleanLine((ServerLevel) mob.level(), existingLine);
                if (existingLine.contains(mob.getUUID())) {
                    return;
                }
            }
        }

        leaveLine();
        linePlayerId = playerId;
        List<UUID> line = FOLLOW_LINES.computeIfAbsent(linePlayerId, ignored -> new ArrayList<>());
        cleanLine((ServerLevel) mob.level(), line);
        line.remove(mob.getUUID());
        if (line.size() >= MAX_FOLLOWERS) {
            linePlayerId = null;
            return;
        }
        line.add(mob.getUUID());

        ChickTransformationState transformationState = CHICK_TRANSFORMATIONS.get(linePlayerId);
        if (transformationState != null && isIneligibleNewFollower(mob, transformationState)) {
            revertChickTransformations((ServerLevel) mob.level(), linePlayerId);
        }
    }

    private boolean canJoinLine(Player player) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        List<UUID> line = FOLLOW_LINES.get(player.getUUID());
        if (line == null) {
            return true;
        }

        cleanLine(serverLevel, line);
        return line.contains(mob.getUUID()) || line.size() < MAX_FOLLOWERS;
    }

    private void leaveLine() {
        if (linePlayerId == null) {
            return;
        }

        List<UUID> line = FOLLOW_LINES.get(linePlayerId);
        if (line != null) {
            line.remove(mob.getUUID());
            if (line.isEmpty()) {
                FOLLOW_LINES.remove(linePlayerId);
            }
        }
        linePlayerId = null;
    }

    private Entity getLeader() {
        if (!(mob.level() instanceof ServerLevel serverLevel) || linePlayerId == null) {
            return null;
        }

        List<UUID> line = FOLLOW_LINES.get(linePlayerId);
        if (line == null) {
            return null;
        }

        cleanLine(serverLevel, line);

        int linePosition = line.indexOf(mob.getUUID());
        if (linePosition < 0) {
            return null;
        }

        if (linePosition == 0) {
            return player;
        }

        Entity leader = serverLevel.getEntity(line.get(linePosition - 1));
        return leader instanceof Animal animal && animal.isAlive() ? animal : player;
    }

    private static void cleanLine(ServerLevel serverLevel, List<UUID> line) {
        line.removeIf(entityId -> {
            Entity entity = serverLevel.getEntity(entityId);
            return !(entity instanceof Animal animal) || !animal.isAlive();
        });
    }

    public static void stopFollowing(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            revertChickTransformations(serverLevel, player.getUUID());
        }
    }

    private static void updateChickTransformations(ServerLevel serverLevel, UUID playerId) {
        if (playerId == null) {
            return;
        }

        List<UUID> line = FOLLOW_LINES.get(playerId);
        ChickTransformationState state = CHICK_TRANSFORMATIONS.get(playerId);
        long gameTime = serverLevel.getGameTime();
        if (state != null && state.lastProcessedGameTick == gameTime) {
            return;
        }

        if (state == null && !canStartChickTransformations(serverLevel, line)) {
            return;
        }

        if (state == null) {
            state = new ChickTransformationState();
            CHICK_TRANSFORMATIONS.put(playerId, state);
        }
        state.lastProcessedGameTick = gameTime;
        keepTransformedChicksAdult(serverLevel, state);

        if (--state.ticksUntilNextTransformation > 0) {
            return;
        }
        state.ticksUntilNextTransformation = CHICK_TRANSFORM_INTERVAL;

        for (UUID followerId : line) {
            Entity entity = serverLevel.getEntity(followerId);
            if (entity instanceof Chicken chicken
                    && chicken.isBaby()
                    && !state.originalBabyAges.containsKey(followerId)) {
                state.originalBabyAges.put(followerId, chicken.getAge());
                chicken.setAge(0);
                serverLevel.sendParticles(ParticleTypes.POOF,
                        chicken.getX(), chicken.getY() + chicken.getBbHeight() * 0.5D, chicken.getZ(),
                        12, 0.25D, 0.25D, 0.25D, 0.03D);
                serverLevel.playSound(null,
                        chicken.getX(), chicken.getY(), chicken.getZ(),
                        SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL,
                        1.0F, 1.15F);
                finishChickTransformationsIfComplete(serverLevel, playerId, line, state);
                return;
            }
        }
    }

    private static boolean canStartChickTransformations(ServerLevel serverLevel, List<UUID> line) {
        if (line == null || line.size() < REQUIRED_BABY_CHICKS) {
            return false;
        }

        for (UUID followerId : line) {
            Entity entity = serverLevel.getEntity(followerId);
            if (!(entity instanceof Chicken chicken) || !chicken.isBaby()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isIneligibleNewFollower(Animal follower, ChickTransformationState state) {
        if (!(follower instanceof Chicken chicken)) {
            return true;
        }
        return !chicken.isBaby() && !state.originalBabyAges.containsKey(chicken.getUUID());
    }

    private static void keepTransformedChicksAdult(ServerLevel serverLevel, ChickTransformationState state) {
        for (UUID followerId : state.originalBabyAges.keySet()) {
            Entity entity = serverLevel.getEntity(followerId);
            if (entity instanceof Chicken chicken && chicken.isAlive() && chicken.getAge() != 0) {
                chicken.setAge(0);
            }
        }
    }

    private static void finishChickTransformationsIfComplete(ServerLevel serverLevel, UUID playerId,
                                                              List<UUID> line,
                                                              ChickTransformationState state) {
        if (state.originalBabyAges.size() < REQUIRED_BABY_CHICKS) {
            return;
        }

        for (UUID followerId : line) {
            Entity entity = serverLevel.getEntity(followerId);
            if (!(entity instanceof Chicken chicken)
                    || chicken.isBaby()
                    || !state.originalBabyAges.containsKey(followerId)) {
                return;
            }
        }

        // Once every chick has grown, the result is permanent. Removing the temporary
        // state without restoring the saved ages commits all of them as adults.
        CHICK_TRANSFORMATIONS.remove(playerId);
        ServerPlayer player = serverLevel.getServer().getPlayerList().getPlayer(playerId);
        if (player != null) {
            ModAdvancementHelper.award(player, "march_of_the_chicks", "ten_adult_chickens");
        }
    }

    private static void revertChickTransformations(ServerLevel serverLevel, UUID playerId) {
        ChickTransformationState state = CHICK_TRANSFORMATIONS.remove(playerId);
        if (state == null) {
            return;
        }

        state.originalBabyAges.forEach((followerId, originalAge) -> {
            Entity entity = serverLevel.getEntity(followerId);
            if (entity instanceof Chicken chicken && chicken.isAlive()) {
                chicken.setAge(originalAge);
            }
        });
    }

    private static final class ChickTransformationState {
        private final Map<UUID, Integer> originalBabyAges = new HashMap<>();
        private int ticksUntilNextTransformation = CHICK_TRANSFORM_INTERVAL;
        private long lastProcessedGameTick = Long.MIN_VALUE;
    }

    private void moveTo(Vec3 target, double movementSpeed) {
        if (--pathUpdateCooldown <= 0) {
            pathUpdateCooldown = PATH_UPDATE_INTERVAL;
            mob.getNavigation().moveTo(target.x, target.y, target.z, movementSpeed);
        }
    }

    private double getNormalizedNavigationSpeed(double multiplier) {
        double mobMovementSpeed = mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
        if (mobMovementSpeed <= 0.0D) {
            return speedModifier * multiplier;
        }
        return LINE_MOVEMENT_SPEED / mobMovementSpeed * multiplier;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
