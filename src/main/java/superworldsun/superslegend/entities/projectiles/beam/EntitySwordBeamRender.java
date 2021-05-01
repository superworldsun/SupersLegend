package superworldsun.superslegend.entities.projectiles.beam;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;

@OnlyIn(Dist.CLIENT)
public class EntitySwordBeamRender extends ArrowRenderer<EntitySwordBeam> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/entity/projectiles/arrows/sword_beam.png");

    public EntitySwordBeamRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntitySwordBeam entity) {
        return TEXTURE;
    }


}