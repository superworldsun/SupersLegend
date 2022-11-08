package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.entities.projectiles.boomerang.BoomerangEntity;
import com.superworldsun.superslegend.entities.projectiles.boomerang.RegularBoomerang;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Boomerang extends Item {


    public Boomerang(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    /*@Override
    protected boolean isInGroup(ItemGroup group) {
        return SupersLegendConfig.COMMON.boomerangsEnabled.get() && super.isInGroup(group);
    }*/

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.getItemInHand(hand).getDamageValue() == 0) {
            BoomerangEntity boomerangEntity = new RegularBoomerang(world, player, player.getItemInHand(hand), hand);

            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ALTTP_BOOMERANG_FLY_LOOP.get(), SoundCategory.PLAYERS, 0.6f, 1.0f);

            world.addFreshEntity(boomerangEntity);
            player.setItemInHand(hand, ItemStack.EMPTY);
        }
        return ActionResult.success(player.getItemInHand(hand));
    }
}