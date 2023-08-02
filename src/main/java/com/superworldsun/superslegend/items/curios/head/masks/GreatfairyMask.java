package com.superworldsun.superslegend.items.curios.head.masks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class GreatfairyMask extends Item implements ICurioItem {
    public GreatfairyMask(Properties pProperties) {
        super(pProperties);
    }


    /*@Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        if (livingEntity.getType() != EntityType.PLAYER)
            return;

        if (isPlayerUsingAbility((Player) livingEntity) && livingEntity.isSprinting())
        {
            livingEntity.setSprinting(false);
        }
    }

    @Override
    public void startUsingAbility(Player player)
    {
        if (player.level.isClientSide)
        {
            playMaskSound(player);
        }

        UUID slowId = UUID.fromString("7176f8ab-df6b-4065-9232-3c314fadb655");
        // -0.3 is 30% slower
        AttributeModifier modifier = new AttributeModifier(slowId, "Bremen Mask Slow", -0.3, AttributeModifier.Operation.MULTIPLY_BASE);
        AttributeInstance movespeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        movespeed.addTransientModifier(modifier);
        IMaskAbility.super.startUsingAbility(player);
    }

    @Override
    public void stopUsingAbility(PlayerEntity player)
    {
        UUID slowId = UUID.fromString("7176f8ab-df6b-4065-9232-3c314fadb655");
        AttributeModifier modifier = player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(slowId);
        AttributeInstance movespeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (modifier != null)
        {
            movespeed.removeModifier(modifier);
        }

        IMaskAbility.super.stopUsingAbility(player);
    }

    @OnlyIn(Dist.CLIENT)
    private void playMaskSound(Player player)
    {
        Minecraft client = Minecraft.getInstance();
        client.getSoundManager().play(new BremenMaskSound(player));
    }*/

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("You wont be noticed, more than usual").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Grants invisibility").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
