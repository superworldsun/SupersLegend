package superworldsun.superslegend.items.masks;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;



public class MaskGiantsmask extends NonEnchantArmor {
    public MaskGiantsmask(String name, EquipmentSlotType slot) 

    {
        super(ArmourMaterialList.giantsmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

    // TODO When wearing mask, Step height is doubled.
    /*@Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        {
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_giantsmask);
                if(isHelmeton)
                {
                    player.stepHeight = 1.0f;
                }
                else {
                	player.stepHeight = 0.5f;
                }
        }
    }
    
    
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity livingEntity = (PlayerEntity) entity;
            if (!(livingEntity.inventory.armorInventory.get(0).getItem().equals(ItemList.mask_giantsmask))) {
            	{
                    livingEntity.stepHeight = 0.5f;
                }
            }
            	else{
            	{
            		livingEntity.stepHeight = 0.5f;
            	}
            }
        }
    }
    
    public void onLivingUpdateEvent(LivingUpdateEvent event)
    {
    	// This event has an Entity variable, access it like this:
    	event.getEntity();

    	// do something to player every update tick:
    	if (event.getEntity() instanceof PlayerEntity)
    	{
    		PlayerEntity player = (PlayerEntity) event.getEntity();
    		@SuppressWarnings("unused")
			ItemStack heldItem = player.getHeldItem(null);
    		if (player.isAlive())
    		{ 
    			player.stepHeight = 0.5f;
    		}
    		else
    		{
    			player.stepHeight = 0.5f;
    		}
    	}
    }*/
        
    
    
    /*public void applyCustomModifiers(ItemStack stack, PlayerEntity player) {
		
    	

    	PlayerEntity.setSize(player, player.getWidth() * 3.0F, player.getHeight() * 3.0F);
		if (player.world.isRemote) {
			player.stepHeight += 1.0F;
		}
		 
	}
	public void removeModifiers(ItemStack stack, PlayerEntity player) {

			if (player.world.isRemote) {
				player.stepHeight -= 1.0F;
			}
		}*/
	 
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "Takes a big spirit to wear this mask"));
	}
}
