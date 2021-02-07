package superworldsun.superslegend.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.entities.projectiles.items.bomb.BombModel;
import superworldsun.superslegend.lists.ItemList;

@OnlyIn(Dist.CLIENT)
public class ItemTileEntityRenderer {
    public static final ItemTileEntityRenderer instance = new ItemTileEntityRenderer();
    private final BombModel Bomb = new BombModel();

    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Item item = stack.getItem();
        if (item == ItemList.bomb) {
            matrixStack.push();
            matrixStack.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder1 = ItemRenderer.getEntityGlintVertexBuilder(buffer, this.Bomb.getRenderType(BombModel.TEXTURE_LOCATION), false, stack.hasEffect());
            this.Bomb.render(matrixStack, ivertexbuilder1, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }

    }
}