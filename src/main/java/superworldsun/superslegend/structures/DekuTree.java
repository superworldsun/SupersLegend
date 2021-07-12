package superworldsun.superslegend.structures;

import net.minecraft.block.trees.Tree;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import superworldsun.superslegend.init.BlockInit;
import superworldsun.superslegend.lists.BlockList;

import javax.annotation.Nonnull;
import java.util.Random;

//public class DekuTree extends Tree {
/*

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DEKUTREEFEATURE =
            Feature.TREE.withConfiguration(
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(BlockList.deku_wood.getDefaultState()),
                            new SimpleBlockStateProvider(BlockList.deku_leaves.getDefaultState()),
                            new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
                            new StraightTrunkPlacer(4, 2, 0),
                            new TwoLayerFeature(1, 0, 1)
                    ).setIgnoreVines().build());



    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(@Nonnull Random randomIn, boolean largeHive) {
        return DEKUTREEFEATURE;
    }

}*/
