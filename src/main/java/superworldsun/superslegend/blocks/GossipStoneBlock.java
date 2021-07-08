package superworldsun.superslegend.blocks;


import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class GossipStoneBlock extends AbstractGossipStoneBlock
{

    public GossipStoneBlock(Properties builder)
    {
        super(builder);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        
        //Eventually where you would reference your tile arrows class (unless you dont need one)
        return null;
        
    }

}
	