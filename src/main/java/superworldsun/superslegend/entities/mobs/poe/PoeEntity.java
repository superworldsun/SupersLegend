package superworldsun.superslegend.entities.mobs.poe;

//import com.github.alexthe666.citadel.animation.Animation;
//import com.github.alexthe666.citadel.animation.AnimationHandler;
//import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.Interfaces.IAnimalFear;
import superworldsun.superslegend.entities.Interfaces.IHumanoid;
import superworldsun.superslegend.entities.Interfaces.IVillagerFear;
//import superworldsun.superslegend.entities.ai.PoeChargeAttack;
import superworldsun.superslegend.entities.ai.PoePathNavigator;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class PoeEntity extends MonsterEntity {
    protected PoeEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    /*private static final DataParameter<Boolean> CHARGING = EntityDataManager.createKey(PoeEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_DAYTIME_MODE = EntityDataManager.createKey(PoeEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> DAYTIME_COUNTER = EntityDataManager.createKey(PoeEntity.class, DataSerializers.VARINT);
    public static Animation ANIMATION_HIT;
    private int animationTick;
    private Animation currentAnimation;


    public PoeEntity(EntityType<? extends PoeEntity> type, World worldIn) {
        super(type, worldIn);
        ANIMATION_HIT = Animation.create(10);
        this.moveController = new PoeEntity.MoveHelperController(this);
        this.experienceValue = 10;
    }

    public void move(MoverType typeIn, Vector3d pos) {
        super.move(typeIn, pos);
        this.doBlockCollisions();
    }

    //TODO Add an ambient sound
    /*@Nullable
    protected SoundEvent getAmbientSound() {
        return IafSoundRegistry.poe_IDLE;
    }*/

    //TODO Add hurt sound
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return super.getHurtSound(damageSourceIn);
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height;
    }

    //TODO Add death sound
    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    public static AttributeModifierMap.MutableAttribute prepareAttributes() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20D)
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.8F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5F)
                .createMutableAttribute(Attributes.ARMOR, 5D);
    }

    public boolean isPotionApplicable(EffectInstance potioneffectIn) {
        return potioneffectIn.getPotion() != Effects.POISON && potioneffectIn.getPotion() != Effects.WITHER && super.isPotionApplicable(potioneffectIn);
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source)
                && source.isFireDamage()
                && source == DamageSource.IN_WALL
                && source == DamageSource.CACTUS
                && source == DamageSource.DROWN
                && source == DamageSource.FALLING_BLOCK
                && source == DamageSource.ANVIL
                && source == DamageSource.LAVA
                && source == DamageSource.FALL
                && source == DamageSource.SWEET_BERRY_BUSH;
    }

    protected PathNavigator createNavigator(World worldIn) {
        return new PoePathNavigator(this, worldIn);
    }

    /*public boolean isCharging() {
        return this.dataManager.get(CHARGING);
    }

    public void setCharging(boolean moving) {
        this.dataManager.set(CHARGING, moving);
    }

    public boolean isDaytimeMode() {
        return this.dataManager.get(IS_DAYTIME_MODE);
    }

    public void setDaytimeMode(boolean moving) {
        this.dataManager.set(IS_DAYTIME_MODE, moving);
    }

    /**
     * If the Poe can be pushed
     */
    @Override
    public boolean canBePushed() {
        return false;
    }

    /**
     * If the Poe can collide with other entities and or it's target.
     * Setting to yes, allows it to follow it's attack set of discombobulating the target of where it's at and where it's coming from.
     */
    @Override
    protected void collideWithEntity(Entity entity) {
    }

    /**
     * Registering Poe Entity AI tasks with priority levels in which to follow through with.
     */
    protected void registerGoals() {
        // Set to 0 for "Do not swim at all"
        this.goalSelector.addGoal(0, new SwimGoal(this));
        // An attempt for the mob to avoid sun before disappearing.
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        // Same concept as RestrictSunGoal, however our entity uses these goals to disappear/sleep during the day.
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        // This is how the Poe attacks, the mix of code causes it to circle, sway, pivot, and charge at the target.
        //this.goalSelector.addGoal(3, new PoeChargeAttack(this));
        // The LookAtGoal is here to help the Poe look at it's target, unless you're creative.
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F, 1.0F) {
            public boolean shouldContinueExecuting() {
                // Here is the call to check if the player is creative or not.
                if (this.closestEntity != null && this.closestEntity instanceof PlayerEntity && ((PlayerEntity) this.closestEntity).isCreative()) {
                    return false;
                }
                return super.shouldContinueExecuting();
            }
        });
        // Helper to keep the Poe away from water when roaming and "Walking/gliding" around.
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.6D) {
            public boolean shouldExecute() {
                executionChance = 60;
                return super.shouldExecute();
            }
        });
        // Just looking randomly, nothing special.
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        // Setting the attack goal for the Poe, set to attack Players.
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    /**
     * Called to update the entity's position/logic.
     *
     * General or Main Ticking event that goes on no matter what else is going on, this helps as to check that the
     * AI and Entity are functioning as wanted.
     */
    /*public void livingTick() {
        super.livingTick();
        this.noClip = true;
        this.setNoGravity(true);
        if (!world.isRemote) {
            boolean day = isInDaylight();
            if (day) {
                this.setDaytimeMode(true);
            } else {
                this.setDaytimeMode(false);
                this.setDaytimeCounter(0);
            }
            if (isDaytimeMode()) {
                this.setMotion(Vector3d.ZERO);
                this.setDaytimeCounter(this.getDaytimeCounter() + 1);
                if (getDaytimeCounter() >= 100) {
                    this.setInvisible(true);
                }
            } else {
                this.setInvisible(this.isPotionActive(Effects.INVISIBILITY));
                this.setDaytimeCounter(0);
            }
        } else {
            if (this.getAnimation() == ANIMATION_HIT && this.getAttackTarget() != null) {
                if (this.getDistance(this.getAttackTarget()) < 1.4D && this.getAnimationTick() >= 4 && this.getAnimationTick() < 6) {
                    this.setAnimation(PoeEntity.ANIMATION_HIT);
                    this.attackEntityAsMob(this.getAttackTarget());
                }
            }
        }
        AnimationHandler.INSTANCE.updateAnimations(this);
    }

    /**
     * AI of the Poe disables during the day to make it a night grave crawling creature/mob.
     */
   /* public boolean isAIDisabled() {
        return this.isDaytimeMode();
    }

    /**
     * Is silent during the day as the poe is dormant/sleeping during the day.
     */
    /*public boolean isSilent() {
        return this.isDaytimeMode();
    }

    /**
     * If the poe is in the day light, do some stuff.
     */
    protected boolean isInDaylight() {
        if (this.world.isDaytime() && !this.world.isRemote) {
            float f = this.getBrightness();
            BlockPos blockpos = this.getRidingEntity() instanceof BoatEntity ? (new BlockPos(this.getPosX(), (double) Math.round(this.getPosY()), this.getPosZ())).up() : new BlockPos(this.getPosX(), (double) Math.round(this.getPosY() + 4), this.getPosZ());
            return f > 0.5F && this.world.canSeeSky(blockpos);
        }

        return false;
    }

    /**
     * Setting the poe to have no gravity allowing it to essentially fly or float where ever it goes.
     * This plays in part with the No clip setting to the mob allowing it to keep it self from going to the void.
     */
    public boolean hasNoGravity() {
        return true;
    }

    /**
     * Poe stops all movement when it's day.
     * AKA setting the Vector3d to ZERO "0"
     */
    /*@Override
    public void travel(Vector3d vec) {
        if (this.isDaytimeMode()) {
            super.travel(Vector3d.ZERO);
            return;
        }
        super.travel(vec);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }


    /*@Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(CHARGING, false);
        this.getDataManager().register(IS_DAYTIME_MODE, false);
        this.getDataManager().register(DAYTIME_COUNTER, Integer.valueOf(0));
    }

    public int getDaytimeCounter() {
        return this.getDataManager().get(DAYTIME_COUNTER).intValue();
    }

    public void setDaytimeCounter(int counter) {
        this.getDataManager().set(DAYTIME_COUNTER, counter);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        this.setDaytimeMode(compound.getBoolean("DaytimeMode"));
        this.setDaytimeCounter(compound.getInt("DaytimeCounter"));
        super.readAdditional(compound);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putBoolean("DaytimeMode", this.isDaytimeMode());
        compound.putInt("DaytimeCounter", this.getDaytimeCounter());
        super.writeAdditional(compound);
    }*/

    /*@Override
    public int getAnimationTick() {
        return animationTick;
    }

    @Override
    public void setAnimationTick(int tick) {
        animationTick = tick;
    }

    @Override
    public Animation getAnimation() {
        return currentAnimation;
    }

    @Override
    public void setAnimation(Animation animation) {
        currentAnimation = animation;
    }

    @Override
    public Animation[] getAnimations() {
        return new Animation[]{NO_ANIMATION, ANIMATION_HIT};
    }


    @Override
    public boolean shouldAnimalsFear(Entity entity) {
        return false;
    }*/

    class MoveHelperController extends MovementController {
        public MoveHelperController(PoeEntity poe) {
            super(poe);
        }

        public void tick() {
            if (this.action == MovementController.Action.MOVE_TO) {
                Vector3d vec3d = new Vector3d(this.posX - PoeEntity.this.getPosX(), this.posY - PoeEntity.this.getPosY(), this.posZ - PoeEntity.this.getPosZ());
                double d0 = vec3d.length();
                if (d0 < PoeEntity.this.getBoundingBox().getAverageEdgeLength()) {
                    this.action = MovementController.Action.WAIT;
                    PoeEntity.this.setMotion(PoeEntity.this.getMotion().scale(0.5D));
                } else {
                    PoeEntity.this.setMotion(PoeEntity.this.getMotion().add(vec3d.scale(this.speed * 0.05D / d0)));
                    if (PoeEntity.this.getAttackTarget() == null) {
                        Vector3d vector3d1 = PoeEntity.this.getMotion();
                        PoeEntity.this.rotationYaw = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                        PoeEntity.this.renderYawOffset = PoeEntity.this.rotationYaw;
                    } else {
                        double d2 = PoeEntity.this.getAttackTarget().getPosX() - PoeEntity.this.getPosX();
                        double d1 = PoeEntity.this.getAttackTarget().getPosZ() - PoeEntity.this.getPosZ();
                        PoeEntity.this.rotationYaw = -((float) MathHelper.atan2(d2, d1)) * (180F / (float) Math.PI);
                        PoeEntity.this.renderYawOffset = PoeEntity.this.rotationYaw;
                    }
                }
            }
        }
    }
}
