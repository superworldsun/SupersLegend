package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class HoverBoots extends ArmorItem {
    public HoverBoots(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.hoverboots, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.YELLOW + "No road needed"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Sneak, To Hover over Gaps"));
		list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "Cannot use SlowFall when equiped"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "[WIP]"));
		
		
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.hover_boots);

                if(isBootsOn&&player.isSneaking() && player.getFoodStats().getFoodLevel()!= 0)
                {
                	player.removePotionEffect(Effect.get(28));
                	player.addExhaustion(0.1f);
                	player.setNoGravity(true);
                }
    			else
    			{
    				player.removePotionEffect(Effect.get(28));
    				player.setNoGravity(false);
    			}
                }
            }
        }