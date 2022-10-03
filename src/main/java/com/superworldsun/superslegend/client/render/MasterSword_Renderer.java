package com.superworldsun.superslegend.client.render;



import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.HookshotModel;
import com.superworldsun.superslegend.client.model.MasterSword_Model;
import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import com.superworldsun.superslegend.entities.projectiles.mastersword.MasterSwordSwordEntity;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MasterSword_Renderer extends EntityRenderer<MasterSwordSwordEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/mastersword.png");

    private final MasterSword_Model model = new MasterSword_Model();

    public MasterSword_Renderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(MasterSwordSwordEntity mastersword, float yaw, float tick, MatrixStack matrixStack, IRenderTypeBuffer provider, int light) {
        matrixStack.pushPose();
        matrixStack.scale(3.0F, 0, 3.0F);
        matrixStack.mulPose(Vector3f.YN.rotationDegrees(mastersword.getBoomerangRotation()));
        rendersword(tick, mastersword.tickCount, matrixStack, provider, light);
        matrixStack.popPose();
        super.render(mastersword, yaw, tick, matrixStack, provider, light);

    }

    public void rendersword(float tickDelta, int age, MatrixStack stack, IRenderTypeBuffer provider, int light) {
        IVertexBuilder ivertexbuilder = provider.getBuffer(RenderType.entityTranslucent(TEXTURE));
        this.model.renderToBuffer(stack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

    }


    @Override
    public ResourceLocation getTextureLocation(MasterSwordSwordEntity entity) {
        return TEXTURE;
    }

}

