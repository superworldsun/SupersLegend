package superworldsun.superslegend.models.masks;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * MajorasMask - superworldsun
 * Created using Tabula 7.1.0
 */

/*@OnlyIn(Dist.CLIENT)
public class ModelMajorasMask<T extends LivingEntity> extends BipedModel<T>
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
    public RendererModel shape15;
    public RendererModel shape16;
    public RendererModel shape17;
    public RendererModel shape18;
    public RendererModel shape19;
    public RendererModel shape20;

    public ModelMajorasMask() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.shape5 = new RendererModel(this, 63, 121);
        this.shape5.setRotationPoint(-5.0F, -3.0F, -5.0F);
        this.shape5.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.shape15 = new RendererModel(this, 41, 99);
        this.shape15.setRotationPoint(3.6F, -2.9F, -5.0F);
        this.shape15.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, -0.1F);
        this.setRotateAngle(shape15, 0.0F, 0.0F, 0.5415756668938405F);
        this.shape16 = new RendererModel(this, 41, 123);
        this.shape16.setRotationPoint(5.4F, -4.5F, -5.0F);
        this.shape16.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, -0.1F);
        this.setRotateAngle(shape16, 0.0F, 0.0F, 0.5009094953223726F);
        this.shape6 = new RendererModel(this, 98, 122);
        this.shape6.setRotationPoint(-6.0F, -5.0F, -5.0F);
        this.shape6.addBox(0.0F, 0.0F, 0.0F, 12, 2, 1, 0.0F);
        this.shape11 = new RendererModel(this, 100, 38);
        this.shape11.setRotationPoint(-2.2F, -10.8F, -5.0F);
        this.shape11.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, -0.1F);
        this.setRotateAngle(shape11, 0.0F, 0.0F, 1.4514158059584843F);
        this.shape9 = new RendererModel(this, 118, 84);
        this.shape9.setRotationPoint(-3.5F, -8.0F, -5.0F);
        this.shape9.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.shape7 = new RendererModel(this, 96, 118);
        this.shape7.setRotationPoint(-5.0F, -6.0F, -5.0F);
        this.shape7.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.shape10 = new RendererModel(this, 100, 100);
        this.shape10.setRotationPoint(0.5F, -8.0F, -5.0F);
        this.shape10.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.shape4 = new RendererModel(this, 55, 93);
        this.shape4.setRotationPoint(-1.0F, 1.0F, -5.0F);
        this.shape4.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shape13 = new RendererModel(this, 61, 110);
        this.shape13.setRotationPoint(1.1F, -0.1F, -5.0F);
        this.shape13.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, -0.1F);
        this.setRotateAngle(shape13, 0.0F, 0.0F, 0.5462880558742251F);
        this.shape20 = new RendererModel(this, 41, 75);
        this.shape20.setRotationPoint(-4.9F, -3.6F, -5.0F);
        this.shape20.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, -0.1F);
        this.setRotateAngle(shape20, 0.0F, 0.0F, 2.6406831582674206F);
        this.shape14 = new RendererModel(this, 18, 87);
        this.shape14.setRotationPoint(2.3F, -1.7F, -5.0F);
        this.shape14.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, -0.1F);
        this.setRotateAngle(shape14, 0.0F, 0.0F, 0.5918411493512771F);
        this.shape8 = new RendererModel(this, 100, 89);
        this.shape8.setRotationPoint(-4.0F, -7.0F, -5.0F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
        this.shape12 = new RendererModel(this, 82, 44);
        this.shape12.setRotationPoint(3.2F, -10.7F, -5.0F);
        this.shape12.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, -0.1F);
        this.setRotateAngle(shape12, 0.0F, 0.0F, 1.7039649487220636F);
        this.shape17 = new RendererModel(this, 17, 110);
        this.shape17.setRotationPoint(-0.7F, 1.0F, -5.0F);
        this.shape17.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, -0.1F);
        this.setRotateAngle(shape17, 0.0F, 0.0F, 2.7192229746071654F);
        this.shape19 = new RendererModel(this, 41, 86);
        this.shape19.setRotationPoint(-3.1F, -1.8F, -5.0F);
        this.shape19.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, -0.1F);
        this.setRotateAngle(shape19, 0.0F, 0.0F, 2.7272514891663393F);
        this.shape1 = new RendererModel(this, 3, 101);
        this.shape1.setRotationPoint(-4.0F, -2.0F, -5.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
        this.shape2 = new RendererModel(this, 76, 110);
        this.shape2.setRotationPoint(-3.0F, -1.0F, -5.0F);
        this.shape2.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
        this.shape3 = new RendererModel(this, 100, 50);
        this.shape3.setRotationPoint(-2.0F, 0.0F, -5.0F);
        this.shape3.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.shape18 = new RendererModel(this, 68, 91);
        this.shape18.setRotationPoint(-1.7F, -0.7F, -5.0F);
        this.shape18.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, -0.1F);
        this.setRotateAngle(shape18, 0.0F, 0.0F, 2.6469663435746F);
        
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
        this.bipedHead.addChild(shape15);
        this.bipedHead.addChild(shape16);
        this.bipedHead.addChild(shape17);
        this.bipedHead.addChild(shape18);
        this.bipedHead.addChild(shape19);
        this.bipedHead.addChild(shape20);
    }*/

    /*@Override
    public void render(T arrows, float f, float f1, float f2, float f3, float f4, float f5) {
        if (arrows instanceof ArmorStandEntity) {
            f3 = 0;
        }

        super.render(arrows, f, f1, f2, f3, f4, f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    /*public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }*/


