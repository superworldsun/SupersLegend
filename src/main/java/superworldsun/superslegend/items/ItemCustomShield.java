package superworldsun.superslegend.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemCustomShield extends ShieldItem
{
	public ItemCustomShield(Item.Properties builder) {
	      super(builder);
	}
	      
	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return true;
	}
	
	
	
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getHeldItemMainhand();
			{
				if(stack == equipped)
		        {
					if(player.isBurning())
					{
						stack.shrink(1);
						
						BlockPos currentPos = player.getPosition();
						 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1f, 1f);
						 
						player.sendStatusMessage(new TranslationTextComponent(TextFormatting.RED + "Your shield is gone!"), true);
					}
		        }
			}	

		}
		if(entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getHeldItemOffhand();
			{
				if(stack == equipped)
		        {
					if(player.isBurning())
					{
						stack.shrink(1);
						
						BlockPos currentPos = player.getPosition();
						 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1f, 1f);
						 
						player.sendStatusMessage(new TranslationTextComponent(TextFormatting.RED + "Your shield is gone!"), true);
					}
		        }
			}
		}
	}
	
	
	      @Override
	      public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
	    	  return false;	
	
	}
	      
	      
}
