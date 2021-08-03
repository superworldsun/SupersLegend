package com.superworldsun.superslegend.items.masks;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;


import net.minecraft.item.Item.Properties;

public class MaskHawkeyemask extends NonEnchantArmor {

    public MaskHawkeyemask(Properties properties)
    {
        super(ArmourInit.hawkeye, EquipmentSlotType.HEAD, properties);
    }

    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.WHITE + "A Mask often used by snipers"));
	}

    /*@SubscribeEvent
    public void onFovEvent(FOVUpdateEvent event) {
        ItemStack stack = event.getEntity().getActiveItemStack();
        if(!stack.isEmpty()) {
            float zoom = 0f;

            if(stack.getItem() == ItemList.heros_bow) {
                zoom = 5.0f;
            }
            if(zoom > 0) {
            }
        }
    }*/


        /*public interface IZoomHelper {
            float getMagnificationFactor();
        }


        public float getMagnificationFactor () {
            return 3.0f;
        }*/

    /*@SubscribeEvent
    public static void FOVUpdateEvent(FOVUpdateEvent evt)
    {
        if (!evt.getEntity().getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() && evt.getEntity().getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == ItemList.mask_hawkeyemask)
        {
            evt.setNewfov(evt.getFov() - 3.0F);
        }
    }*/
}