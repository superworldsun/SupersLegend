package superworldsun.superslegend.models.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelBunnyHood - superworldsun
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelPostmansHat<T extends LivingEntity> extends BipedModel<T>
{
    public RendererModel shape1;
    public RendererModel shape2;
    public RendererModel shape3;
    public RendererModel shape4;
    public RendererModel shape5;
    public RendererModel shape6;
    public RendererModel shape7;
    public RendererModel shape8;
    public RendererModel shape9;
    public RendererModel shape10;
    public RendererModel shape11;
    public RendererModel shape12;
    public RendererModel shape13;
    public RendererModel shape14;

    public ModelPostmansHat() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.shape11 = new RendererModel(this, 62, 94);
        this.shape11.setRotationPoint(-4.6F, -6.700000000000004F, -6.4F);
        this.shape11.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1, 0.0F);
        this.setRotateAngle(shape11, 0.6373942428283291F, 0.0F, 0.0F);
        this.shape5 = new RendererModel(this, 45, 120);
        this.shape5.setRotationPoint(-5.0F, -7.0000000000000036F, -5.0F);
        this.shape5.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.shape13 = new RendererModel(this, 57, 85);
        this.shape13.setRotationPoint(-5.12F, -7.300000000000004F, -4.7F);
        this.shape13.addBox(0.0F, 0.0F, 0.0F, 10, 2, 2, 0.0F);
        this.setRotateAngle(shape13, 0.9560913642424937F, 0.0F, 0.0F);
        this.shape8 = new RendererModel(this, 3, 87);
        this.shape8.setRotationPoint(3.8F, -6.5000000000000036F, -6.1F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(shape8, 0.6373942428283291F, 0.0F, 0.0F);
        this.shape10 = new RendererModel(this, 81, 105);
        this.shape10.setRotationPoint(-5.12F, -7.100000000000003F, -5.5F);
        this.shape10.addBox(0.0F, 0.0F, 0.0F, 10, 1, 6, 0.0F);
        this.setRotateAngle(shape10, 0.9560913642424937F, 0.0F, 0.0F);
        this.shape3 = new RendererModel(this, 17, 113);
        this.shape3.setRotationPoint(4.0F, -7.0F, -5.0F);
        this.shape3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 11, 0.0F);
        this.shape4 = new RendererModel(this, 42, 109);
        this.shape4.setRotationPoint(-5.12F, -7.0F, 4.6F);
        this.shape4.addBox(0.0F, 0.0F, 0.0F, 10, 1, 2, 0.0F);
        this.shape9 = new RendererModel(this, 90, 89);
        this.shape9.setRotationPoint(-5.12F, -12.000000000000004F, -2.0F);
        this.shape9.addBox(0.0F, 0.0F, 0.0F, 10, 5, 8, 0.0F);
        this.shape12 = new RendererModel(this, 94, 61);
        this.shape12.setRotationPoint(-5.12F, -6.900000000000004F, -5.0F);
        this.shape12.addBox(0.0F, 0.0F, 0.0F, 10, 1, 6, 0.0F);
        this.setRotateAngle(shape12, 0.9560913642424937F, 0.0F, 0.0F);
        this.shape14 = new RendererModel(this, 65, 118);
        this.shape14.setRotationPoint(-5.12F, -7.300000000000004F, -3.6F);
        this.shape14.addBox(0.0F, 0.0F, 0.0F, 10, 2, 5, 0.0F);
        this.setRotateAngle(shape14, 0.9560913642424937F, 0.0F, 0.0F);
        this.shape2 = new RendererModel(this, 1, 115);
        this.shape2.setRotationPoint(-6.0F, -7.0F, -5.0F);
        this.shape2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 11, 0.0F);
        this.shape1 = new RendererModel(this, 103, 116);
        this.shape1.setRotationPoint(-4.0F, -5.300000000000003F, -7.7F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.setRotateAngle(shape1, 0.6373942428283291F, 0.0F, 0.0F);
        this.shape6 = new RendererModel(this, 72, 115);
        this.shape6.setRotationPoint(-3.0F, -4.700000000000004F, -8.5F);
        this.shape6.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
        this.setRotateAngle(shape6, 0.6373942428283291F, 0.0F, 0.0F);
        this.shape7 = new RendererModel(this, 18, 88);
        this.shape7.setRotationPoint(-4.9F, -6.5000000000000036F, -6.1F);
        this.shape7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(shape7, 0.6373942428283291F, 0.0F, 0.0F);

        this.bipedHead.addChild(shape1);
        this.bipedHead.addChild(shape2);
        this.bipedHead.addChild(shape3);
        this.bipedHead.addChild(shape4);
        this.bipedHead.addChild(shape5);
        this.bipedHead.addChild(shape6);
        this.bipedHead.addChild(shape7);
        this.bipedHead.addChild(shape8);
        this.bipedHead.addChild(shape9);
        this.bipedHead.addChild(shape10);
        this.bipedHead.addChild(shape11);
        this.bipedHead.addChild(shape12);
        this.bipedHead.addChild(shape13);
        this.bipedHead.addChild(shape14);
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {

        super.render(entity, f, f1, f2, f3, f4, f5);

        if (entity instanceof ArmorStandEntity) {
            // Hack so helmets look right on armor stand
            f4 = 0;
        }

    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
