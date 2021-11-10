package com.superworldsun.superslegend.blocks;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.DimensionInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkWorldPortalBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public DarkWorldPortalBlock(AbstractBlock.Properties p_i48406_1_) {
        super(Block.Properties.of(Material.PORTAL).strength(100).noCollission());
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity)
    {
        if(!(entity instanceof ServerPlayerEntity)) return;

        RegistryKey<World> darkworldspawn = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(SupersLegendMain.MOD_ID,
                "dark_world"));
        ServerWorld darkworld = entity.getCommandSenderWorld().getServer().getLevel(darkworldspawn);

        ((ServerPlayerEntity) entity).teleportTo(darkworld, 0, 250, 0, 0, 0);

        /*if (!(entity instanceof ServerPlayerEntity) && ServerPlayerEntity) return;

        RegistryKey<World> overworldworldspawn = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(SupersLegendMain.MOD_ID,
                "overworld"));
        ServerWorld overworld = entity.getCommandSenderWorld().getServer().getLevel(overworldworldspawn);

        ((ServerPlayerEntity) entity).teleportTo(overworld, 0, 250, 0, 0, 0);*/
    }

    /*public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new EndPortalTileEntity();
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if (entity.getVehicle() != null || entity.isVehicle()) return;

        if(world instanceof ServerWorld && world.getServer() != null){
            ServerWorld world2 = world.getServer().getLevel(DimensionInit.DARK_WORLD_WORLD);
            if(world2 == null) return;

            entity.changeDimension(world2);
        }
    }

    public void stepOn(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
        if (p_196262_2_ instanceof ServerWorld && !p_196262_4_.isPassenger() && !p_196262_4_.isVehicle() && p_196262_4_.canChangeDimensions() && VoxelShapes.joinIsNotEmpty(VoxelShapes.create(p_196262_4_.getBoundingBox().move((double)(-p_196262_3_.getX()), (double)(-p_196262_3_.getY()), (double)(-p_196262_3_.getZ()))), p_196262_1_.getShape(p_196262_2_, p_196262_3_), IBooleanFunction.AND)) {
            RegistryKey<World> registrykey = p_196262_2_.dimension() == DimensionInit.DARK_WORLD_WORLD ? World.OVERWORLD : DimensionInit.DARK_WORLD_WORLD;
            ServerWorld serverworld = ((ServerWorld)p_196262_2_).getServer().getLevel(registrykey);
            if (serverworld == null) {
                return;
            }

            p_196262_4_.changeDimension(serverworld);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        double d0 = (double)p_180655_3_.getX() + p_180655_4_.nextDouble();
        double d1 = (double)p_180655_3_.getY() + 0.8D;
        double d2 = (double)p_180655_3_.getZ() + p_180655_4_.nextDouble();
        p_180655_2_.addParticle(ParticleTypes.BUBBLE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    public ItemStack getCloneItemStack(IBlockReader p_185473_1_, BlockPos p_185473_2_, BlockState p_185473_3_) {
        return ItemStack.EMPTY;
    }

    public boolean canBeReplaced(BlockState p_225541_1_, Fluid p_225541_2_) {
        return false;
    }*/
}