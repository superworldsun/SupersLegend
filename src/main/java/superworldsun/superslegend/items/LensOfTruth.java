package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class LensOfTruth extends Item
{

	public LensOfTruth(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);
		  
		 if(!world.isRemote)
	     {
				
	     }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }
	
	@Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        
        if(!player.world.isRemote)
            if(entity.isAlive())
                if(entity instanceof Entity)
                    if(entity.isInvisible())
                        entity.setGlowing(true);
        return false;
        
    }
	
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}  
	
}
