package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class IronBoots extends NonEnchantArmor {


	public IronBoots(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "Sink or Sink"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Allows underwater ground movement"));
	}
    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
            boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.IRON_BOOTS);
            if(isBootsOn)
            	{
            	if(player.isInWater() && !player.isOnGround())
            	{
            		//player.addPotionEffect(new EffectInstance(Effect.get(2), 10, 0, false, false));
            		//player.addPotionEffect(new EffectInstance(Effect.get(30), 3, 0, false, false));
            		
            		player.fallDistance = 0.0F;
    				player.hasImpulse = true;
    				player.setJumping(true);
    				
    				player.push(0, -0.07f, 0);
            		
            		//Vec3d v = player.getMotion();
					//player.setMotion(v.x, v.y * 2.0D, v.z);
            		
            	}
            	else if (player.isInWater() && player.isOnGround() && !player.isSprinting())
            	{
            		//Vec3d v = player.getMotion();
					//player.setMotion(v.x, v.y * 2.0D, v.z);
					
					player.fallDistance = 0.0F;
    				player.hasImpulse = true;
    				player.setJumping(true);
    				
            		//player.addPotionEffect(new EffectInstance(Effect.get(30), 10, 0, false, false));

					//Todo add potion back in
            		//player.addEffect(new EffectInstance(PotionList.iron_boots_effect, 8, 0, false, false));

            	}
            	else if (!player.isInWater())
            	{
            		
            		//Vec3d v = player.getMotion();
					//player.setMotion(v.x, v.y * -1.0D, v.z);
            		
            		player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10, 2, false, false));

            		//todo undo comment
            		//player.removeEffect(PotionList.iron_boots_effect);
            		//player.removePotionEffect(Effect.get(30));
            	}
                
            	}
    }
}