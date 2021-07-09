package superworldsun.superslegend.entities.mobs.fairy;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.tags.ITag;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.lists.ItemList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

import static superworldsun.superslegend.init.SoundInit.FAIRY_HEAL_ON_TOUCH;
import static superworldsun.superslegend.init.SoundInit.FAIRY_TWINKLE;


public class FairyEntity extends AnimalEntity implements IFlyingAnimal {

    /** Home coordinates where this fairy spawned; will not wander too far from here */
    protected BlockPos home = null;


    public FairyEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type,world);
        this.moveControl = new FlyingMovementController(this, 20, true);
    }

    public static AttributeModifierMap.MutableAttribute prepareAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.FLYING_SPEED, 0.8F)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PlayerEntity.class, 10.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 10.0F, 0.2F));
        this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.6d));
        this.goalSelector.addGoal(4, new WanderGoal());
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
    }

    @Override
    public void restrictTo(BlockPos pos, int distance) {
        teleportTo(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
        home = pos;
    }



    @Override
    protected void doPush(Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            ((PlayerEntity) entityIn).getHealth();
            PlayerEntity playerin = (PlayerEntity) entityIn;
            if (!playerin.isCreative() && playerin.getHealth() < 20) {
                playerin.heal(20);
                this.playSound(FAIRY_HEAL_ON_TOUCH, 0.01f, 0.8f + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);


                removeAfterChangingDimensions();
            }
        }
    }

    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (itemstack.getItem() == Items.GLASS_BOTTLE) {
            p_230254_1_.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, p_230254_1_, ItemList.fairy_bottle.getDefaultInstance());
            p_230254_1_.setItemInHand(p_230254_2_, itemstack1);
            this.remove();
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    @Nonnull
    protected PathNavigator createNavigation(@Nonnull World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(false);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    protected float getStandingEyeHeight(@Nonnull Pose poseIn,@Nonnull EntitySize sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.5F : sizeIn.height * 0.5F;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    //TODO Add this to bomb entity code
    private long lastPlayed;
    @Override
    protected boolean makeFlySound() {
        long soundLength = 7000;
        if (System.currentTimeMillis() - lastPlayed > soundLength) {
            // PLAY SOUND
            lastPlayed = System.currentTimeMillis();
            this.playSound(FAIRY_TWINKLE, 0.01f, 0.1f + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
        }
        return true;
    }

    @Override
    @Nonnull
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    protected void jumpInLiquid(@Nonnull ITag<Fluid> fluidTag) {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
    }


    public void aiStep() {
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.5D, 1.0D));
        }

        if (this.wasTouchingWater) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.5D, 1.0D));
        }

        super.aiStep();
    }

    public void baseTick() {
        super.baseTick();
        this.level.getProfiler().push("mobBaseTick");
        if (this.isAlive() && this.random.nextInt(60) < this.ambientSoundTime++) {
            this.resetAmbientSoundTime();
            this.playAmbientSound();
        }

        this.level.getProfiler().pop();
    }

    protected void playHurtSound(DamageSource source) {
        this.resetAmbientSoundTime();
        super.playHurtSound(source);
    }

    private void resetAmbientSoundTime() {
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.2F);
    }

    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    class WanderGoal extends Goal {
        WanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return FairyEntity.this.navigation.isDone() && FairyEntity.this.random.nextInt(3) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return FairyEntity.this.navigation.isInProgress();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Vector3d vector3d = this.getRandomLocation();
            if (vector3d != null) {
                FairyEntity.this.navigation.moveTo(FairyEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vector3d getRandomLocation() {
            Vector3d vector3d = FairyEntity.this.getViewVector(0.3F);
            int i = 8;
            Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(FairyEntity.this, 3, 3, vector3d, ((float) Math.PI / 2F), 1, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(FairyEntity.this, 6, 6, -3, vector3d, (float) Math.PI / 2F);
        }
    }


}
