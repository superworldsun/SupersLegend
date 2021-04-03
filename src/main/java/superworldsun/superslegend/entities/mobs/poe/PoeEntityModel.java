package superworldsun.superslegend.entities.mobs.poe;

//import com.github.alexthe666.citadel.animation.IAnimatedEntity;
//import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
//import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
//import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/*@OnlyIn(Dist.CLIENT)
public class PoeEntityModel extends AdvancedEntityModel<PoeEntity> {
	public AdvancedModelBox Body;
	public AdvancedModelBox LeftArm;
	public AdvancedModelBox LeftShoulder;
	public AdvancedModelBox LeftLowerArm;
	public AdvancedModelBox LeftLowerArmCube_r1;
	public AdvancedModelBox RightArm;
	public AdvancedModelBox RightShoulder;
	public AdvancedModelBox RightShoulderCube_r1;
	public AdvancedModelBox RightLowerArm;
	public AdvancedModelBox RightLowerArmCube_r1;
	public AdvancedModelBox Lantern;
	public AdvancedModelBox Head;
	public AdvancedModelBox LeftEye_r1;
	public AdvancedModelBox RightEye_r1;
	public AdvancedModelBox nose_r1;
	public AdvancedModelBox Hood;
	private final ModelAnimator animator;

	public PoeEntityModel() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.Body = new AdvancedModelBox(this);
		this.Body.setRotationPoint(0.15F, 4.0F, 0.0F);
		this.Body.setTextureOffset(0, 26).addBox(-3.15F, 0.0F, -3.0F, 6.0F, 12.0F, 6.0F, 0.0F, false);

		this.LeftArm = new AdvancedModelBox(this);
		this.LeftArm.setRotationPoint(1.85F, 2.0F, 1.0F);
		this.Body.addChild(LeftArm);

		this.LeftShoulder = new AdvancedModelBox(this);
		this.LeftShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LeftArm.addChild(LeftShoulder);
		this.setRotationAngle(LeftShoulder, 0.0F, 0.0F, 0.8727F);
		this.LeftShoulder.setTextureOffset(22, 43).addBox(0.0F, -1.3572F, -1.25F, 6.0F, 2.0F, 2.0F, 0.0F, false);

		this.LeftLowerArm = new AdvancedModelBox(this);
		this.LeftLowerArm.setRotationPoint(4.6499F, -0.5031F, 0.196F);
		this.LeftShoulder.addChild(LeftLowerArm);
		this.setRotationAngle(LeftLowerArm, 0.0F, 0.48F, 0.8727F);

		this.LeftLowerArmCube_r1 = new AdvancedModelBox(this);
		this.LeftLowerArmCube_r1.setRotationPoint(0.2633F, 0.1836F, 0.0676F);
		this.LeftLowerArm.addChild(LeftLowerArmCube_r1);
		this.setRotationAngle(LeftLowerArmCube_r1, 0.3491F, 0.0436F, -0.829F);
		this.LeftLowerArmCube_r1.setTextureOffset(42, 29).addBox(0.9955F, -1.1567F, -0.9507F, 4.0F, 2.0F, 2.0F, 0.0F, false);

		this.RightArm = new AdvancedModelBox(this);
		this.RightArm.setRotationPoint(-2.15F, 2.0F, 0.25F);
		this.Body.addChild(RightArm);
		this.setRotationAngle(RightArm, 0.0F, 0.1309F, 1.4399F);


		this.RightShoulder = new AdvancedModelBox(this);
		this.RightShoulder.setRotationPoint(-0.1218F, 0.9055F, 0.155F);
		this.RightArm.addChild(RightShoulder);
		this.setRotationAngle(RightShoulder, 0.0F, 0.0F, 0.8727F);

		this.RightShoulderCube_r1 = new AdvancedModelBox(this);
		this.RightShoulderCube_r1.setRotationPoint(2.4728F, -0.205F, 1.0295F);
		this.RightShoulder.addChild(RightShoulderCube_r1);
		this.setRotationAngle(RightShoulderCube_r1, -0.1309F, -0.0873F, 0.0436F);
		this.RightShoulderCube_r1.setTextureOffset(42, 42).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);

		this.RightLowerArm = new AdvancedModelBox(this);
		this.RightLowerArm.setRotationPoint(4.691F, 0.4491F, 1.5291F);
		this.RightShoulder.addChild(RightLowerArm);
		this.setRotationAngle(RightLowerArm, 0.0F, 0.48F, 0.8727F);

		this.RightLowerArmCube_r1 = new AdvancedModelBox(this);
		this.RightLowerArmCube_r1.setRotationPoint(0.0265F, -0.913F, 0.0012F);
		this.RightLowerArm.addChild(RightLowerArmCube_r1);
		this.setRotationAngle(RightLowerArmCube_r1, 0.2182F, 0.0436F, -0.829F);
		this.RightLowerArmCube_r1.setTextureOffset(42, 29).addBox(-0.2943F, -0.9386F, -1.2946F, 4.0F, 2.0F, 2.0F, 0.0F, false);

		this.Lantern = new AdvancedModelBox(this);
		this.Lantern.setRotationPoint(1.428F, -6.9193F, -0.5592F);
		this.RightLowerArm.addChild(Lantern);
		this.setRotationAngle(Lantern, 3.098F, -0.48F, -0.0436F);
		this.Lantern.setTextureOffset(28, 22).addBox(-1.1813F, -1.4583F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
		this.Lantern.setTextureOffset(0, 44).addBox(-0.6813F, -1.9583F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
		this.Lantern.setTextureOffset(43, 6).addBox(-0.6813F, 2.7917F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
		this.Lantern.setTextureOffset(0, 0).addBox(-0.1813F, 3.0417F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		this.Lantern.setTextureOffset(6, 6).addBox(0.3187F, -3.4583F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		this.Lantern.setTextureOffset(0, 3).addBox(-0.6813F, -4.4583F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		this.Head = new AdvancedModelBox(this);
		this.Head.setRotationPoint(-0.15F, 0.0F, 0.0F);
		this.Body.addChild(Head);
		this.Head.setTextureOffset(0, 10).addBox(-4.0F, -8.025F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		this.LeftEye_r1 = new AdvancedModelBox(this);
		this.LeftEye_r1.setRotationPoint(1.75F, -4.225F, -4.0F);
		this.Head.addChild(LeftEye_r1);
		this.setRotationAngle(LeftEye_r1, 0.0F, 0.0F, -0.3927F);
		this.LeftEye_r1.setTextureOffset(0, 7).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		this.RightEye_r1 = new AdvancedModelBox(this);
		this.RightEye_r1.setRotationPoint(-1.75F, -4.225F, -4.0F);
		this.Head.addChild(RightEye_r1);
		this.setRotationAngle(RightEye_r1, 0.0F, 0.0F, 0.3927F);
		this.RightEye_r1.setTextureOffset(0, 5).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		this.nose_r1 = new AdvancedModelBox(this);
		this.nose_r1.setRotationPoint(0.0F, -1.8512F, -4.0123F);
		this.Head.addChild(nose_r1);
		this.setRotationAngle(nose_r1, -0.3927F, 0.0F, 0.0F);
		this.nose_r1.setTextureOffset(2, 9).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		this.Hood = new AdvancedModelBox(this);
		this.Hood.setRotationPoint(0.0F, -0.0625F, -0.125F);
		this.Head.addChild(Hood);
		this.Hood.setTextureOffset(40, 23).addBox(-4.5F, -1.9375F, -5.125F, 9.0F, 2.0F, 0.0F, 0.0F, false);
		this.Hood.setTextureOffset(0, 9).addBox(3.5F, -6.9375F, -5.125F, 1.0F, 5.0F, 0.0F, 0.0F, false);
		this.Hood.setTextureOffset(32, 0).addBox(4.5F, -8.9375F, -5.125F, 0.0F, 9.0F, 10.0F, 0.0F, false);
		this.Hood.setTextureOffset(24, 31).addBox(-4.5F, -8.9375F, -5.125F, 9.0F, 2.0F, 0.0F, 0.0F, false);
		this.Hood.setTextureOffset(8, 0).addBox(-4.5F, -6.9375F, -5.125F, 1.0F, 5.0F, 0.0F, 0.0F, false);
		this.Hood.setTextureOffset(28, 0).addBox(-4.5F, -8.9375F, 4.875F, 9.0F, 9.0F, 0.0F, 0.0F, false);
		this.Hood.setTextureOffset(0, 0).addBox(-4.5F, -8.9375F, -5.125F, 9.0F, 0.0F, 10.0F, 0.0F, false);
		this.Hood.setTextureOffset(24, 24).addBox(-4.5F, -8.9375F, -5.125F, 0.0F, 9.0F, 10.0F, 0.0F, false);

		animator = ModelAnimator.create();
		this.updateDefaultPose();

	}

	@Override
	public Iterable<AdvancedModelBox> getAllParts() {
		return ImmutableList.of(
		this.Body,
		this.LeftArm,
		this.LeftShoulder,
		this.LeftLowerArm,
		this.LeftLowerArmCube_r1,
		this.RightArm,
		this.RightShoulder,
		this.RightShoulderCube_r1,
		this.RightLowerArm,
		this.RightLowerArmCube_r1,
		this.Lantern,
		this.Head,
		this.LeftEye_r1,
		this.RightEye_r1,
		this.nose_r1,
		this.Hood
		);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.Body);
	}

	public void setRotationAngles(PoeEntity entity, float f, float f1, float f2, float f3, float f4) {
		animate(entity, f, f1, f2, f3, f4, 1);
		this.faceTarget(f3, f4, 1, this.Head);
		float speed_walk = 0.6F;
		float speed_idle = 0.05F;
		float degree_walk = 1F;
		float degree_idle = 0.25F;

		float f12 = (float) Math.toRadians(-1.29f) + f1;
		if (f12 < 0.0F) {
			f12 = 0.0F;
		}
		if (f12 > Math.toRadians(20)) {
			f12 = (float) Math.toRadians(20);
		}
		this.Body.rotateAngleX = f12;
		this.Head.rotateAngleX = this.Head.rotateAngleX - f12;
		this.RightArm.rotateAngleX = this.RightArm.rotateAngleX - f12;
		this.LeftArm.rotateAngleX = this.LeftArm.rotateAngleX - f12;

		this.walk(RightArm, speed_idle * 1.5F, degree_idle * 0.4F, false, 2, 0.0F, f2, 1);
		this.walk(LeftArm, speed_idle * 1.5F, degree_idle * 0.4F, true, 2, 0.0F, f2, 1);

		this.flap(RightArm, speed_idle * 1.5F, degree_idle * 0.2F, false, 2, 0.2F, f2, 1);
		this.flap(RightArm, speed_idle * 1.5F, degree_idle * 0.2F, true, 2, 0.2F, f2, 1);

		this.flap(Body, speed_idle * 1.1F, degree_idle * 0.1F, true, 3, 0, f2, 1);
		this.bob(Body, speed_idle * 0.5F, degree_idle * 4.1F, false,  f2, 1);
		this.bob(Body, speed_walk * 0.75F, degree_walk * 2.1F, false,  f, f1);
		if (entity.isCharging()) {
			this.RightArm.rotateAngleZ = 2.5F;
			this.RightArm.rotateAngleX = -1F;
			this.RightArm.rotateAngleY = 0.5F;
			this.RightEye_r1.rotateAngleX = 0.2F;
			this.LeftEye_r1.rotateAngleX = -0.2F;
			this.swing(Lantern, 1F, 3, true, 1, 1, 1, 1);
			this.LeftArm.rotateAngleX = 1F;
		}

	}

	public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.resetToDefaultPose();
		animator.update(entity);
		animator.setAnimation(PoeEntity.ANIMATION_HIT);
		animator.startKeyframe(5);
		animator.move(Head, 0, -1, 0);
		this.rotateMinus(animator, Body, 0, 0F, 0F);
		this.rotateMinus(animator, Head, 0, 1F, 0F);
		this.rotateMinus(animator, RightArm, -180F, 1F, 25);
		this.rotateMinus(animator, LeftArm, 1F, 0.1F, 0);
		this.rotateMinus(animator, Lantern, -0.5F, 1F, 0);
		animator.endKeyframe();
		animator.resetKeyframe(5);
	}

	public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
		AdvancedModelBox.rotateAngleX = x;
		AdvancedModelBox.rotateAngleY = y;
		AdvancedModelBox.rotateAngleZ = z;
	}
}
*/