package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.entities.projectiles.bombs.AbstractEntityBomb;
import com.superworldsun.superslegend.entities.projectiles.bombs.EntityBomb;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.util.SupersLegendKeyboardUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class BombItem extends Item {
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    public BombItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isClientSide) {
            if(playerIn.isShiftKeyDown()){
                EntityBomb entity = new EntityBomb(EntityTypeInit.BOMB.get(), worldIn);
                entity.setPos(playerIn.getX(), playerIn.getY(), playerIn.getZ());
                worldIn.playSound(null, playerIn.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                worldIn.addFreshEntity(entity);
            } else {
                EntityBomb bombEntity = new EntityBomb(playerIn, worldIn);
                float pitch = 0;
                float throwingForce = 0.7F;
                bombEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, pitch, throwingForce, 0.9F);
                worldIn.playSound(null, playerIn.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                worldIn.addFreshEntity(bombEntity);
            }
            if(!playerIn.isCreative()) {
                playerIn.getMainHandItem().shrink(1);
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

}

