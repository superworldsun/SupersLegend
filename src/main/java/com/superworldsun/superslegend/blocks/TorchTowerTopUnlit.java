package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.entities.projectiles.arrows.FireArrowEntity;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TorchTowerTopUnlit extends Block

{
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 9.0D, 13.0D);

    public TorchTowerTopUnlit(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.INVISIBLE;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        pos = pos.below();
        return level.getBlockState(pos).getBlock().getCloneItemStack(state, target, level, pos, player);
    }

    public boolean canSurvive(BlockState state, LevelAccessor world, BlockPos pos)
    {
        return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.below()).is(BlockInit.TORCH_TOWER.get()));
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState pState) {
        level.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
    }

    //TODO, add back when these entities are re added
    /*public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if(entityIn instanceof FireArrowEntity || entityIn instanceof MagicFireArrowEntity ||
                entityIn instanceof com.superworldsun.superslegend.entities.projectiles.magic.FireballEntity)
        {
            worldIn.setBlock(pos , BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 1);
            worldIn.playSound((Player) null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        if(entityIn instanceof Arrow || entityIn instanceof BoomerangEntity
                || entityIn instanceof MagicBoomerangEntity || entityIn instanceof WWBoomerangEntity)
        {
            if (entityIn.isOnFire())
            {
                worldIn.setBlock(pos , BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 1);
                worldIn.playSound((Player)null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }*/

    @Override
    public InteractionResult use(BlockState blockstate, Level worldIn, BlockPos pos, Player playerentity, InteractionHand hand, BlockHitResult blocktrace) {
        ItemStack itemstack = playerentity.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE &&  item != ItemInit.BLUE_CANDLE.get() && item != ItemInit.RED_CANDLE.get())
        {
            return super.use(blockstate, worldIn, pos, playerentity, hand, blocktrace);
        }
        else
        {
            onCaughtFire(blockstate, worldIn, pos, blocktrace.getDirection(), playerentity);
            worldIn.setBlock(pos, BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState(), 1);
            worldIn.playSound((Player) null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!playerentity.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, playerentity, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(hand);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos neighborPos, boolean flag)
    {
        //TODO, probably not needed but couldnt port
        //DebugPacketSender.sendNeighborsUpdatePacket(world, pos);

        if (!canSurvive(state, world, pos))
        {
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }
}
