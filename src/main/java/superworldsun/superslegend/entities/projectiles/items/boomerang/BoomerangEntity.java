package superworldsun.superslegend.entities.projectiles.items.boomerang;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.config.SupersLegendConfig;
import superworldsun.superslegend.init.SoundInit;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BoomerangEntity extends Entity {

    private BlockPos activatedPos;
    protected boolean isBouncing;
    private double bounceFactor;
    private float prevBoomerangRotation;
    private boolean turningAround;
    protected int timeBeforeTurnAround;
    List<ItemEntity> itemsPickedUp;
    private ItemStack selfStack;
    private Hand hand;
    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(BoomerangEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Optional<UUID>> RETURN_UNIQUE_ID = EntityDataManager.createKey(BoomerangEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    public BoomerangEntity(EntityType<BoomerangEntity> type, World world) {
        super(type, world);
        this.bounceFactor = 0.84999999999999998D;
        this.turningAround = false;
        this.timeBeforeTurnAround = 30;
        this.itemsPickedUp = new ArrayList<ItemEntity>();
        this.hand = Hand.MAIN_HAND;
    }

    public BoomerangEntity(EntityType<BoomerangEntity> type, World worldIn, PlayerEntity entity, ItemStack itemstack, Hand hand) {
        this(type, worldIn);
        this.selfStack = itemstack;
        this.setRotation(entity.rotationYaw, entity.rotationPitch);
        double x = -MathHelper.sin((entity.rotationYaw * 3.141593F) / 180F);
        double z = MathHelper.cos((entity.rotationYaw * 3.141593F) / 180F);

        double motionX = +0.5D * x * (double) MathHelper.cos((entity.rotationPitch / 180F) * 3.141593F);
        double motionY = -0.5D * (double) MathHelper.sin((entity.rotationPitch / 180F) * 3.141593F);
        double motionZ = +0.5D * z * (double) MathHelper.cos((entity.rotationPitch / 180F) * 3.141593F);
        this.setMotion(new Vector3d(+motionX, motionY, +motionZ));
        setPosition(entity.getPosX(), this.getReturnEntityY(entity), entity.getPosZ());
        prevPosX = getPosX();
        prevPosY = getPosY();
        prevPosZ = getPosZ();
        this.isBouncing = false;
        this.turningAround = false;
        this.hand = hand;
        this.setReturnToId(entity.getUniqueID());
    }

    public double getReturnEntityY(PlayerEntity entity) {
        return entity.getPosY() + entity.getEyeHeight() - 0.10000000149011612D;
    }

    @Override
    @Deprecated
    public void tick() {
        PlayerEntity player = this.getReturnTo();

        //BlockPos currentPos = this.getPosition();
        //this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_FLY_LOOP, SoundCategory.PLAYERS, 1f, 1f);

        if(this.ticksExisted % 11 == 0)
        {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_FLY_LOOP, SoundCategory.PLAYERS, 0.4f, 1.0f);
        }

        Vector3d vec3d1 = this.getPositionVec();
        Vector3d vec3d = this.getPositionVec().add(this.getMotion());
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d1, vec3d, BlockMode.OUTLINE, FluidMode.ANY, null));

        if (raytraceresult != null) {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos pos = new BlockPos(raytraceresult.getHitVec());
                BlockState state = world.getBlockState(pos);

                if (state.getMaterial() == Material.PLANTS && SupersLegendConfig.COMMON.breaksFlowers.get()) {
                    world.destroyBlock(pos, true);
                }
                /*if ((state.getBlock() instanceof GrassBlock) && SupersLegendConfig.COMMON.breaksGrass.get()) {
                    world.destroyBlock(pos, true);
                }*/
                if ((state.getMaterial() == Material.TALL_PLANTS) && SupersLegendConfig.COMMON.breaksTallGrass.get()) {
                    world.destroyBlock(pos, true);
                }
                if ((state.getBlock() instanceof TorchBlock) && SupersLegendConfig.COMMON.breaksTorches.get()) {
                    world.destroyBlock(pos, true);
                }
                if ((state.getBlock() instanceof LeverBlock) && SupersLegendConfig.COMMON.activatesLevers.get()) {
                    if (timeBeforeTurnAround > 0 && SupersLegendConfig.COMMON.turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().onBlockActivated(state, world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof AbstractButtonBlock) && SupersLegendConfig.COMMON.activatesButtons.get()) {
                    if (timeBeforeTurnAround > 0 && SupersLegendConfig.COMMON.turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().onBlockActivated(state, world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof PressurePlateBlock) && SupersLegendConfig.COMMON.activatesPressurePlates.get()) {
                    if (timeBeforeTurnAround > 0 && SupersLegendConfig.COMMON.turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().onBlockActivated(state, world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof TripWireBlock) && SupersLegendConfig.COMMON.activatesTripWire.get()) {
                    if (timeBeforeTurnAround > 0 && SupersLegendConfig.COMMON.turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().onBlockActivated(state, world, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
            }
        }

        if (!turningAround) {
            Vector3d motionBefore = this.getMotion();
            this.move(MoverType.SELF, motionBefore);

            Vector3d motionAfter = this.getMotion();
            double newX = motionAfter.x;
            double newY = motionAfter.y;
            double newZ = motionAfter.z;

            boolean flag = false;
            if (motionAfter.x != motionBefore.x) {
                newX = -motionBefore.x;
                flag = true;
            }
            if (motionAfter.y != motionBefore.y) {
                newY = -motionBefore.y;
                flag = true;
            }
            if (motionAfter.z != motionBefore.z) {
                newZ = -motionBefore.z;
                flag = true;
            }
            if (flag) {
                isBouncing = true;
                this.setMotion(new Vector3d(newX, newY, newZ).mul(bounceFactor, bounceFactor, bounceFactor));
            }

            this.beforeTurnAround(player);

            if (timeBeforeTurnAround-- <= 0) {
                turningAround = true;
            }
        } else if (player != null) {
            double x = player.getPosX() - this.getPosX();
            double y = this.getReturnEntityY(player) - this.getPosY();
            double z = player.getPosZ() - this.getPosZ();
            double d = Math.sqrt(x * x + y * y + z * z);
            if (d < 1.5D) {
                setEntityDead();
            }
            this.setMotion((0.9D * x) / d, (0.5D * y) / d, (0.9D * z) / d);
            this.setPosition(this.getPosX() + this.getMotion().x, this.getPosY() + this.getMotion().y, this.getPosZ() + this.getMotion().z);
        }

        determineRotation();
        prevBoomerangRotation = getBoomerangRotation();
        for (this.setBoomerangRotation(this.getBoomerangRotation() + 36F); this.getBoomerangRotation() > 360F; this.setBoomerangRotation(this.getBoomerangRotation() - 360F)) {
        }
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().expand(0.5D, 0.5D, 0.5D));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity instanceof ItemEntity) {
                itemsPickedUp.add((ItemEntity) entity);
                if (timeBeforeTurnAround > 0 && SupersLegendConfig.COMMON.turnAroundItem.get()) {
                    timeBeforeTurnAround = 0;
                }
                continue;
            }
            if (!(entity instanceof LivingEntity) || entity == player) {
                continue;
            }

            this.onEntityHit(entity, player);

            if (timeBeforeTurnAround > 0 && SupersLegendConfig.COMMON.turnAroundMob.get()) {
                timeBeforeTurnAround = 0;
            }
        }

        Iterator<ItemEntity> iterator = itemsPickedUp.iterator();
        while (iterator.hasNext()) {
            ItemEntity item = iterator.next();
            item.setMotion(0, 0, 0);
            if (item.isAlive()) {
                Vector3d pos = this.getPositionVec();
                item.setPosition(pos.x, pos.y, pos.z);
            }
        }

        super.tick();
    }

    public void beforeTurnAround(PlayerEntity player) {
        // NO-OP
    }

    public void onEntityHit(Entity hitEntity, PlayerEntity player) {
        hitEntity.attackEntityFrom(causeNewDamage(this, player), getDamage(hitEntity, player));
    }

    protected abstract int getDamage(Entity hitEntity, PlayerEntity player);

    public abstract DamageSource causeNewDamage(BoomerangEntity entityboomerang, Entity entity);

    public void setEntityDead() {
        if (this.getReturnTo() != null) {
            if (selfStack != null) {
                if (this.hand == Hand.OFF_HAND) {
                    if (this.getReturnTo().getHeldItemOffhand().isEmpty()) {
                        this.getReturnTo().setHeldItem(Hand.OFF_HAND, selfStack);
                    } else {
                        this.getReturnTo().inventory.addItemStackToInventory(selfStack);
                    }
                } else {
                    this.getReturnTo().inventory.addItemStackToInventory(selfStack);
                }
            }
        }
        super.setDead();
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(ROTATION, 0.0F);
        this.getDataManager().register(RETURN_UNIQUE_ID, Optional.empty());
    }

    public float getBoomerangRotation() {
        return this.getDataManager().get(ROTATION);
    }

    public void setBoomerangRotation(float rotationIn) {
        this.getDataManager().set(ROTATION, rotationIn);
    }

    @Nullable
    public UUID getReturnToId() {
        return this.dataManager.get(RETURN_UNIQUE_ID).orElse(null);
    }

    public void setReturnToId(@Nullable UUID uuid) {
        this.dataManager.set(RETURN_UNIQUE_ID, Optional.ofNullable(uuid));
    }

    @Nullable
    public PlayerEntity getReturnTo() {
        try {
            UUID uuid = this.getReturnToId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean isReturnTo(LivingEntity entityIn) {
        return entityIn == this.getReturnTo();
    }

    public void determineRotation() {
        Vector3d motion = this.getMotion();

        rotationYaw = -57.29578F * (float) Math.atan2(motion.x, motion.z);
        double d1 = Math.sqrt(motion.z * motion.z + motion.x * motion.x);
        rotationPitch = -57.29578F * (float) Math.atan2(motion.y, d1);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.isBouncing = compound.getBoolean("IsBouncing");
        this.bounceFactor = compound.getDouble("BounceFactor");
        this.prevBoomerangRotation = compound.getFloat("PrevBoomerangRotation");
        this.setBoomerangRotation(compound.getFloat("BoomerangRotation"));
        this.turningAround = compound.getBoolean("TurningAround");
        this.timeBeforeTurnAround = compound.getInt("TimeBeforeTurnAround");
        if (compound.contains("xPos") && compound.contains("yPos") && compound.contains("zPos"))
            this.activatedPos = new BlockPos(compound.getInt("xPos"), compound.getInt("yPos"), compound.getInt("zPos"));

        if (compound.contains("ReturnToUUID", 8)) {
            try {
                this.setReturnToId(UUID.fromString(compound.getString("ReturnToUUID")));
            } catch (Throwable t) {
                // NO-OP
            }
        }

        this.selfStack = ItemStack.read(compound.getCompound("SelfStack"));

        ListNBT itemsGathered = compound.getList("ItemsPickedUp", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsGathered.size(); i++) {
            CompoundNBT tag = itemsGathered.getCompound(i);
            ItemEntity item = new ItemEntity(world, 0, 0, 0);
            item.readAdditional(tag);
            this.itemsPickedUp.add(item);
        }

        this.hand = Hand.valueOf(compound.getString("hand"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putBoolean("IsBouncing", isBouncing);
        compound.putDouble("BounceFactor", bounceFactor);
        compound.putFloat("PrevBoomerangRotation", prevBoomerangRotation);
        compound.putFloat("BoomerangRotation", this.getBoomerangRotation());
        compound.putBoolean("TurningAround", turningAround);
        compound.putInt("TimeBeforeTurnAround", timeBeforeTurnAround);
        if (activatedPos != null) {
            compound.putInt("xPos", activatedPos.getX());
            compound.putInt("yPos", activatedPos.getY());
            compound.putInt("zPos", activatedPos.getZ());
        }

        if (this.getReturnToId() == null) {
            compound.putString("ReturnToUUID", "");
        } else {
            compound.putString("ReturnToUUID", this.getReturnToId().toString());
        }

        CompoundNBT selfStackTag = new CompoundNBT();
        selfStack.write(selfStackTag);
        compound.put("SelfStack", selfStackTag);

        ListNBT itemsGathered = new ListNBT();
        for (int i = 0; i < itemsPickedUp.size(); i++) {
            if (itemsPickedUp.get(i) != null) {
                CompoundNBT tag = new CompoundNBT();
                itemsPickedUp.get(i).writeAdditional(compound);
                itemsGathered.add(tag);
            }
        }

        compound.put("ItemsPickedUp", itemsGathered);
        compound.putString("hand", this.hand.toString());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
