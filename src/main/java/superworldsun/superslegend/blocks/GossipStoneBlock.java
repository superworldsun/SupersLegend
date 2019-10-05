package superworldsun.superslegend.blocks;


import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class GossipStoneBlock extends AbstractGossipStoneBlock
{

    public GossipStoneBlock(Properties builder)
    {
        super(builder);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        
        //Eventually where you would reference your tile entity class (unless you dont need one)
        return null;
        
    }

}
	