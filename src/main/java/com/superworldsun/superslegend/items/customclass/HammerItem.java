package com.superworldsun.superslegend.items.customclass;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.Mod;

import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public abstract class HammerItem extends TieredItem implements Vanishable {

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public HammerItem(Tier itemTier, int attackDamageBonus, Properties properties) {
        super(itemTier, properties);
        float attackDamage = attackDamageBonus + itemTier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        defaultModifiers = builder.build();
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        // only for hammers
        if (!(event.getItemStack().getItem() instanceof HammerItem)) {
            return;
        }

        HammerItem hammerItem = (HammerItem) event.getItemStack().getItem();

        if (event.getEntity().getCooldowns().isOnCooldown(hammerItem)) {
            // can't click when on cooldown
            event.setUseItem(Event.Result.DENY);
            event.setUseBlock(Event.Result.DENY);
            event.setCanceled(true);
            return;
        }

        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        event.getEntity().getCooldowns().addCooldown(hammerItem, hammerItem.getLeftClickCooldown());

        if (blockState.is(TagInit.FRAGILE)) {
            event.getLevel().destroyBlock(event.getPos(), false, event.getEntity());
            Block.dropResources(blockState, event.getLevel(), event.getPos());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClickInput(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Item mainHandItem = minecraft.player.getMainHandItem().getItem();

        // only for hammers
        if (!(mainHandItem instanceof HammerItem)) {
            return;
        }

        if (minecraft.player.getCooldowns().isOnCooldown(mainHandItem)) {
            // can't click when on cooldown
            event.setCanceled(true);
            event.setSwingHand(false);
            return;
        }

        HammerItem hammerItem = (HammerItem) mainHandItem;
        minecraft.player.getCooldowns().addCooldown(mainHandItem, hammerItem.getLeftClickCooldown());
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player playerEntity) {
        return !playerEntity.isCreative();
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack itemStack, BlockState blockState) {
        if (blockState.is(Blocks.COBWEB)) {
            return 15.0F;
            //TODO, not sure how to check block material
            // TODO: Make your own custom tags and call them here to check the materials
        } else if(blockState.is(Tags.Blocks.GLASS)) {
            return 1.0F;
            // material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !blockState.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F
        } else {
            return 1.5F;
        }
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockState) {
        return blockState.is(Blocks.COBWEB);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, @NotNull LivingEntity attackingEntity, @NotNull LivingEntity targetEntity) {
        itemStack.hurtAndBreak(1, targetEntity, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack itemStack, @NotNull Level level, BlockState blockState, @NotNull BlockPos blockPos, @NotNull LivingEntity livingEntity) {
        if (blockState.getDestroySpeed(level, blockPos) != 0.0F) {
            itemStack.hurtAndBreak(2, livingEntity, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slotType) {
        return slotType == EquipmentSlot.MAINHAND ? this.defaultModifiers : ImmutableMultimap.of();
    }

    protected abstract int getLeftClickCooldown();
}