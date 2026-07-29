package com.superworldsun.superslegend.items.customclass;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

    @Override
    public boolean isBookEnchantable(@NotNull ItemStack stack, @NotNull ItemStack book) {
        return false;
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getLevel().isClientSide) {
            return;
        }

        if (!(event.getItemStack().getItem() instanceof HammerItem hammerItem)) {
            return;
        }

        Player player = event.getEntity();
        if (player.getCooldowns().isOnCooldown(hammerItem)) {
            event.setUseItem(Event.Result.DENY);
            event.setUseBlock(Event.Result.DENY);
            event.setCanceled(true);
            return;
        }

        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        player.getCooldowns().addCooldown(hammerItem, hammerItem.getLeftClickCooldown());

        if (!blockState.isAir() && blockState.getFluidState().isEmpty()) {
            event.getLevel().playSound(null, event.getPos(), hammerItem.getHitSound(),
                    SoundSource.PLAYERS, 0.8F, 1.0F);

            if (event.getLevel() instanceof ServerLevel serverLevel) {
                spawnHammerShockwaveParticles(serverLevel, event.getPos(), blockState, event.getFace());
            }
        }

        if (blockState.is(TagInit.FRAGILE)) {
            event.getLevel().destroyBlock(event.getPos(), true, player);
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)
                || event.getSource().getDirectEntity() != player
                || player.level().isClientSide) {
            return;
        }

        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof HammerItem hammerItem)) {
            return;
        }

        if (player.getCooldowns().isOnCooldown(hammerItem)) {
            event.setCanceled(true);
            return;
        }

        player.getCooldowns().addCooldown(hammerItem, hammerItem.getLeftClickCooldown());
        event.getEntity().level().playSound(null, event.getEntity().blockPosition(),
                hammerItem.getHitSound(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClickInput(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        Item mainHandItem = minecraft.player.getMainHandItem().getItem();

        if (!(mainHandItem instanceof HammerItem hammerItem)) {
            return;
        }

        if (minecraft.player.getCooldowns().isOnCooldown(mainHandItem)) {
            event.setCanceled(true);
            event.setSwingHand(false);
            return;
        }

        minecraft.player.getCooldowns().addCooldown(mainHandItem, hammerItem.getLeftClickCooldown());
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity,
                              int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, level, entity, itemSlot, isSelected);
        if (level.isClientSide || !isSelected || !(entity instanceof Player player)) {
            return;
        }

        ItemStack offhandStack = player.getOffhandItem();
        if (!offhandStack.isEmpty()) {
            player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
            player.drop(offhandStack, false);
        }
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player playerEntity)
    {
        // Creative mode would otherwise instantly destroy normal blocks before the hammer impact logic runs.
        return !playerEntity.isCreative();
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack itemStack, @NotNull BlockState blockState) {
        // Vegetation remains breakable, but hammers cannot continuously mine normal solid blocks.
        return isHammerBreakablePlant(blockState) ? 1.5F : 0.0F;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, @NotNull LivingEntity attackingEntity, @NotNull LivingEntity targetEntity)
    {
        itemStack.hurtAndBreak(1, targetEntity, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slotType)
    {
        return slotType == EquipmentSlot.MAINHAND ? this.defaultModifiers : ImmutableMultimap.of();
    }

    protected abstract int getLeftClickCooldown();

    protected abstract SoundEvent getHitSound();

    private static boolean isHammerBreakablePlant(BlockState blockState) {
        return blockState.is(TagInit.HAMMER_BREAKABLE_PLANTS)
                || blockState.getBlock() instanceof BushBlock;
    }

    /**
     * Spawns shockwave particles when hammer hits a block
     */
    private static void spawnHammerShockwaveParticles(ServerLevel level, BlockPos hitPos,
                                                       BlockState blockState, Direction hitFace) {
        Direction face = hitFace == null ? Direction.UP : hitFace;
        BlockParticleOption particleOption = new BlockParticleOption(ParticleTypes.BLOCK, blockState);
        double startX = hitPos.getX() + 0.5D + face.getStepX() * 0.51D;
        double startY = hitPos.getY() + 0.5D + face.getStepY() * 0.51D;
        double startZ = hitPos.getZ() + 0.5D + face.getStepZ() * 0.51D;
        double spreadX = face.getAxis() == Direction.Axis.X ? 0.08D : 0.42D;
        double spreadY = face.getAxis() == Direction.Axis.Y ? 0.08D : 0.42D;
        double spreadZ = face.getAxis() == Direction.Axis.Z ? 0.08D : 0.42D;

        level.sendParticles(particleOption, startX, startY, startZ,
                28, spreadX, spreadY, spreadZ, 0.18D);
    }
}
