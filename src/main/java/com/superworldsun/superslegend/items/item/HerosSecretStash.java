package com.superworldsun.superslegend.items.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HerosSecretStash extends Item {
    public HerosSecretStash(Properties pProperties) {
        super(pProperties);
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {

        PlayerEnderChestContainer enderChest = player.getEnderChestInventory();

        if (enderChest != null)
        {
            if (!level.isClientSide)
            {
                BlockPos currentPos = player.blockPosition();
                level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENDER_CHEST_OPEN, SoundEvents.ENDER_CHEST_OPEN, 1f, 1f);
                player.openMenu(new SimpleNamedContainerProvider((p_220114_1_, p_220114_2_, p_220114_3_) -> {
                    return ChestMenu.threeRows(p_220114_1_, p_220114_2_, enderChest);
                }, CONTAINER_TITLE));
            }
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, player.getItemInHand(hand));
    }*/
}
