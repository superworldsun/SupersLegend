package superworldsun.superslegend.entities.mobs.poe;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.entities.mobs.fairy.FairyEntity;

import javax.annotation.Nullable;

public class PoeEntityRenderer extends MobRenderer<PoeEntity, PoeEntityModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/entity/mobs/poeentity/poeentity_body.png");

    public PoeEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PoeEntityModel(), 0.5F);
    }

    @Override
    @Nullable
    public ResourceLocation getEntityTexture(PoeEntity entity) {
        return TEXTURE;
    }



}
