package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static com.superworldsun.superslegend.blocks.TorchTower.OUTPUT_POWER;

public class TorchTowerTopLit extends Block

{
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 9.0D, 13.0D);

    public TorchTowerTopLit(Properties properties) {
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

    public void animateTick(BlockState pState, Level level, BlockPos pos, RandomSource rand)
    {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.5D;
        double d2 = (double)pos.getZ() + 0.5D;
        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);

        double d3 = (double)pos.getX() + 0.6D;
        double d4 = (double)pos.getY() + 0.4D;
        double d5 = (double)pos.getZ() + 0.6D;
        level.addParticle(ParticleTypes.SMOKE, d3, d4, d5, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D);

        double d6 = (double)pos.getX() + 0.4D;
        double d7 = (double)pos.getY() + 0.4D;
        double d8 = (double)pos.getZ() + 0.4D;
        level.addParticle(ParticleTypes.SMOKE, d6, d7, d8, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d6, d7, d8, 0.0D, 0.0D, 0.0D);

        double  d9 = (double)pos.getX() + 0.6D;
        double d10 = (double)pos.getY() + 0.4D;
        double d11 = (double)pos.getZ() + 0.4D;
        level.addParticle(ParticleTypes.SMOKE, d9, d10, d11, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d9, d10, d11, 0.0D, 0.0D, 0.0D);

        double d12 = (double)pos.getX() + 0.4D;
        double d13 = (double)pos.getY() + .4D;
        double d14 = (double)pos.getZ() + 0.6D;
        level.addParticle(ParticleTypes.SMOKE, d12, d13, d14, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d12, d13, d14, 0.0D, 0.0D, 0.0D);
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
		if(entityIn instanceof ArrowEntity || (entityIn instanceof AbstractArrowEntity && !(entityIn instanceof IceArrowEntity || entityIn instanceof MagicIceArrowEntity) || entityIn instanceof BoomerangEntity
		|| entityIn instanceof MagicBoomerangEntity || entityIn instanceof WWBoomerangEntity))
		{
			entityIn.setSecondsOnFire(15);
		}
		if(entityIn instanceof IceArrowEntity || entityIn instanceof MagicIceArrowEntity || entityIn instanceof IceballEntity)
		{
			worldIn.setBlock(pos , BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState(), 1);
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}*/
    //TODO add some particles like a camp fire
    public InteractionResult use(BlockState blockstate, Level worldIn, BlockPos pos, Player playerentity, InteractionHand hand, BlockHitResult blocktrace) {
        ItemStack itemstack = playerentity.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (item != Items.WATER_BUCKET)
        {
            return super.use(blockstate, worldIn, pos, playerentity, hand, blocktrace);
        }
        else
        {
            worldIn.setBlock(pos, BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState(), 1);
            worldIn.playSound((Player) null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);

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

    @Override
    public void onPlace(BlockState blockState, Level world, BlockPos blockPos, BlockState oldBlockState, boolean b) {
        world.setBlockAndUpdate(blockPos.below(), BlockInit.TORCH_TOWER.get().defaultBlockState().setValue(OUTPUT_POWER, 15));
        world.setBlockAndUpdate(blockPos, BlockInit.TORCH_TOWER_TOP_LIT.get().defaultBlockState());
    }
}
