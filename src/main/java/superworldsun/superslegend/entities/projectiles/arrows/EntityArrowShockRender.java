package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;

@OnlyIn(Dist.CLIENT)
public class EntityArrowShockRender extends ArrowRenderer<EntityArrowShock> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/entity/projectiles/arrows/shock_arrow.png");

    public EntityArrowShockRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityArrowShock entity) {
        return TEXTURE;
    }


}