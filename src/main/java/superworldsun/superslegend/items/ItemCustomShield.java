package superworldsun.superslegend.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
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
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getHeldItemMainhand();
			if(!world.isRemote)
			{
				if(stack == equipped)
		        {
					if(player.isBurning())
					{
						stack.setDamage(337);
						
						player.sendStatusMessage(new TranslationTextComponent(TextFormatting.RED + "Your Shield has been Burnt"), true);
					}
		        }
			}	

		}
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getHeldItemOffhand();
			if(!world.isRemote)
			{
				if(stack == equipped)
		        {
					if(player.isBurning())
					{
						stack.setDamage(337);
						
						player.sendStatusMessage(new TranslationTextComponent(TextFormatting.RED + "Your Shield has been Burnt"), true);
					}
		        }
			}
		}
	}
	
	
	      @Override
	      public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
	          // TODO Auto-generated method stub
	    	  return false;	
	
	}
	      
	      
}
