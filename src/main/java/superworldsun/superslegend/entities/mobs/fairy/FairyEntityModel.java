package superworldsun.superslegend.entities.mobs.fairy;

//import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
//import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;

/*@OnlyIn(Dist.CLIENT)
public class FairyEntityModel extends AdvancedEntityModel<FairyEntity> {
    // ROOT meaning base for whole model
    private final AdvancedModelBox root;
    // BODY of the model
    private final AdvancedModelBox body;
    // Looking at the model from the BACK - Just in case you get confused about
    // Left side wings
    private final AdvancedModelBox TopLeftWing_r1;
    private final AdvancedModelBox BottomLeftWing_r1;
    // Right side wings
    private final AdvancedModelBox TopRightWing_r1;
    private final AdvancedModelBox BottomRightWing_r1;


    public FairyEntityModel() {
        textureWidth = 16;
        textureHeight = 16;

        // Just the root, Changing these values will move the entities hit box
        root = new AdvancedModelBox(this);
        root.setRotationPoint(0.0F, 10F, 0.0F);


        body = new AdvancedModelBox(this);
        body.setRotationPoint(0.5F, 10F, 0.5F);
        root.addChild(body);
        body.setTextureOffset(0, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        BottomRightWing_r1 = new AdvancedModelBox(this);
        BottomRightWing_r1.setRotationPoint(-0.7F, 0.9384F, 0.5456F);
        body.addChild(BottomRightWing_r1);
        setRotationAngle(BottomRightWing_r1, 0.6981F, -0.3054F, 0.0F);
        BottomRightWing_r1.setTextureOffset(0, 8).addBox(0.0F, 0.0566F, -0.9076F, 0.0F, 2.0F, 2.0F, 0.0F, false);

        TopLeftWing_r1 = new AdvancedModelBox(this);
        TopLeftWing_r1.setRotationPoint(0.5F, 0.5F, -0.1032F);
        body.addChild(TopLeftWing_r1);
        setRotationAngle(TopLeftWing_r1, -0.7854F, 0.3491F, 0.0F);
        TopLeftWing_r1.setTextureOffset(0, 4).addBox(-0.0524F, -4.985F, -0.8434F, 0.0F, 4.0F, 2.0F, 0.0F, false);

        BottomLeftWing_r1 = new AdvancedModelBox(this);
        BottomLeftWing_r1.setRotationPoint(0.75F, 0.9384F, 0.5456F);
        body.addChild(BottomLeftWing_r1);
        setRotationAngle(BottomLeftWing_r1, 0.6981F, 0.2182F, 0.0F);
        BottomLeftWing_r1.setTextureOffset(4, 8).addBox(0.0F, 0.0566F, -0.9076F, 0.0F, 2.0F, 2.0F, 0.0F, false);

        TopRightWing_r1 = new AdvancedModelBox(this);
        TopRightWing_r1.setRotationPoint(-0.75F, -0.5F, 0.9571F);
        body.addChild(TopRightWing_r1);
        setRotationAngle(TopRightWing_r1, -0.7854F, -0.4363F, 0.0F);
        TopRightWing_r1.setTextureOffset(4, 4).addBox(0.0F, -3.4142F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F, false);

        this.updateDefaultPose();
    }


    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(root);
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(root, body, TopLeftWing_r1, BottomLeftWing_r1, TopRightWing_r1, BottomRightWing_r1);
    }


    @Override
    public void setRotationAngles(FairyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.resetToDefaultPose();
        float flySpeed = 1.5F;
        float flyDegree = 0.8F;
        boolean flag = entityIn.isOnGround() && entityIn.getMotion().lengthSquared() < 1.0E-7D;
        if (flag) {
            // Left wings
            this.TopLeftWing_r1.rotateAngleZ = (float) Math.toRadians(-25);
            this.BottomLeftWing_r1.rotateAngleZ = (float) Math.toRadians(-25);
            // Right wings
            this.TopRightWing_r1.rotateAngleZ = (float) Math.toRadians(25);
            this.BottomRightWing_r1.rotateAngleZ = (float) Math.toRadians(25);
        } else {
            this.flap(TopLeftWing_r1, flySpeed * 1.5F, flyDegree, true, 0, 0.1F, ageInTicks, 0.5f);
            this.flap(BottomLeftWing_r1, flySpeed * 1.1F, flyDegree, true, 0, 0.1F, ageInTicks, 0.2f);

            this.flap(TopRightWing_r1, flySpeed * 1.5F, flyDegree, false, 0, 0.1F, ageInTicks, 0.5f);
            this.flap(BottomRightWing_r1, flySpeed * 1.1F, flyDegree, false, 0, 0.1F, ageInTicks, 0.2f);
        }
    }

    public void setRotationAngle(AdvancedModelBox advancedModelBox, float x, float y, float z) {
        advancedModelBox.rotateAngleX = x;
        advancedModelBox.rotateAngleY = y;
        advancedModelBox.rotateAngleZ = z;
    }
}*/
