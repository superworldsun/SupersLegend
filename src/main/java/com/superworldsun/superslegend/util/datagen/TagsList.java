package com.superworldsun.superslegend.util.datagen;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TagsList {
    public static class ItemTagsDataGen extends ItemTagsProvider {

        public ItemTagsDataGen(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider,
                               ExistingFileHelper existingFileHelper) {
            super(generatorIn, blockTagsProvider, SupersLegendMain.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            // To refresh tags after edits below, run preparerundata then run rundata
            //copy the tags folder to your mod's data folder, ie resources/data/modid/tags

            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.CURSED_RING.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.POWER_RING_L1.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.ARMOR_RING_L1.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.HEART_RING_L1.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.GREEN_HOLY_RING.get());
            //tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.BLUE_HOLY_RING.get());
            //tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.RED_HOLY_RING.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.GREEN_LUCK_RING.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.BLUE_LUCK_RING.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.GOLD_LUCK_RING.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.RED_LUCK_RING.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.SWIMMERS_RING.get());
            tag(Tags.Items.APPRAISAL_LIST).add(ItemInit.STEADFAST_RING.get());

        }
    }

    public static class BlockTagsDataGen extends BlockTagsProvider {

        public BlockTagsDataGen(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
            super(generatorIn, SupersLegendMain.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags() {
        }
    }
}
