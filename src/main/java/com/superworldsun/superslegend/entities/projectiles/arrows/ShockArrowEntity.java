package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

public class ShockArrowEntity extends AbstractArrow
{
    public ShockArrowEntity(EntityType<? extends ShockArrowEntity> type, Level level)
    {
        super(type, level);
    }

    public ShockArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.SHOCK_ARROW.get(), shooter, worldIn);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        setBaseDamage(4.0D);
    }

    @Override
    protected @NotNull ItemStack getPickupItem()
    {
        return new ItemStack(ItemInit.ICE_ARROW.get());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.inGround) {
            this.level().addParticle(ParticleTypes.BUBBLE_POP, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        LivingEntity livingentity = (LivingEntity) entity;
        if(livingentity instanceof LivingEntity)
        {
            System.out.println("living entity hit");
            int armorPartsEquipped = 0;

            if (livingentity.getItemBySlot(EquipmentSlot.HEAD).getItem() == Items.IRON_HELMET)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.HEAD).getItem() == Items.GOLDEN_HELMET)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.HEAD).getItem() == Items.CHAINMAIL_HELMET)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.HEAD).getItem() == Items.NETHERITE_HELMET)
                armorPartsEquipped++;

            if (livingentity.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.IRON_CHESTPLATE)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.GOLDEN_CHESTPLATE)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.CHAINMAIL_CHESTPLATE)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.NETHERITE_CHESTPLATE)
                armorPartsEquipped++;

            if (livingentity.getItemBySlot(EquipmentSlot.LEGS).getItem() == Items.IRON_LEGGINGS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.LEGS).getItem() == Items.GOLDEN_LEGGINGS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.LEGS).getItem() == Items.CHAINMAIL_LEGGINGS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.LEGS).getItem() == Items.NETHERITE_LEGGINGS)
                armorPartsEquipped++;

            if (livingentity.getItemBySlot(EquipmentSlot.FEET).getItem() == Items.IRON_BOOTS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.FEET).getItem() == Items.GOLDEN_BOOTS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.FEET).getItem() == Items.CHAINMAIL_BOOTS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlot.FEET).getItem() == Items.NETHERITE_BOOTS)
                armorPartsEquipped++;

            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GREEN_HOLY_RING.get(), livingentity).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            if (stack.isEmpty())
            {
                if (entity.isAlive()) {
                    if (armorPartsEquipped == 1) {
                        this.setBaseDamage(this.getBaseDamage() * 3.0F);
                    } else if (armorPartsEquipped == 2) {
                        this.setBaseDamage(this.getBaseDamage() * 4.0F);
                    } else if (armorPartsEquipped == 3) {
                        this.setBaseDamage(this.getBaseDamage() * 5.0F);
                    } else if (armorPartsEquipped == 4) {
                        this.setBaseDamage(this.getBaseDamage() * 6.5F);
                    }
                }
            }
            if (!stack.isEmpty())
            {
                this.setBaseDamage(this.getBaseDamage() * 1.0F);
            }
        }

        super.onHitEntity(result);
        if (entity instanceof LivingEntity) {
            if(livingentity.level().isClientSide)
                return;
            if(livingentity instanceof Creeper) {
                LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create((ServerLevel) livingentity.level());
                livingentity.thunderHit((ServerLevel) livingentity.level(), lightningBoltEntity);
            }
            this.getBaseDamage();
            if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }
        //TODO, turn this part back on when config is re added
        /*super.onHitEntity(result);
        if (entity instanceof LivingEntity) {
            if(livingentity.level().isClientSide)
                return;
            if(livingentity instanceof Creeper && SupersLegendConfig.getInstance().shockArrowCreeper()) {
                LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create((ServerLevel) livingentity.level());
                livingentity.thunderHit((ServerLevel) livingentity.level(), lightningBoltEntity);
            }
            this.getBaseDamage();
            if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }*/
    }

    protected void doPostHurtEffects(LivingEntity entity) {
        BlockPos currentPos = entity.blockPosition();
        ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GREEN_HOLY_RING.get(), entity).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (!stack.isEmpty())
        {
        }
        else
        {
            entity.level().playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_SHOCK.get(), SoundSource.PLAYERS, 1f, 1f);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 50, false, true, false));
        }
    }
}
