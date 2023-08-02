package com.superworldsun.superslegend.items.curios.head.masks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class TroupeLeadersMask extends Item implements ICurioItem {
    public TroupeLeadersMask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        Player player = (Player) livingEntity;

        if(!player.isCrouching() && !player.isEyeInFluid(FluidTags.WATER))
        {
            double particleX = player.getX() + (player.getRandom().nextBoolean() ? -0.1D : 0);
            double particleY = player.getY() + player.getRandom().nextFloat() * 0 - -1.7D;
            double particleZ = player.getZ() + (player.getRandom().nextBoolean() ? -0.1D : 0);
            player.level().addParticle(ParticleTypes.RAIN, particleX, particleY, particleZ, 0, 0, 0);
            //player.level.addParticle(ParticleTypes.DRIPPING_WATER, particleX, particleY, particleZ, 0, 1.0D, 0);
        }
        if(player.isCrouching() && !player.isEyeInFluid(FluidTags.WATER))
        {
            double particleX = player.getX() + (player.getRandom().nextBoolean() ? -0.1D : 0);
            double particleY = player.getY() + player.getRandom().nextFloat() * 0 - -1.2D;
            double particleZ = player.getZ() + (player.getRandom().nextBoolean() ? -0.1D : 0);
            player.level().addParticle(ParticleTypes.RAIN, particleX, particleY, particleZ, 0, 0, 0);
            //player.level.addParticle(ParticleTypes.DRIPPING_WATER, particleX, particleY, particleZ, 0, 1.0D, 0);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("A very depressing expression").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
