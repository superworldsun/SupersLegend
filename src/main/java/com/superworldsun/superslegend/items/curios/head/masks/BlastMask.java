package com.superworldsun.superslegend.items.curios.head.masks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.AllNightMaskModel;
import com.superworldsun.superslegend.client.model.armor.BlastMaskModel;
import com.superworldsun.superslegend.cooldowns.CooldownsProvider;
import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class BlastMask extends Item implements IMaskAbility, ICurioItem {
    @OnlyIn(Dist.CLIENT)
    private Object model;
    // put your texture here
    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/armor/blast_mask.png");


    public BlastMask(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.GRAY + "Bomb Blastic"));
    }

    @Override
    public void startUsingAbility(PlayerEntity player) {
        // Do nothing if on cooldown
        if (CooldownsProvider.get(player).hasCooldown(ItemInit.MASK_BLASTMASK.get())) {
            return;
        }

        Vector3d explosionPos = player.getEyePosition(1.0F).add(player.getLookAngle().multiply(0.5D, 0.5D, 0.5D));
        player.level.explode(player, explosionPos.x, explosionPos.y, explosionPos.z, 2.0F, Mode.BREAK);
        player.hurt(DamageSource.explosion(player), 2.0F);
        // 200 ticks are 10 seconds
        CooldownsProvider.get(player).setCooldown(ItemInit.MASK_BLASTMASK.get(), 200);
        IMaskAbility.super.startUsingAbility(player);
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
    {
        // put your model here
        if (!(this.model instanceof AllNightMaskModel))
        {
            model = new BlastMaskModel<>();
        }

        BlastMaskModel<?> maskModel = (BlastMaskModel<?>) this.model;
        ICurio.RenderHelper.followHeadRotations(livingEntity, maskModel.base);
        IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, maskModel.renderType(TEXTURE), false, stack.hasFoil());
        maskModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}