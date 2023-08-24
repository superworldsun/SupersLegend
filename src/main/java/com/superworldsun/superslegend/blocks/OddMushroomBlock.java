package com.superworldsun.superslegend.blocks;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;


public class OddMushroomBlock extends MushroomBlock implements BonemealableBlock
{
    public OddMushroomBlock(Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> pFeature) {
        super(pProperties, pFeature);
    }
}
