package superworldsun.superslegend.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import superworldsun.superslegend.entities.mobs.poe.PoeEntity;

import java.util.EnumSet;


public class PoeAttackGoal extends MeleeAttackGoal {
        private final PoeEntity poe;
        private final double speedTowardsTarget;
        private final boolean longMemory;
        private Path path;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int delayCounter;
        private int ticksUntilNextAttack;
        private final int attackInterval = 20;
        private long lastCanUseCheck;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;

        public PoeAttackGoal(PoeEntity poe, double speedIn, boolean useLongMemory) {
            super(poe, speedIn, useLongMemory);
            this.poe = poe;
            this.speedTowardsTarget = speedIn;
            this.longMemory = useLongMemory;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            long i = this.poe.level.getGameTime();
            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.poe.getTarget();
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else {
                    if (canPenalize) {
                        if (--this.delayCounter <= 0) {
                            this.path = this.poe.getNavigation().createPath(livingentity, 0);
                            this.delayCounter = 4 + this.poe.getRandom().nextInt(7);
                            return this.path != null;
                        } else {
                            return true;
                        }
                    }
                    this.path = this.poe.getNavigation().createPath(livingentity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getAttackReachSqr(livingentity) >= this.poe.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                    }
                }
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = this.poe.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!this.longMemory) {
                return !this.poe.getNavigation().isDone();
            } else if (!this.poe.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative();
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.poe.getNavigation().moveTo(this.path, this.speedTowardsTarget);
            this.poe.setAggressive(true);
            this.delayCounter = 0;
            this.ticksUntilNextAttack = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            LivingEntity livingentity = this.poe.getTarget();
            if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.poe.setTarget((LivingEntity)null);
            }

            this.poe.setAggressive(false);
            this.poe.getNavigation().stop();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.poe.getTarget();
            this.poe.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            double d0 = this.poe.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            this.delayCounter = Math.max(this.delayCounter - 1, 0);
            if ((this.longMemory || this.poe.getSensing().canSee(livingentity)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || livingentity.distanceToSqr(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.poe.getRandom().nextFloat() < 0.05F)) {
                this.targetX = livingentity.getX();
                this.targetY = livingentity.getY();
                this.targetZ = livingentity.getZ();
                this.delayCounter = 4 + this.poe.getRandom().nextInt(7);
                if (this.canPenalize) {
                    this.delayCounter += failedPathFindingPenalty;
                    if (this.poe.getNavigation().getPath() != null) {
                        net.minecraft.pathfinding.PathPoint finalPathPoint = this.poe.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }
                if (d0 > 1024.0D) {
                    this.delayCounter += 10;
                } else if (d0 > 256.0D) {
                    this.delayCounter += 5;
                }

                if (!this.poe.getNavigation().moveTo(livingentity, this.speedTowardsTarget)) {
                    this.delayCounter += 15;
                }
            }

            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(livingentity, d0);
        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.ticksUntilNextAttack <= 0) {
                this.resetAttackCooldown();
                this.poe.swing(Hand.MAIN_HAND);
                this.poe.doHurtTarget(enemy);
            }

        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = 20;
        }

        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack() {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval() {
            return 20;
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(this.poe.getBbWidth() * 2.0F * this.poe.getBbWidth() * 2.0F + attackTarget.getBbWidth());
        }
    }