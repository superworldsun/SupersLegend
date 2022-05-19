package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.common.util.Constants;

import java.util.Objects;


public class TPBokoblinSpawnEgg extends Item

{
    public TPBokoblinSpawnEgg(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    //A different way to do spawn eggs, some people do a class thats an auto-generator for all custom mod entities
    //The only disadvantage is that you will need to create a different spawnegg class for each entity
    //Don't forget to check the PacketHandler/PacketSpawn, that is required for proper spawning


    /**
     * Called when this item is used when targetting a Block
     */
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);
            TileEntity tileentity = world.getBlockEntity(blockpos);
            if (tileentity instanceof MobSpawnerTileEntity) {
                AbstractSpawner abstractspawner = ((MobSpawnerTileEntity) tileentity).getSpawner();
                abstractspawner.setEntityId(EntityTypeInit.TP_BOKOBLIN.get());
                tileentity.setChanged();
                world.sendBlockUpdated(blockpos, blockstate, blockstate, Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
                itemstack.shrink(1);
                return ActionResultType.SUCCESS;
            }

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            if (EntityTypeInit.TP_BOKOBLIN.get().spawn((ServerWorld) world, itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                itemstack.shrink(1);
            }

            return ActionResultType.SUCCESS;
        }
    }

}
