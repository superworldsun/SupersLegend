package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.SpinnerEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.BombArrowEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.MinecartModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.MinecartItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class SpinnerRenderer<T extends AbstractMinecartEntity> extends MinecartRenderer
{
    private static final ResourceLocation MINECART_LOCATION = new ResourceLocation(SupersLegendMain.MOD_ID,
            "textures/entity/spinner.png");
    protected final EntityModel<T> model = new MinecartModel<>();

    public SpinnerRenderer(EntityRendererManager p_i46155_1_) {
        super(p_i46155_1_);
        this.shadowRadius = 0.7F;
    }

    @Override
    protected void renderMinecartContents(AbstractMinecartEntity entityIn, float partialTicks, BlockState stateIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0, -0.15, 0);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(stateIn, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStackIn.popPose();
    }
}