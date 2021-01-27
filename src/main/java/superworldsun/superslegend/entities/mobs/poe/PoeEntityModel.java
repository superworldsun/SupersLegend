package superworldsun.superslegend.entities.mobs.poe;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import superworldsun.superslegend.ModelingAPI.helpers.AdvancedEntityModel;
import superworldsun.superslegend.ModelingAPI.helpers.AdvancedModelBox;

public class PoeEntityModel extends EntityModel<PoeEntity> {

    public ModelRenderer body;

    public PoeEntityModel() {
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-10, 22, -10, 20, 20, 20);
    }

    @Override
    public void setRotationAngles(PoeEntity poeEntity, float v, float v1, float v2, float v3, float v4) {

    }


    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
