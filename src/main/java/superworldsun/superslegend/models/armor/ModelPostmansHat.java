package superworldsun.superslegend.models.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.LivingEntity;


@SuppressWarnings("rawtypes")
public class ModelPostmansHat<T extends LivingEntity> extends BipedModel<T>
{
	private final RendererModel bone;


    public ModelPostmansHat() {
    	this.textureWidth = 16;
        this.textureHeight = 16;
        
        this.bone = new RendererModel(this);
        this.bone.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.bone.cubeList.add(new ModelBox(bone, 0, 0, 2.0F, -10.0F, -2.0F, 1, 1, 6, 0.0F, false));
        this.bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -10.0F, -2.0F, 1, 1, 6, 0.0F, false));
        this.bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -11.0F, -3.0F, 7, 1, 7, 0.0F, false));
        this.bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -10.0F, -5.0F, 7, 1, 3, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -10.0F, 4.0F, 5, 1, 1, 0.0F, false));
		this.bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -12.0F, -2.0F, 5, 1, 6, 0.0F, false));
		
		this.bipedHead.addChild(bone);
	}
    
    @SuppressWarnings("unchecked")
	public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
    public void setupAngles(BipedModel<T> model)
    {
    	setRotateAngle(model.bipedHead, this.bone);
    }
    
    
    private static void setRotateAngle(RendererModel source, RendererModel target)
    {
        target.offsetX = source.offsetX;
        target.offsetY = source.offsetY;
        target.offsetZ = source.offsetZ;
        target.copyModelAngles(source);
    }

}