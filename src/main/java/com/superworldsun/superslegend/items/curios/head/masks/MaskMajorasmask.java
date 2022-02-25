package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MaskMajorasmask extends Item implements ICurioItem {

    public MaskMajorasmask(Properties properties) {
        super(properties);
    }
    
    /*@SuppressWarnings("unchecked")
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
    	ModelMajorasMask model = new ModelMajorasMask();

        model.isChild = _default.isChild;
        model.isSneak = _default.isSneak;
        model.isSitting = _default.isSitting;

        return (A) model;
    }*/

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.RED + "This Mask gives off a strong evil aura"));
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        World world = livingEntity.level;
        PlayerEntity player = (PlayerEntity) livingEntity;


        if (!world.isClientSide) {
            ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_MAJORASMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            if (!stack0.isEmpty()) {
                player.addEffect(new EffectInstance(Effect.byId(20), 120, 0, false, true));
                player.addEffect(new EffectInstance(Effect.byId(5), 10, 1, false, false));
                player.addEffect(new EffectInstance(Effect.byId(11), 10, 1, false, false));
                player.addEffect(new EffectInstance(Effect.byId(1), 10, 1, false, false));
                player.addEffect(new EffectInstance(Effect.byId(31), 10, 0, false, false));
            } else {
                player.removeEffect(Effect.byId(20));

            }
        }
    }
}
