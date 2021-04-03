package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;

@OnlyIn(Dist.CLIENT)
public class EntityArrowAncientRender extends ArrowRenderer<EntityArrowAncient> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/entity/projectiles/arrows/ancient_arrow.png");

    public EntityArrowAncientRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityArrowAncient entity) {
        return TEXTURE;
    }


}