package superworldsun.superslegend.items.masks;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class MaskAllnightmaskEffects extends NonEnchantArmor {
    public MaskAllnightmaskEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.allnightmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_allnightmask);
                if(isHelmeton) player.addPotionEffect(new EffectInstance(Effect.get(16), 230, 0, false, false));
                }
        if(player.isSleeping())
        {
        	player.wakeUpPlayer(true, false, false);
        	player.sendStatusMessage(new TranslationTextComponent(TextFormatting.GRAY + "You feel restless"), true);
        }
            }
    
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.WHITE + "Cant sleep huh?"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants nightvision"));
	}

    
        }



