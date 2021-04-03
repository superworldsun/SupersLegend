package superworldsun.superslegend.entities.mobs.fairy;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;
//import superworldsun.superslegend.particles.fairy.FairyParticleData;

import javax.annotation.ParametersAreNonnullByDefault;

/*@OnlyIn(Dist.CLIENT)
public class FairyEntityRenderer extends MobRenderer<FairyEntity, FairyEntityModel> {

    public static final ResourceLocation WINGS = new ResourceLocation(SupersLegend.modid, "textures/entity/mobs/fairyentity/fairyentity_wings.png");



    public FairyEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FairyEntityModel(), 0F);
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

    public void render(FairyEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        float r = 255;
        float g = 153;
        float b = 255;
        float scale = 0.5f;
        entityIn.world.addParticle(new FairyParticleData(r, g, b, scale),
                entityIn.getPosX(),
                entityIn.getPosY() + 0.2,
                entityIn.getPosZ(), 0, 0, 0);

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }


    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(FairyEntity entity) {
        return WINGS;
    }

}*/
