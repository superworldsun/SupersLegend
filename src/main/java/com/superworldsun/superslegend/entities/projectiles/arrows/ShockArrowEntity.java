package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.config.SupersLegendConfig;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;


public class ShockArrowEntity extends AbstractArrowEntity {

    public ShockArrowEntity(EntityType<? extends ShockArrowEntity> type, World world) {
        super(type, world);
    }

    public ShockArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntityTypeInit.SHOCK_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 2.0F);
    }

    public ShockArrowEntity(World worldIn, double x, double y, double z) {
        super(EntityTypeInit.SHOCK_ARROW.get(), x, y, z, worldIn);
    }


    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.SHOCK_ARROW.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    public void tick() {
        super.tick();

        if (!this.inGround) {
            this.level.addParticle(ParticleTypes.BUBBLE_POP, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        LivingEntity livingentity = (LivingEntity) entity;
        if(livingentity instanceof LivingEntity)
        {
            System.out.println("living entity hit");
            int armorPartsEquipped = 0;

            if (livingentity.getItemBySlot(EquipmentSlotType.HEAD).getItem() == Items.IRON_HELMET)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.HEAD).getItem() == Items.GOLDEN_HELMET)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.HEAD).getItem() == Items.CHAINMAIL_HELMET)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.HEAD).getItem() == Items.NETHERITE_HELMET)
                armorPartsEquipped++;

            if (livingentity.getItemBySlot(EquipmentSlotType.CHEST).getItem() == Items.IRON_CHESTPLATE)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.CHEST).getItem() == Items.GOLDEN_CHESTPLATE)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.CHEST).getItem() == Items.CHAINMAIL_CHESTPLATE)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.CHEST).getItem() == Items.NETHERITE_CHESTPLATE)
                armorPartsEquipped++;

            if (livingentity.getItemBySlot(EquipmentSlotType.LEGS).getItem() == Items.IRON_LEGGINGS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.LEGS).getItem() == Items.GOLDEN_LEGGINGS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.LEGS).getItem() == Items.CHAINMAIL_LEGGINGS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.LEGS).getItem() == Items.NETHERITE_LEGGINGS)
                armorPartsEquipped++;

            if (livingentity.getItemBySlot(EquipmentSlotType.FEET).getItem() == Items.IRON_BOOTS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.FEET).getItem() == Items.GOLDEN_BOOTS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.FEET).getItem() == Items.CHAINMAIL_BOOTS)
                armorPartsEquipped++;
            if (livingentity.getItemBySlot(EquipmentSlotType.FEET).getItem() == Items.NETHERITE_BOOTS)
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
            if(livingentity.level.isClientSide)
                return;
            if(livingentity instanceof CreeperEntity && SupersLegendConfig.getInstance().shockArrowCreeper()) {
                LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create((ServerWorld) livingentity.level);
                livingentity.thunderHit((ServerWorld) livingentity.level, lightningBoltEntity);
            }
            this.getBaseDamage();
            if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }
    }

    protected void doPostHurtEffects(LivingEntity entity) {
        BlockPos currentPos = entity.blockPosition();
    	//System.out.println("Client:" + entity.world.isRemote);

    	//LightningBoltEntity l1 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, entity.world);
    	//l1.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), 0, 0);
    	//entity.world.addEntity(l1);
        ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GREEN_HOLY_RING.get(), entity).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (!stack.isEmpty())
        {
        }
        else
        {
            entity.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_SHOCK.get(), SoundCategory.PLAYERS, 1f, 1f);
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 50, false, true));
        }
	}
	
	public static EntityType<ShockArrowEntity> createEntityType()
	{
		return EntityType.Builder.<ShockArrowEntity>of(ShockArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":shock_arrow");
	}
}
