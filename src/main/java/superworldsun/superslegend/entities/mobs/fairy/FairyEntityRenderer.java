package superworldsun.superslegend.entities.mobs.fairy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.ModelingAPI.helpers.RenderTypes;
import superworldsun.superslegend.SupersLegend;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class FairyEntityRenderer extends MobRenderer<FairyEntity, FairyEntityModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/entity/mobs/fairyentity/fairyentity_body.png");

    public FairyEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FairyEntityModel(), 0.0F);
        this.addLayer(new FairyWings(this));
    }

    protected int getBlockLight(FairyEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    protected void applyRotations(FairyEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if (this.func_230495_a_(entityLiving)) {
            rotationYaw += (float) (Math.cos((double) entityLiving.ticksExisted * 7F) * Math.PI * (double) 0.9F);
            float vibrate = 0.05F;
            matrixStackIn.translate((entityLiving.getRNG().nextFloat() - 0.5F) * vibrate, (entityLiving.getRNG().nextFloat() - 0.5F) * vibrate, (entityLiving.getRNG().nextFloat() - 0.5F) * vibrate);
        }
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(FairyEntity entity) {
        return TEXTURE;
    }

}

class FairyWings extends LayerRenderer<FairyEntity, FairyEntityModel> {

    public FairyWings(FairyEntityRenderer p_i50928_1_) {
        super(p_i50928_1_);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, FairyEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder lvt_11_1_ = bufferIn.getBuffer(this.getRenderType());
        this.getEntityModel().render(matrixStackIn, lvt_11_1_, 15728640, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0), 0.5F, 0.5F, 0.5F, getAlphaForRender(entitylivingbaseIn, partialTicks));

    }

    public float getAlphaForRender(FairyEntity entityIn, float partialTicks) {
        return ((float) Math.sin((entityIn.ticksExisted + partialTicks) * 0.1F) + 1.5F) * 0.1F + 0.5F;
    }

    public static final ResourceLocation WINGS = new ResourceLocation(SupersLegend.modid, "textures/entity/mobs/fairyentity/fairyentity_wings.png");

    public RenderType getRenderType() {
        return RenderTypes.getEyesFlickering(WINGS, 200.0F);
    }

}





    /*public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.isChild) {
            matrixStackIn.push();
            matrixStackIn.scale(0.65F, 0.65F, 0.65F);
            matrixStackIn.translate(0.0D, 0.95D, 0.125D);
            getParts().forEach((p_228292_8_) -> {
                p_228292_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.pop();
        } else {
            matrixStackIn.push();
            getParts().forEach((p_228290_8_) -> {
                p_228290_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.pop();
        }

    }*/
