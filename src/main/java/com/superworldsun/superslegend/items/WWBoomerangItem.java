package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.entities.projectiles.boomerang.BoomerangEntity;
import com.superworldsun.superslegend.entities.projectiles.boomerang.RegularBoomerang;
import com.superworldsun.superslegend.entities.projectiles.boomerang.WWBoomerang;
import com.superworldsun.superslegend.entities.projectiles.boomerang.WWBoomerangEntity;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WWBoomerangItem extends NonEnchantItem {


    public WWBoomerangItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.getItemInHand(hand).getDamageValue() == 0) {
            WWBoomerangEntity wwboomerangEntity = new WWBoomerang(world, player, player.getItemInHand(hand), hand);

            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.WW_BOOMERANG_FLY_LOOP.get(), SoundCategory.PLAYERS, 0.6f, 1.0f);

            world.addFreshEntity(wwboomerangEntity);
            player.setItemInHand(hand, ItemStack.EMPTY);
        }
        return ActionResult.success(player.getItemInHand(hand));
    }
}