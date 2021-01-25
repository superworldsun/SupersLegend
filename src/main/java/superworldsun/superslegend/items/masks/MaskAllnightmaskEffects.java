package superworldsun.superslegend.items.masks;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;
import superworldsun.superslegend.models.masks.ModelAllnightmask;

import javax.annotation.Nonnull;


public class MaskAllnightmaskEffects extends NonEnchantArmor {
    public MaskAllnightmaskEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.allnightmask, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {
        return (A) new ModelAllnightmask(0);
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
        	player.wakeUp();
        	player.sendStatusMessage(new TranslationTextComponent(TextFormatting.GRAY + "You feel restless"), true);
        }
    }

    @Override
    public @Nonnull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType equipmentSlot) {
        return HashMultimap.create();
    }

    
    @Override
    @OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.WHITE + "Cant sleep huh?"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants nightvision"));
	}
}



