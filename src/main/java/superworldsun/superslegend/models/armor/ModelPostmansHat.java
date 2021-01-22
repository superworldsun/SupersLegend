package superworldsun.superslegend.models.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelBunnyHood - superworldsun
 * Created using Tabula 7.1.0
 */
public class ModelPostmansHat extends EntityModel<Entity> {
    private final ModelRenderer MaskPostmansHat;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;

    public MaskPostmansHat() {
        textureWidth = 64;
        textureHeight = 64;

        MaskPostmansHat = new ModelRenderer(this);
        MaskPostmansHat.setRotationPoint(0.0F, 22.0F, 0.0F);
        MaskPostmansHat.setTextureOffset(0, 0).addBox(-5.0F, -12.0F, -3.0F, 10.0F, 1.0F, 8.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(0, 9).addBox(-4.5F, -8.475F, -5.05F, 5.0F, 1.0F, 10.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(0, 20).addBox(0.5F, -8.475F, -5.075F, 4.0F, 1.0F, 10.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(40, 18).addBox(4.0F, -11.0F, -3.5F, 1.0F, 3.0F, 8.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(0, 4).addBox(4.0F, -9.5F, -4.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(0, 0).addBox(-5.0F, -9.5F, -4.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(4, 0).addBox(4.0F, -10.5F, -3.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(4, 4).addBox(-5.0F, -10.5F, -3.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(38, 1).addBox(4.5F, -8.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(0, 31).addBox(-5.5F, -8.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        MaskPostmansHat.setTextureOffset(14, 39).addBox(-5.0F, -11.0F, -3.5F, 1.0F, 3.0F, 8.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(1.0F, -7.0F, 5.0F);
        MaskPostmansHat.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -1.5708F, 0.0F);
        cube_r1.setTextureOffset(18, 26).addBox(-1.0F, -4.0F, -4.0F, 1.0F, 3.0F, 10.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, -6.0F, -5.35F);
        MaskPostmansHat.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 1.5708F, 0.0F);
        cube_r2.setTextureOffset(26, 0).addBox(-1.0F, -2.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(0.0F, -6.0F, 4.5F);
        MaskPostmansHat.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 1.5708F, 0.0F);
        cube_r3.setTextureOffset(30, 30).addBox(-1.0F, -2.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(0.0F, -6.3391F, -6.9656F);
        MaskPostmansHat.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.5716F, 0.0F, 0.0F);
        cube_r4.setTextureOffset(20, 13).addBox(-3.0F, -0.4983F, -0.9982F, 6.0F, 1.0F, 2.0F, 0.0F, false);
        cube_r4.setTextureOffset(0, 9).addBox(-2.0F, -0.4958F, -1.9799F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r4.setTextureOffset(20, 11).addBox(-4.0F, -0.5059F, 0.9781F, 8.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setRotationPoint(0.0F, -9.1995F, -4.2144F);
        MaskPostmansHat.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.9992F, 0.0F, 0.0F);
        cube_r5.setTextureOffset(18, 20).addBox(-5.0F, -0.5F, -2.0F, 10.0F, 1.0F, 5.0F, 0.0F, false);
    }

    public ModelPostmansHat() {
    }

    @Override
    public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        MaskPostmansHat.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}