package superworldsun.superslegend.items.masks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;

import java.util.List;


public class MaskHawkeyemask extends NonEnchantArmor {
    public MaskHawkeyemask(String name, EquipmentSlotType slot)
    
    {
        super(ArmourMaterialList.hawkeye, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
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