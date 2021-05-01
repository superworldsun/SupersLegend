package superworldsun.superslegend.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.util.ResourceLocation;

public class DekuLog extends WoodType {
    public DekuLog(AbstractBlock.Properties nameIn) {
        super(String.valueOf(nameIn));
    }

    public Block setRegistryName(ResourceLocation deku_log) {
        return null;
    }
}
