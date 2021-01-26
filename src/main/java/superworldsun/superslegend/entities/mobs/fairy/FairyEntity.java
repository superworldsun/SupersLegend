package superworldsun.superslegend.entities.mobs.fairy;

import com.google.common.cache.RemovalNotification;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.EntityType;
import net.minecraft.world.server.ServerWorld;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.lists.ItemList;

import javax.annotation.Nullable;


public class FairyEntity extends AnimalEntity implements IFlyingAnimal{

    public FairyEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type,world);
    }

    @Nullable
    @Override
    public FairyEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    public static AttributeModifierMap.MutableAttribute prepareAttributes()
    {
        return LivingEntity.registerAttributes()
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.0D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3d);

    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 0.6d));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 10.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F, 0.2F));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    protected boolean isDespawnPeaceful() {
        return false;
    }

    public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
        if (itemstack.getItem() == Items.GLASS_BOTTLE) {
            p_230254_1_.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.fill(itemstack, p_230254_1_, ItemList.fairy_bottle.getDefaultInstance());
            p_230254_1_.setHeldItem(p_230254_2_, itemstack1);
            this.remove();
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.func_230254_b_(p_230254_1_, p_230254_2_);
        }
    }

    public void livingTick() {
        /*if (this.onGround) {
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
        }*/

        if (this.world.isRemote) {
            if (this.rand.nextInt(28) == 0 && !this.isSilent()) {
                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, this.getSoundCategory(), 0.2F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                //this.world.addParticle(ParticleTypes.POOF, this.getPosXRandom(0.1D), this.getPosYRandom(), this.getPosZRandom(0.1D), 0.0D, 0.0D, 0.0D);
            }


        }

        super.livingTick();
    }

    public void baseTick() {
        super.baseTick();
        this.world.getProfiler().startSection("mobBaseTick");
        if (this.isAlive() && this.rand.nextInt(60) < this.livingSoundTime++) {
            this.func_241821_eG();
            this.playAmbientSound();
        }

        this.world.getProfiler().endSection();
    }

    protected void playHurtSound(DamageSource source) {
        this.func_241821_eG();
        super.playHurtSound(source);
    }

    private void func_241821_eG() {
        this.livingSoundTime = -this.getTalkInterval();
    }

}
