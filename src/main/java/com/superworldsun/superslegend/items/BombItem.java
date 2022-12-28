package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.entities.projectiles.bombs.EntityBomb;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BombItem extends NonEnchantItem {
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    public BombItem(Item.Properties properties) {
        super(properties);
    }

    //TODO Make it so the bomb can bu used when the player is holding 2 different items,
    // had problems if a player has bomb in offhand and does allow the use of bomb with another item
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) 
    {
        if(!worldIn.isClientSide && player.hasItemInSlot(EquipmentSlotType.MAINHAND) && !player.hasItemInSlot(EquipmentSlotType.OFFHAND))
        {
            if (player.isShiftKeyDown())
                {
                    EntityBomb entity = new EntityBomb(EntityTypeInit.BOMB.get(), worldIn);
                    entity.setPos(player.getX(), player.getY(), player.getZ());
                    worldIn.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                    worldIn.addFreshEntity(entity);
                }
            else
                {
                    EntityBomb bombEntity = new EntityBomb(player, worldIn);
                    float pitch = 0;
                    float throwingForce = 0.7F;
                    bombEntity.shootFromRotation(player, player.xRot, player.yRot, pitch, throwingForce, 0.9F);
                    worldIn.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                    worldIn.addFreshEntity(bombEntity);
                }
            if  (!player.isCreative())
            {
                player.getMainHandItem().shrink(1);
            }
        }
        if(!worldIn.isClientSide && !player.hasItemInSlot(EquipmentSlotType.MAINHAND) && player.hasItemInSlot(EquipmentSlotType.OFFHAND))
        {
            if (player.isShiftKeyDown())
            {
                EntityBomb entity = new EntityBomb(EntityTypeInit.BOMB.get(), worldIn);
                entity.setPos(player.getX(), player.getY(), player.getZ());
                worldIn.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                worldIn.addFreshEntity(entity);
            }
            else
            {
                EntityBomb bombEntity = new EntityBomb(player, worldIn);
                float pitch = 0;
                float throwingForce = 0.7F;
                bombEntity.shootFromRotation(player, player.xRot, player.yRot, pitch, throwingForce, 0.9F);
                worldIn.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                worldIn.addFreshEntity(bombEntity);
            }
            if  (!player.isCreative())
            {
                player.getOffhandItem().shrink(1);
            }
        }
        return super.use(worldIn, player, handIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.BLUE + "Overpower your enemies with an explosive blast"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Right-Click to throw"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Sneak+Right-Click to Drop Bomb"));
        list.add(new StringTextComponent(TextFormatting.RED + "Requires a free hand to throw"));
    }

    /*if(!player.isCreative() && player.getOffhandItem().getItem().equals(ItemInit.BOMB))
    {
        player.getOffhandItem().shrink(1);
    }
            else if(!player.isCreative() && player.getMainHandItem().getItem().equals(ItemInit.BOMB))
    {
        player.getMainHandItem().shrink(1);
    }*/
}

