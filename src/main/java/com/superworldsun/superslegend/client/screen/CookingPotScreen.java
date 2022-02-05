package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.CookingPotContainer;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CookingPotScreen extends ContainerScreen<CookingPotContainer> {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/cooking_pot.png");

    public CookingPotScreen(CookingPotContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageHeight = 184;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public ITextComponent getTitle() {
        return menu.getTileEntity().getBlockState().getBlock().getName();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        ITextComponent title = getTitle();
        font.draw(matrixStack, title, imageWidth / 2.0F - font.width(title) / 2.0F, (float) titleLabelY, 0x404040);
        font.draw(matrixStack, inventory.getDisplayName(), (float) inventoryLabelX, (float) inventoryLabelY, 0x404040);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bind(TEXTURE);

        // Draw Background
        blit(matrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Draw Input Slots
        blit(matrixStack, leftPos + 38, topPos + 16, 176, 97, 36, 36);
        //For fifth slot!
        blit(matrixStack, leftPos + 38, topPos + 16, 176, 97, 36, 16);

        // Draw Process Arrow
        blit(matrixStack, leftPos + 80, topPos + 44, 176, 63, 24, 17);

        // Draw Process Bar
        int cookingProgress = menu.getCookingProgress();
        blit(matrixStack, leftPos + 80, topPos + 43, 176, 80, cookingProgress + 1, 16);

        // Draw Output Slots
        blit(matrixStack, leftPos + 112, topPos + 39, 176, 133, 26, 26);
    }
}
