package com.superworldsun.superslegend.items.weapons.swords;

import com.superworldsun.superslegend.items.customclass.ItemCustomSword;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.ItemToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class GiantsKnife extends ItemCustomSword {
    public GiantsKnife(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(entity instanceof Player)
        {
            Player player = (Player)entity;
            ItemStack equipped = player.getMainHandItem();
            {
                if(stack == equipped)
                {
                    if(player.hasItemInSlot(EquipmentSlot.OFFHAND))
                    {
                        player.spawnAtLocation(player.getOffhandItem());
                        player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if(this.getTier() == ItemToolTiers.BROKEN_GIANTS_KNIFE) {
            return false;
        }

        if(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1) {
            super.hurtEnemy(itemStack, attacked, attacker);
        }

        else {
            ItemStack	newItemStack = new ItemStack(ItemInit.BROKEN_GIANTS_KNIFE.get());
            newItemStack.setDamageValue(newItemStack.getMaxDamage());
            attacker.setItemSlot(EquipmentSlot.MAINHAND, newItemStack);
            attacker.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockstate, BlockPos pos, LivingEntity entity) {
        if(this.getTier() == ItemToolTiers.BROKEN_GIANTS_KNIFE) {
            return false;
        }

        if(itemStack.getDamageValue() < itemStack.getMaxDamage() - 2) {
            super.mineBlock(itemStack, level, blockstate, pos, entity);
        }
        else {
            ItemStack	newItemStack = new ItemStack(ItemInit.BROKEN_GIANTS_KNIFE.get());
            newItemStack.setDamageValue(newItemStack.getMaxDamage());
            entity.setItemSlot(EquipmentSlot.MAINHAND, newItemStack);
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("A Large Sword that requires two hands to wield").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
