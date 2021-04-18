package superworldsun.superslegend.items.masks;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
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


public class MaskStonemaskEffects extends NonEnchantArmor {
    public MaskStonemaskEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.stonemask, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "You wont be noticed, more than usual"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "Grants invisibility"));
	}

    @Override
    public boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity) {
        return true;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_stonemask);
                if(isHelmeton) player.addPotionEffect(new EffectInstance(Effect.get(14), 10, 0, false, false));
                }
            }
        }