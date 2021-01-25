package superworldsun.superslegend.entities.mobs.fairy;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class FairyEntityRenderer extends MobRenderer<FairyEntity, FairyEntityModel>{

    public static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/entity/fairyentity.png");

    public FairyEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FairyEntityModel(), 0.5f);
    }

    @Override
    protected void preRenderCallback(@Nonnull FairyEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float scaleFactor = 1.00F;
        matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }


    @Nullable
    @Override
    public ResourceLocation getEntityTexture(FairyEntity entity) {
        return TEXTURE;
    }

}
