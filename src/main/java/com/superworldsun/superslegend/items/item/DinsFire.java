package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class DinsFire extends Item {
    public DinsFire(Properties builder) {
        super(builder);
    }

    /**
     * This function is called when the player stops using the item.
     */

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, net.minecraft.world.level.@NotNull Level level, @NotNull LivingEntity livingEntity) {
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    //TODO The fire attack can be used with a instant right click instead of the full charged right click

    //TODO The fire blast also sets the user on fire so it is set to clear fire, find a better solution if possible

    float manaCost = 6.0F;

    /**
     * If the player has enough mana, and the item has been used for more than 72000 ticks, then set all nearby entities on
     * fire
     */
    
    
    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int remainingUseTicks)
    {
        float manacost = 6F;
        Player player = (Player) livingEntity;
        boolean hasMagic = MagicProvider.hasMagic(player, manacost);
        if (hasMagic)
        {
            if(livingEntity instanceof Player)
            {
                if (!level.isClientSide)
                {
                    fibonacci_sphere(player);

                    AABB targetBox = new AABB(player.position(), player.position()).inflate(6);

                    List<LivingEntity> foundTarget =
                            level.getEntitiesOfClass(LivingEntity.class, targetBox, AVOID_PLAYERS);

                    //SupersLegendMain.LOGGER.info(hasMagic);
                    //SupersLegendMain.LOGGER.info(!foundTarget.isEmpty());
                    //SupersLegendMain.LOGGER.info(getUseDuration(stack));

                    if (!foundTarget.isEmpty() && hasMagic && getUseDuration(stack) >= 72000)
                    {
                        //TODO, idk how to fix random.nextfloat
                        //level.playSound(player, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
                        for (LivingEntity living : foundTarget)
                        {
                            MagicProvider.spendMagic(player, manaCost);
                            player.getCooldowns().addCooldown(this, 16);
                            living.setSecondsOnFire(6);

                            BlockPos playerPos = player.blockPosition();
                            int radius = 10;
                            BlockState blockToReplace = BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState();
                            BlockState blockToReplaceWith = BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState();
                            // Create the fire field around the player
                            // Replace blocks within the field
                            replaceBlocksAroundPlayer(player, level, playerPos, radius, blockToReplace, blockToReplaceWith);

                            player.clearFire();
                        }
                    }
                }
            }
        }
        if (player.isCreative())
        {
            BlockPos playerPos = player.blockPosition();
            int radius = 10;
            BlockState blockToReplace = BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState();
            BlockState blockToReplaceWith = BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState();
            // Create the fire field around the player
            // Replace blocks within the field
            replaceBlocksAroundPlayer(player, level, playerPos, radius, blockToReplace, blockToReplaceWith);
        }
    }

    private void replaceBlocksAroundPlayer(Player player, Level world, BlockPos playerPos, int radius, BlockState blockToReplace, BlockState blockToReplaceWith) {
        for (BlockPos pos : BlockPos.betweenClosed(playerPos.offset(-radius, -radius, -radius), playerPos.offset(radius, radius, radius))) {
            if (pos.distSqr(playerPos) <= radius * radius && world.getBlockState(pos) == blockToReplace) {
                world.setBlock(pos, blockToReplaceWith, 3);
            }
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 72000;
    }

    public void fibonacci_sphere(Player player) {
        int samples = 1000;
        double phi = Math.PI * (3. - Math.sqrt(5.));

        for(int i = 0; i < samples; i++) {
            int i1 = samples - 1;
            int y = 1 - (i / i1) *2;
            double radius = Math.sqrt(1 - y * y);

            double theta = phi * i;

            double x = Math.cos(theta) * radius;
            double z = Math.sin(theta) * radius;

            ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, player.getX() + x, player.getY() + y, player.getZ() + z, 1, 0.0, 0.0, 0.0, 0.1);
        }
    }

    // It's a filter that checks if the entity is a player, and if it is, it checks if the player is in creative mode or is
    // a spectator. If it is, it returns false, otherwise it returns true.

    //TODO, check if player is in creative mode
    private static final Predicate<Entity> AVOID_PLAYERS = (player) -> {
        return !player.isDiscrete() && !player.isSpectator();
        //return !player.isDiscrete() && EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(player);
    };

    /**
     * If the player is using the item in their hand, start using it.
     */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Through Din, you can set the world ablaze").withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Right-Click to use").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("When used, set all entities nearby on fire").withStyle(ChatFormatting.RED));
            tooltip.add(Component.literal("Uses Magic on use").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}