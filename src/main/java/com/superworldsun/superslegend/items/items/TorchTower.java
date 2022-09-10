package com.superworldsun.superslegend.items.items;

import java.util.List;

import javax.annotation.Nonnull;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TorchTower extends Item

{

	public TorchTower(Properties properties) {
		super(properties);
	}
	

	@Nonnull
    public ActionResultType useOn(ItemUseContext context) 
	{
        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        ItemStack item = context.getItemInHand();
        BlockPos pos1 = blockpos.above();
        BlockPos pos2 = blockpos.above(2);
        
		 world.playSound(null, pos1.getX(), pos1.getY(), pos1.getZ(), SoundEvents.WOOD_PLACE, SoundCategory.PLAYERS, 1f, 1f);
		
        if(world.getBlockState(pos1).isAir() && world.getBlockState(pos2).isAir())
        
        {
            //TODO FIX TORCH TOWER BLOCKS
            //world.setBlockAndUpdate(pos1, BlockInit.torch_tower_block_bottom.defaultBlockState());
            //world.setBlockAndUpdate(pos2, BlockInit.torch_tower_block_top.defaultBlockState());
        	
        item.shrink(1);
        }
        return ActionResultType.PASS;
        
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "[WIP]"));
	}  
}
