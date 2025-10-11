package com.superworldsun.superslegend.items.customclass;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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


    public HammerItem(Tier itemTier, int attackDamageBonus, Properties properties)
    {
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
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        // only for hammers
        if (!(event.getItemStack().getItem() instanceof HammerItem))
        {
            return;
        }

        HammerItem hammerItem = (HammerItem) event.getItemStack().getItem();

        if (event.getEntity().getCooldowns().isOnCooldown(hammerItem))
        {
            // can't click when on cooldown
            event.setUseItem(Event.Result.DENY);
            event.setUseBlock(Event.Result.DENY);
            event.setCanceled(true);
            return;
        }

        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        event.getEntity().getCooldowns().addCooldown(hammerItem, hammerItem.getLeftClickCooldown());
//TODO Find better sounds for Block & Player Hit
        // Play hammer impact sound when hitting any solid block (not air or liquid)
        if (!blockState.isAir() && blockState.getFluidState().isEmpty()) {
            event.getLevel().playSound(null, event.getPos(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.8F, 1.2F);

            // Create shockwave particle effect using the block's particles
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                spawnHammerShockwaveParticles(serverLevel, event.getPos(), blockState, event.getFace());
            }
        }

        if (blockState.is(TagInit.FRAGILE))
        {
            event.getLevel().destroyBlock(event.getPos(), false, event.getEntity());
            Block.dropResources(blockState, event.getLevel(), event.getPos());
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event)
    {
        // Check if attacker is a player using a hammer
        if (!(event.getSource().getEntity() instanceof Player player))
        {
            return;
        }

        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof HammerItem))
        {
            return;
        }

        // Play custom hammer sound for entity hits - this replaces the default hurt sound
        event.getEntity().level().playSound(null, event.getEntity().blockPosition(),
                SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 0.8F);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClickInput(InputEvent.InteractionKeyMappingTriggered event)
    {
        if (!event.isAttack())
        {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Item mainHandItem = minecraft.player.getMainHandItem().getItem();

        // only for hammers
        if (!(mainHandItem instanceof HammerItem))
        {
            return;
        }

        if (minecraft.player.getCooldowns().isOnCooldown(mainHandItem))
        {
            // can't click when on cooldown
            event.setCanceled(true);
            event.setSwingHand(false);
            return;
        }

        HammerItem hammerItem = (HammerItem) mainHandItem;
        minecraft.player.getCooldowns().addCooldown(mainHandItem, hammerItem.getLeftClickCooldown());
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player playerEntity)
    {
        return !playerEntity.isCreative();
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack itemStack, BlockState blockState)
    {
        if (blockState.is(Blocks.COBWEB))
        {
            return 15.0F;
        }
        else
        {
            //TODO, not sure how to check block material
            //Material material = blockState.getMaterial();
            //return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !blockState.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F
            return 1.5F;
        }
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockState)
    {
        return blockState.is(Blocks.COBWEB);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, @NotNull LivingEntity attackingEntity, @NotNull LivingEntity targetEntity)
    {
        itemStack.hurtAndBreak(1, targetEntity, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack itemStack, @NotNull Level level, BlockState blockState, @NotNull BlockPos blockPos, @NotNull LivingEntity livingEntity)
    {
        if (blockState.getDestroySpeed(level, blockPos) != 0.0F)
        {
            itemStack.hurtAndBreak(2, livingEntity, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slotType)
    {
        return slotType == EquipmentSlot.MAINHAND ? this.defaultModifiers : ImmutableMultimap.of();
    }

    protected abstract int getLeftClickCooldown();

    /**
     * Spawns shockwave particles when hammer hits a block
     */
    private static void spawnHammerShockwaveParticles(ServerLevel level, BlockPos hitPos, BlockState blockState, net.minecraft.core.Direction hitFace) {
        RandomSource random = level.getRandom();

        // Create block particle option for the hit block
        BlockParticleOption particleOption = new BlockParticleOption(ParticleTypes.BLOCK, blockState);

        // Get the normal vector of the hit face
        net.minecraft.world.phys.Vec3 faceNormal = net.minecraft.world.phys.Vec3.atLowerCornerOf(hitFace.getNormal());

        // Spawn particles flying away from the hit face
        int particleCount = 20 + random.nextInt(25); // 20-45 particles

        for (int i = 0; i < particleCount; i++) {
            // Random spread around the hit face
            double spreadX = (random.nextDouble() - 0.5) * 1.5; // -0.75 to 0.75
            double spreadY = (random.nextDouble() - 0.5) * 1.5; // -0.75 to 0.75
            double spreadZ = (random.nextDouble() - 0.5) * 1.5; // -0.75 to 0.75

            // Start position on the hit face
            double startX = hitPos.getX() + 0.5 + (hitFace.getStepX() * 0.51) + (spreadX * 0.3);
            double startY = hitPos.getY() + 0.5 + (hitFace.getStepY() * 0.51) + (spreadY * 0.3);
            double startZ = hitPos.getZ() + 0.5 + (hitFace.getStepZ() * 0.51) + (spreadZ * 0.3);

            // Particle velocity in the direction of the hit face normal, with some randomness
            double baseSpeed = 0.4 + random.nextDouble() * 0.3; // 0.4 to 0.7
            double velocityX = faceNormal.x * baseSpeed + spreadX * 0.2;
            double velocityY = faceNormal.y * baseSpeed + spreadY * 0.2;
            double velocityZ = faceNormal.z * baseSpeed + spreadZ * 0.2;

            // Add slight upward bias for better visual effect (except when hitting from below)
            if (hitFace != net.minecraft.core.Direction.DOWN) {
                velocityY += 0.1;
            }

            // Spawn the particle
            level.sendParticles(
                    particleOption,
                    startX, startY, startZ,
                    1, // particle count per spawn
                    velocityX * 0.1, velocityY * 0.1, velocityZ * 0.1, // small random offset
                    Math.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ) // speed
            );
        }
    }
}
