package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.entities.projectiles.bombs.AbstractEntityWaterBomb;
import com.superworldsun.superslegend.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;

import java.time.Duration;
import java.time.Instant;

public class WaterBombRender<T extends AbstractEntityWaterBomb> extends SpriteRenderer<T> {
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    public static final int INITIAL_FLASHING_RATE_IN_MILLISECONDS = 20 * 1000 / 60;
    public static final int RAPID_FLASHING_RATE_IN_MILLISECONDS = 4 * 1000 / 60;

    private ItemRenderer itemRenderer;

    public WaterBombRender(EntityRendererManager renderManagerIn, ItemRenderer itemRendererIn) {
        super(renderManagerIn, itemRendererIn);
        this.itemRenderer = itemRendererIn;
    }

    public WaterBombRender(EntityRendererManager renderManagerIn) {
        this(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
    
    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        float modulationValue = getFlashingModulationValue(entityIn.getCreationTime(), entityIn.shouldFlashRapidly() ? RAPID_FLASHING_RATE_IN_MILLISECONDS : INITIAL_FLASHING_RATE_IN_MILLISECONDS);
        RenderUtil.renderProjectileEntityWithModulation(entityIn, matrixStackIn, bufferIn, packedLightIn, entityRenderDispatcher, itemRenderer, 1.0F, modulationValue, modulationValue);
    }

    private float getFlashingModulationValue(Instant baseTimestamp, int frequencyInMilliSeconds) {
        Instant now = Instant.now();
        long millisSince = Duration.between(baseTimestamp, now).toMillis();
        // This creates a nice sine wave
        double modValue = (Math.sin(((millisSince * 2.0) / frequencyInMilliSeconds - 0.5) * Math.PI) + 1) / 2.0;
        int colorModValue = (int) (255 * modValue);
        return colorModValue;
    }
}

