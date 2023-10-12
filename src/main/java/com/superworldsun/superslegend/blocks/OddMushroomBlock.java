package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;


public class OddMushroomBlock extends MushroomBlock implements BonemealableBlock
{
    public OddMushroomBlock(Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> pFeature) {
        super(pProperties, pFeature);
    }

    private static final TagKey<Block> odd_mushroom_placement = TagKey.create(Registries.BLOCK, new ResourceLocation(SupersLegendMain.MOD_ID, "odd_mushroom_placement"));

    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.is(odd_mushroom_placement);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockpos = blockPos.below();
        return this.mayPlaceOn(levelReader.getBlockState(blockpos), levelReader, blockpos);
    }
}
