package superworldsun.superslegend.entities.mobs.fairy;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
/*public class FairyEntityRenderer extends MobRenderer<FairyEntity, FairyEntityModel>{

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



    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(FairyEntity entity) {
        return TEXTURE;
    }

}*/


public class FairyEntityRenderer extends MobRenderer<FairyEntity, FairyEntityModel>{

public static final ResourceLocation TEXTURE = new ModelResourceLocation(new ResourceLocation(SupersLegend.modid, "textures/entity/fairyentity.png"), "models/fairyentity.json");

    public FairyEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FairyEntityModel(), 0.0F);
    }

    @Override
    protected void preRenderCallback(@Nonnull FairyEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float scaleFactor = 1.00F;
        matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }



    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(FairyEntity entity) {
        return TEXTURE;
    }

}
