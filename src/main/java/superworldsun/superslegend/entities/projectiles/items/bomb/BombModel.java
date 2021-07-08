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
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(SupersLegend.modid, "textures/item/bomb.png");
    private final ModelRenderer Bomb;

    public BombModel() {
        super(RenderType::entitySolid);
        texWidth = 128;
        texHeight = 128;

        Bomb = new ModelRenderer(this);
        this.Bomb.setPos(0.5F, 18.0F, -0.5F);
        this.Bomb.texOffs(0, 39).addBox(1.4F, -0.6F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
        this.Bomb.texOffs(40, 24).addBox(1.6F, -0.35F, -1.75F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(12, 40).addBox(1.6F, -0.35F, -1.25F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(32, 40).addBox(1.6F, -0.2F, -1.75F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(22, 40).addBox(1.6F, -0.2F, -1.25F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(28, 0).addBox(1.4F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
        this.Bomb.texOffs(35, 5).addBox(-3.4F, -0.6F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
        this.Bomb.texOffs(46, 20).addBox(-3.6F, -0.35F, -1.75F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(40, 36).addBox(-3.6F, -0.2F, -1.25F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(43, 10).addBox(-3.6F, -0.35F, -1.25F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(38, 44).addBox(-3.6F, -0.2F, -1.75F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(28, 10).addBox(-3.4F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
        this.Bomb.texOffs(0, 0).addBox(-3.0F, -0.6F, -2.0F, 5.0F, 5.0F, 5.0F, 0.0F, false);
        this.Bomb.texOffs(0, 25).addBox(-2.25F, -0.4F, -1.75F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(16, 16).addBox(-2.75F, -0.4F, -1.75F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(0, 16).addBox(-2.75F, -0.4F, -1.25F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(16, 6).addBox(-2.25F, -0.4F, -1.25F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(0, 10).addBox(-3.0F, -1.0F, -2.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
        this.Bomb.texOffs(35, 0).addBox(-2.2F, -1.25F, -1.8F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(12, 35).addBox(-2.1F, -1.15F, -1.1F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(0, 34).addBox(-2.9F, -1.15F, -1.1F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(28, 30).addBox(-2.9F, -1.15F, -1.9F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(15, 0).addBox(-2.1F, -1.15F, -1.9F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(28, 35).addBox(-2.8F, -1.25F, -1.2F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(34, 19).addBox(-2.8F, -1.25F, -1.2F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        this.Bomb.texOffs(12, 48).addBox(-3.0F, 0.0F, -2.4F, 5.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(33, 52).addBox(-2.75F, -0.2F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(48, 48).addBox(-2.75F, -0.2F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(50, 38).addBox(-2.75F, -0.35F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(24, 48).addBox(-2.25F, -0.35F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(51, 0).addBox(-2.25F, -0.2F, -2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(42, 5).addBox(-3.0F, -0.6F, -2.4F, 5.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(56, 43).addBox(-2.5F, 0.0F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(53, 9).addBox(-3.0F, 0.0F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(56, 22).addBox(-2.5F, -0.6F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(43, 53).addBox(-2.75F, -0.15F, 2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(52, 17).addBox(-2.25F, -0.15F, 2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(11, 53).addBox(-2.25F, -0.4F, 2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(21, 53).addBox(-2.75F, -0.4F, 2.6F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(53, 53).addBox(-3.0F, -0.6F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(55, 27).addBox(-2.0F, -0.6F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(0, 54).addBox(-2.0F, 0.0F, 2.4F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(0, 48).addBox(-2.8F, -0.8F, -2.2F, 5.0F, 5.0F, 1.0F, 0.0F, false);
        this.Bomb.texOffs(27, 20).addBox(-3.2F, -0.8F, -2.2F, 1.0F, 5.0F, 5.0F, 0.0F, false);
        this.Bomb.texOffs(16, 25).addBox(1.2F, -0.8F, -1.8F, 1.0F, 5.0F, 5.0F, 0.0F, false);
        this.Bomb.texOffs(46, 32).addBox(-3.2F, -0.8F, 2.2F, 5.0F, 5.0F, 1.0F, 0.0F, false);

        ModelRenderer stem = new ModelRenderer(this);
        stem.setPos(0.5F, 1.5432F, 1.5227F);
        this.Bomb.addChild(stem);
        stem.texOffs(44, 44).addBox(-2.5F, -3.0432F, -2.5227F, 3.0F, 1.0F, 3.0F, 0.0F, false);
        stem.texOffs(0, 0).addBox(-1.5F, -3.5432F, -1.5227F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        ModelRenderer wickmiddle_r1 = new ModelRenderer(this);
        wickmiddle_r1.setPos(0.0F, 0.0F, 0.0F);
        stem.addChild(wickmiddle_r1);
        this.setRotationAngle(wickmiddle_r1, 0.3927F, 0.0F, 0.0F);
        wickmiddle_r1.texOffs(0, 10).addBox(-1.5F, -4.4735F, -0.127F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        ModelRenderer wicktop_r1 = new ModelRenderer(this);
        wicktop_r1.setPos(0.0F, 0.0F, 0.0F);
        stem.addChild(wicktop_r1);
        this.setRotationAngle(wicktop_r1, 0.7854F, 0.0F, 0.0F);
        wicktop_r1.texOffs(0, 2).addBox(-1.5F, -4.7989F, 1.5185F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }

    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Bomb.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
