package superworldsun.superslegend.items.armors;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;
import superworldsun.superslegend.lists.PotionList;


public class IronBoots extends NonEnchantArmor {
    public IronBoots(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.ironboots, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "Sink or Sink"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Allows underwater ground movement"));
	}
    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
            boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.iron_boots);
            if(isBootsOn)
            	{
            	if(player.isInWater() && !player.isOnGround())
            	{
            		//player.addPotionEffect(new EffectInstance(Effect.get(2), 10, 0, false, false));
            		//player.addPotionEffect(new EffectInstance(Effect.get(30), 3, 0, false, false));
            		
            		player.fallDistance = 0.0F;
    				player.isAirBorne = true;
    				player.setJumping(true);
    				
    				player.addVelocity(0, -0.07f, 0);
            		
            		//Vec3d v = player.getMotion();
					//player.setMotion(v.x, v.y * 2.0D, v.z);
            		
            	}
            	else if (player.isInWater() && player.isOnGround() && !player.isSprinting())
            	{
            		//Vec3d v = player.getMotion();
					//player.setMotion(v.x, v.y * 2.0D, v.z);
					
					player.fallDistance = 0.0F;
    				player.isAirBorne = true;
    				player.setJumping(true);
    				
            		//player.addPotionEffect(new EffectInstance(Effect.get(30), 10, 0, false, false));
            		
            		player.addPotionEffect(new EffectInstance(PotionList.iron_boots_effect, 8, 0, false, false));

            	}
            	else if (!player.isInWater())
            	{
            		
            		//Vec3d v = player.getMotion();
					//player.setMotion(v.x, v.y * -1.0D, v.z);
            		
            		player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 2, false, false));
            		
            		player.removePotionEffect(PotionList.iron_boots_effect);
            		//player.removePotionEffect(Effect.get(30));
            	}
                
            	}
    }
}