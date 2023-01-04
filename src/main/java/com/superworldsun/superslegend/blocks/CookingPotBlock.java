package com.superworldsun.superslegend.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.superworldsun.superslegend.blocks.entity.CookingPotTileEntity;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public abstract class CookingPotBlock extends Block {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    private long lastSysTime;
    private final Set<Integer> toPick = new HashSet<>();

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public CookingPotBlock() {
        //Cooking pots have their hardness set halfway between stone and iron
        //Their blast resistance matches iron/stone and they have the same light-level as furnaces/smokers/etc
        super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.25F, 6.0F).lightLevel((state) -> 13).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CookingPotTileEntity();
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof CookingPotTileEntity && state.getBlock() != newState.getBlock()) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .ifPresent(itemHandler -> {
                        for (int i = 0; i < itemHandler.getSlots(); i++) {
                            ItemStack stack = itemHandler.getStackInSlot(i);
                            if (!stack.isEmpty()) {
                                popResource(worldIn, pos, stack);
                            }
                        }
                    });
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide && handIn == Hand.MAIN_HAND) {
            CookingPotTileEntity tileEntity = (CookingPotTileEntity) worldIn.getBlockEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) player, tileEntity, (packetBuffer -> {
                assert tileEntity != null;
                packetBuffer.writeBlockPos(tileEntity.getBlockPos());
            }));
        }
        return ActionResultType.sidedSuccess(worldIn.isClientSide);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(LIT) ? super.getLightValue(state, world, pos) : 0;
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(LIT)) {
            double xPos = (double) pos.getX() + 0.5;
            double yPos = (double) pos.getY() + 0.2;
            double zPos = (double) pos.getZ() + 0.5;
            if (rand.nextInt(10) == 0) {
                worldIn.playLocalSound(xPos, yPos, zPos, SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, rand.nextFloat() + 0.5F, MathHelper.nextFloat(rand, 0.6F, 1.3F), false);
            }
                double xOffset = MathHelper.nextDouble(rand, -0.15, 0.15);
                double zOffset = MathHelper.nextDouble(rand, -0.15, 0.15);
                worldIn.addParticle(ParticleTypes.SMOKE, xPos + xOffset, yPos, zPos + zOffset, 0.0, 0.0, 0.0);
                worldIn.addParticle(ParticleTypes.FLAME, xPos + xOffset, yPos, zPos + zOffset, 0.0, 0.0, 0.0);
            }
    }

    @Override
    public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.8F;
    }

    @Override
    public IFormattableTextComponent getName() {
            long sysTime = System.currentTimeMillis();
            if (this.lastSysTime + 5000 < sysTime) {
                this.lastSysTime = sysTime;
                this.toPick.clear();
            return new TranslationTextComponent(this.getDescriptionId());
        } else {
            return super.getName();
        }
    }

    //To simulate BOTW's temp-gauge overing overload if the player jumps on a crockpot, the player will take minor fire damage
    @Override
    public void stepOn(World p_176199_1_, BlockPos p_176199_2_, Entity p_176199_3_) {
        p_176199_3_.hurt(DamageSource.ON_FIRE, 1F);
        super.stepOn(p_176199_1_, p_176199_2_, p_176199_3_);
    }
}
