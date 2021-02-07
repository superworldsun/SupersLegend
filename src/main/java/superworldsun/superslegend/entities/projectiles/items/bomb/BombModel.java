package superworldsun.superslegend.entities.projectiles.items.bomb;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;


@OnlyIn(Dist.CLIENT)
public class BombModel extends Model {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(SupersLegend.modid, "textures/entity/projectiles/bomb.png");
    private final ModelRenderer Bomb = new ModelRenderer(128, 128, 0, 0);

    public BombModel() {
        super(RenderType::getEntitySolid);

        this.Bomb.setRotationPoint(0.5F, 18.0F, -0.5F);
        this.Bomb.setTextureOffset(0, 39).addBox(1.4F, -0.6F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
        this.Bomb.setTextureOffset(40, 24).addBox(1.6F, -0.35F, -1.75F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(12, 40).addBox(1.6F, -0.35F, -1.25F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(32, 40).addBox(1.6F, -0.2F, -1.75F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(22, 40).addBox(1.6F, -0.2F, -1.25F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(28, 0).addBox(1.4F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
        this.Bomb.setTextureOffset(35, 5).addBox(-3.4F, -0.6F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
        this.Bomb.setTextureOffset(46, 20).addBox(-3.6F, -0.35F, -1.75F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(40, 36).addBox(-3.6F, -0.2F, -1.25F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(43, 10).addBox(-3.6F, -0.35F, -1.25F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(38, 44).addBox(-3.6F, -0.2F, -1.75F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(28, 10).addBox(-3.4F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
        this.Bomb.setTextureOffset(0, 0).addBox(-3.0F, -0.6F, -2.0F, 5.0F, 5.0F, 5.0F, 0.0F, false);
        this.Bomb.setTextureOffset(0, 25).addBox(-2.25F, -0.4F, -1.75F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(16, 16).addBox(-2.75F, -0.4F, -1.75F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(0, 16).addBox(-2.75F, -0.4F, -1.25F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(16, 6).addBox(-2.25F, -0.4F, -1.25F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(0, 10).addBox(-3.0F, -1.0F, -2.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
        this.Bomb.setTextureOffset(35, 0).addBox(-2.2F, -1.25F, -1.8F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(12, 35).addBox(-2.1F, -1.15F, -1.1F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(0, 34).addBox(-2.9F, -1.15F, -1.1F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(28, 30).addBox(-2.9F, -1.15F, -1.9F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(15, 0).addBox(-2.1F, -1.15F, -1.9F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(28, 35).addBox(-2.8F, -1.25F, -1.2F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(34, 19).addBox(-2.8F, -1.25F, -1.2F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.setTextureOffset(12, 48).addBox(-3.0F, 0.0F, -2.4F, 5.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(33, 52).addBox(-2.75F, -0.2F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(48, 48).addBox(-2.75F, -0.2F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(50, 38).addBox(-2.75F, -0.35F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(24, 48).addBox(-2.25F, -0.35F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(51, 0).addBox(-2.25F, -0.2F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(42, 5).addBox(-3.0F, -0.6F, -2.4F, 5.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(56, 43).addBox(-2.5F, 0.0F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(53, 9).addBox(-3.0F, 0.0F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(56, 22).addBox(-2.5F, -0.6F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(43, 53).addBox(-2.75F, -0.15F, 2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(52, 17).addBox(-2.25F, -0.15F, 2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(11, 53).addBox(-2.25F, -0.4F, 2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(21, 53).addBox(-2.75F, -0.4F, 2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(53, 53).addBox(-3.0F, -0.6F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(55, 27).addBox(-2.0F, -0.6F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(0, 54).addBox(-2.0F, 0.0F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(0, 48).addBox(-2.8F, -0.8F, -2.2F, 5.0F, 5.0F, 1.0F, 0.0F, false);
        this.Bomb.setTextureOffset(27, 20).addBox(-3.2F, -0.8F, -2.2F, 1.0F, 5.0F, 5.0F, 0.0F, false);
        this.Bomb.setTextureOffset(16, 25).addBox(1.2F, -0.8F, -1.8F, 1.0F, 5.0F, 5.0F, 0.0F, false);
        this.Bomb.setTextureOffset(46, 32).addBox(-3.2F, -0.8F, 2.2F, 5.0F, 5.0F, 1.0F, 0.0F, false);

        ModelRenderer stem = new ModelRenderer(this);
        stem.setRotationPoint(0.5F, 1.5432F, 1.5227F);
        this.Bomb.addChild(stem);
        stem.setTextureOffset(44, 44).addBox(-2.5F, -3.0432F, -2.5227F, 3.0F, 1.0F, 3.0F, 0.0F, false);
        stem.setTextureOffset(0, 0).addBox(-1.5F, -3.5432F, -1.5227F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        ModelRenderer wickmiddle_r1 = new ModelRenderer(this);
        wickmiddle_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        stem.addChild(wickmiddle_r1);
        this.setRotationAngle(wickmiddle_r1, 0.3927F, 0.0F, 0.0F);
        wickmiddle_r1.setTextureOffset(0, 10).addBox(-1.5F, -4.4735F, -0.127F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        ModelRenderer wicktop_r1 = new ModelRenderer(this);
        wicktop_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        stem.addChild(wicktop_r1);
        this.setRotationAngle(wicktop_r1, 0.7854F, 0.0F, 0.0F);
        wicktop_r1.setTextureOffset(0, 2).addBox(-1.5F, -4.7989F, 1.5185F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Bomb.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
