package com.superworldsun.superslegend.items.weapons.other;

import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class DekuStick extends NonEnchantItem {
    public DekuStick(Properties pProperties) {
        super(pProperties);
    }

    //TODO, finish port
    /*@Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
        if(!player.isCreative())
        {
            entity.hurt(DamageSource.GENERIC, 8.0F);
            player.playSound(SoundEvents.ITEM_BREAK, 1F, 1F);

            Random rand = (Random) player.level().random;
            for (int i = 0; i < 10; i++)
            {
                player.level().addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(ItemInit.DEKU_STICK.get())),
                        player.xo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 0.2f,
                        player.yo + rand.nextFloat() * 1 - -1,
                        player.zo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 0.2f,
                        0, 0.105D, 0);
            }
            stack.shrink(1);
        }
        if(player.isCreative())
        {
            entity.hurt(DamageSource.GENERIC, 8.0F);
        }

        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return super.useOn(context);
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        ItemStack stack = context.getItemInHand();
        RayTraceResult raytraceresult = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.NONE);
        if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && level.getBlockState(((BlockRayTraceResult)raytraceresult).getBlockPos()).is(Blocks.FIRE))
        {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.FIRE_IGNITE.get(), SoundSource.PLAYERS, 1f, 1f);
            stack.shrink(1);
            player.addItem(new ItemStack(ItemInit.DEKU_STICK_LIT.get()));

            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && level.getBlockState(((BlockRayTraceResult)raytraceresult).getBlockPos()).is(BlockInit.TORCH_TOWER_TOP_LIT.get()))
        {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.FIRE_IGNITE.get(), SoundSource.PLAYERS, 1f, 1f);
            stack.shrink(1);
            player.addItem(new ItemStack(ItemInit.DEKU_STICK_LIT.get()));

            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        else
        {
            return InteractionResult.FAIL;
        }
    }*/
}
