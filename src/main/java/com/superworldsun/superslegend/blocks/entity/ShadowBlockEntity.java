package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.registries.BlockEntityInit;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;

public class ShadowBlockEntity extends BlockEntity  {
    private BlockState disguise;

    public ShadowBlockEntity(BlockEntityType<?> pType, BlockPos pos, BlockState state) {
        super(pType, pos, state);
    }

    public ShadowBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.SHADOW_ENTITY.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return Shapes.block().bounds().move(getBlockPos());
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (disguise != null) {
            compoundTag.putInt("disguise", Block.getId(disguise));
        }
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag != null && compoundTag.contains("disguise")) {
            disguise = Block.stateById(compoundTag.getInt("disguise")); //here

        }
    }
    public BlockState getDisguise() {
        return disguise;
    }

    public void setDisguise(BlockState newDisguise) {
        if (newDisguise != null) {
            this.disguise = newDisguise;
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                // A disguise can have different skylight/light-blocking behavior than the
                // placeholder state that existed when this block entity was first placed.
                level.getLightEngine().checkBlock(getBlockPos());
            }
        }
    }


    public BlockState getAppearance() {
        setDisguise((disguise != null ? disguise : BlockInit.SHADOW_MODEL_BLOCK.get().defaultBlockState()));
        return getDisguise();
    }

    public static BlockEntityType<ShadowBlockEntity> createShadowType() {
        return BlockEntityType.Builder.of(ShadowBlockEntity::new, BlockInit.SHADOW_BLOCK.get()).build(null);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (disguise != null)
            tag.putInt("disguise", Block.getId(disguise));
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        disguise = Block.stateById(tag.getInt("disguise"));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
