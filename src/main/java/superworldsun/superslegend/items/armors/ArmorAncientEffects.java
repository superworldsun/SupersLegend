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

public class ArmorAncientEffects extends NonEnchantArmor
{
    public ArmorAncientEffects(String name, EquipmentSlotType slot)
    
    {
        super(ArmourMaterialList.ancient, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.YELLOW + "Armor made from Ancient technology"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Wearing the set grants defense"));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
    {
        boolean isHelmetOn = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.ancient_helmet);
        boolean isChestplateOn = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.ancient_cuirass);
        boolean isLeggingsOn = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem().equals(ItemList.ancient_greaves);
        boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.ancient_boots);
        if (!world.isRemote)
        {
            if(isHelmetOn&isChestplateOn || isHelmetOn&isLeggingsOn || isHelmetOn&isBootsOn ||
                    isChestplateOn&isLeggingsOn || isChestplateOn&isBootsOn || isLeggingsOn&isBootsOn)
            {
                player.addPotionEffect(new EffectInstance(Effect.get(11), 3, 0, false, false, false));
            }
        }

        if (!world.isRemote)
        {
            if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
            {
                player.addPotionEffect(new EffectInstance(Effect.get(11), 3, 1, false, false, false));
            }
        }
    }
}