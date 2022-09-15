package com.superworldsun.superslegend.client.render;



import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.HookshotModel;
import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
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
public class HookshotRender extends EntityRenderer<HookshotEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/hookshot.png");
    private static final ResourceLocation CHAIN_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/chain.png");
    private static final RenderType CHAIN_LAYER = RenderType.entitySmoothCutout(CHAIN_TEXTURE);
    //Get the model.
    private final HookshotModel<HookshotEntity> model = new HookshotModel<>();

    public HookshotRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    // Function that allows the rendering of the chain and the hook.
    @Override
    public void render(HookshotEntity hookshotEntity, float yaw, float tick, MatrixStack matrixStack, IRenderTypeBuffer provider, int light) {

        PlayerEntity player = (PlayerEntity) hookshotEntity.getOwner();

            if(player != null && !HookModel.get(player).getHasHook()) {

                HandSide mainArm = Minecraft.getInstance().options.mainHand;
                Hand activeHand = player.getUsedItemHand();

                matrixStack.pushPose();
                boolean rightHandIsActive = (mainArm == HandSide.RIGHT && activeHand == Hand.MAIN_HAND) || (mainArm == HandSide.LEFT && activeHand == Hand.OFF_HAND);
                double bodyYawToRads = Math.toRadians(player.yBodyRot);
                double radius = rightHandIsActive ? -0.4D : 0.4D;
                double startX = player.getX() + radius * Math.cos(bodyYawToRads);
                double startY = player.getY() + (player.getBbHeight() / 3D);
                double startZ = player.getZ() + radius * Math.sin(bodyYawToRads);
                float distanceX = (float) (startX - hookshotEntity.getX());
                float distanceY = (float) (startY - hookshotEntity.getY());
                float distanceZ = (float) (startZ - hookshotEntity.getZ());

                float message1 = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
                boolean message = message1 < 2;
                if (!(message)) {
                    renderChain(distanceX, distanceY, distanceZ, tick, hookshotEntity.tickCount, matrixStack, provider, light);
                    renderSecondChain(distanceX, distanceY, distanceZ, tick, hookshotEntity.tickCount, matrixStack, provider, light);
                }

                renderHook(distanceX, distanceY, distanceZ, tick, hookshotEntity.tickCount, matrixStack, provider, light);

                matrixStack.popPose();
            }


        super.render(hookshotEntity, yaw, tick, matrixStack, provider, light);
    }

    public void renderHook(float x, float y, float z, float tickDelta, int age, MatrixStack stack, IRenderTypeBuffer provider, int light) {
        float lengthXY = MathHelper.sqrt(x * x + z * z);

        stack.pushPose();

        stack.mulPose(Vector3f.YP.rotation((float) (-Math.atan2(z, x)) - 1.5707964F));
        stack.mulPose(Vector3f.XP.rotation((float) (-Math.atan2(lengthXY, y)) - 1.5707964F));
        // You can change the size of the hook here.
        stack.scale(1.0F,1.0F,1.0F);
        IVertexBuilder ivertexbuilder = provider.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(stack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        stack.popPose();

    }

    public void renderChain(float x, float y, float z, float tickDelta, int age, MatrixStack stack, IRenderTypeBuffer provider, int light) {

        float lengthXY = MathHelper.sqrt(x * x + z * z);
        float squaredLength = x * x + y * y + z * z;
        float length = MathHelper.sqrt(squaredLength);

        stack.pushPose();
        stack.mulPose(Vector3f.YP.rotation((float) (-Math.atan2(z, x)) - 1.5707964F));
        stack.mulPose(Vector3f.XP.rotation((float) (-Math.atan2(lengthXY, y)) - 1.5707964F));

        IVertexBuilder iVertexBuilder = provider.getBuffer(CHAIN_LAYER);
        float h = 0.0F - ((float) age + tickDelta) * 0.01F;
        float i = MathHelper.sqrt(squaredLength) / 32.0F - ((float) age + tickDelta) * 0.01F;
        float k = 0.0F;
        float l = 0.75F;
        float m = 0.0F;
        MatrixStack.Entry entry = stack.last();
        Matrix4f matrix4f = entry.pose();
        Matrix3f matrix3f = entry.normal();

        for(int n = 1; n <= 8; ++n) {

            float o = MathHelper.sin((float) n * 6.2831855F / 8.0F) * 0.125F;
            float p = MathHelper.cos((float) n * 6.2831855F / 8.0F) * 0.125F;
            float q = (float) n / 8.0F;

            iVertexBuilder.vertex(matrix4f, k * 0.2F, l * 0.2F, 0.0F).color(0, 0, 0, 255).uv(m, h).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            iVertexBuilder.vertex(matrix4f, k, l, length).color(255, 255, 255, 255).uv(m, i).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            iVertexBuilder.vertex(matrix4f, o, p, length).color(255, 255, 255, 255).uv(q, i).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            iVertexBuilder.vertex(matrix4f, o * 0.2F, p * 0.2F, 0.0F).color(0, 0, 0, 255).uv(q, h).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();

            k = o;
            l = p;
            m = q;
        }

        stack.popPose();
    }

    public void renderSecondChain(float x, float y, float z, float tickDelta, int age, MatrixStack stack, IRenderTypeBuffer provider, int light) {

        float lengthXY = MathHelper.sqrt(x * x + z * z);
        float squaredLength = x * x + y * y + z * z;
        float length = MathHelper.sqrt(squaredLength);

        stack.pushPose();
        stack.mulPose(Vector3f.YP.rotation((float) (-Math.atan2(z, x)) - 1.5707964F));
        stack.mulPose(Vector3f.XP.rotation((float) (-Math.atan2(lengthXY, y)) - 1.5707964F));

        IVertexBuilder iVertexBuilder = provider.getBuffer(CHAIN_LAYER);
        float h = 0.0F - ((float) age + tickDelta) * 0.01F;
        float i = MathHelper.sqrt(squaredLength) / 32.0F - ((float) age + tickDelta) * 0.01F;
        float k = 0.0F;
        float l = -0.10F;
        float m = 0.525F;
        MatrixStack.Entry entry = stack.last();
        Matrix4f matrix4f = entry.pose();
        Matrix3f matrix3f = entry.normal();

        for(int n = 1; n <= 8; ++n) {

            float o = MathHelper.sin((float) n * 6.2831855F / 8.0F) * 0.125F;
            float p = MathHelper.cos((float) n * 6.2831855F / 8.0F) * 0.125F;
            float q = (float) n / 8.0F;

            iVertexBuilder.vertex(matrix4f, k * 0.2F, l * 0.2F, 0.0F).color(0, 0, 0, 255).uv(m, h).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            iVertexBuilder.vertex(matrix4f, k, l, length).color(255, 255, 255, 255).uv(m, i).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            iVertexBuilder.vertex(matrix4f, o, p, length).color(255, 255, 255, 255).uv(q, i).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            iVertexBuilder.vertex(matrix4f, o * 0.2F, p * 0.2F, 0.0F).color(0, 0, 0, 255).uv(q, h).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();

            k = o;
            l = p;
            m = q;
        }

        stack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(HookshotEntity entity) {
        return TEXTURE;
    }

}

