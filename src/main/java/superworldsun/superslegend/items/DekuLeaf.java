package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.lists.ItemList;

public class DekuLeaf extends Item
{
	public DekuLeaf(Properties properties)
	{
		super(properties);
	}
	
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getHeldItemMainhand();
			if(!world.isRemote)
			{
				if(player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.hover_boots) && player.getFoodStats().getFoodLevel()!= 0) 
            	{
            		player.removePotionEffect(Effect.get(28));
            	}
				else if(stack == equipped && !player.onGround && !player.isInWater() && player.getFoodStats().getFoodLevel()!= 0)
		        {
					player.addExhaustion(0.2f);
					player.addPotionEffect(new EffectInstance(Effect.get(28), 2, 10, false, false));
		        }
			}	

		}
	}
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GREEN + "Holding this will slow your decent"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina when airborne"));
	}   
}