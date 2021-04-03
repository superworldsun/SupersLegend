package superworldsun.superslegend.items.armors;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class ArmorClimbingGearEffects extends NonEnchantArmor {
    public ArmorClimbingGearEffects(String name, EquipmentSlotType slot)
    
    {
        super(ArmourMaterialList.climbing, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "Going up"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Wearing the set grants the ability to climb any surface"));
        list.add(new StringTextComponent(TextFormatting.RED + "Dosent work well in rain"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        boolean isHelmetOn = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.climbers_bandanna);
        boolean isChestplateOn = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.climbing_gear);
        boolean isLeggingsOn = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem().equals(ItemList.climbing_pants);
        boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.climbing_boots);
            if(isHelmetOn&isChestplateOn || isHelmetOn&isLeggingsOn || isHelmetOn&isBootsOn ||
                    isChestplateOn&isLeggingsOn || isChestplateOn&isBootsOn || isLeggingsOn&isBootsOn) {
                if (!player.isSpectator() && player.collidedHorizontally && player.moveForward > 0 && !player.isWet()) {
                    player.setMotion(player.getMotion().getX(), 0.05, player.getMotion().getZ());
                    player.fallDistance = 0F;
                }
            }
            if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
            {
                if (!player.isSpectator() && player.collidedHorizontally && player.moveForward > 0 && !player.isWet()) {
                    player.setMotion(player.getMotion().getX(), 0.1, player.getMotion().getZ());
                    player.fallDistance = 0F;
                }
            }
    }
}