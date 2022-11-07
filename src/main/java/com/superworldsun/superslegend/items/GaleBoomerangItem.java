package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.entities.projectiles.boomerang.GaleBoomerang;
import com.superworldsun.superslegend.entities.projectiles.boomerang.GaleBoomerangEntity;
import com.superworldsun.superslegend.entities.projectiles.boomerang.WWBoomerang;
import com.superworldsun.superslegend.entities.projectiles.boomerang.WWBoomerangEntity;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GaleBoomerangItem extends Item {


    public GaleBoomerangItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    /*@Override
    protected boolean isInGroup(ItemGroup group) {
        return SupersLegendConfig.COMMON.boomerangsEnabled.get() && super.isInGroup(group);
    }*/

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.getItemInHand(hand).getDamageValue() == 0) {
            GaleBoomerangEntity galeboomerangEntity = new GaleBoomerang(world, player, player.getItemInHand(hand), hand);

            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.OOT_BOOMERANG_THROW.get(), SoundCategory.PLAYERS, 0.6f, 1.0f);

            world.addFreshEntity(galeboomerangEntity);
            player.setItemInHand(hand, ItemStack.EMPTY);
        }
        return ActionResult.success(player.getItemInHand(hand));
    }
}