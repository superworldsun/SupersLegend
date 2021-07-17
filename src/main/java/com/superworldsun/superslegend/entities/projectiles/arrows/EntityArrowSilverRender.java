package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;

@OnlyIn(Dist.CLIENT)
public class EntityArrowSilverRender extends ArrowRenderer<EntityArrowSilver> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/entity/projectiles/arrows/silver_arrow.png");

    public EntityArrowSilverRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityArrowSilver entity) {
        return TEXTURE;
    }


}