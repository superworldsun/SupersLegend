package superworldsun.superslegend.util.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import static superworldsun.superslegend.SupersLegend.modid;

public class CustomArrowTag
{

    public static class Items{
        public static final Tag<Item> CUSTOMARROW = tag("custom_arrow_tag");

        private static Tag<Item> tag(String name){return new ItemTags.Wrapper(new ResourceLocation(modid, name));}
    }

    public static class Blocks{
        public static final Tag<Block> CUSTOMARROWTAG = tag("custom_arrow_tag");

        private static Tag<Block> tag(String name){return new BlockTags.Wrapper(new ResourceLocation(modid, name));}
    }

}
