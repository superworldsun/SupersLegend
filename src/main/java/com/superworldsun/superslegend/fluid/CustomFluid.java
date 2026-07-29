package com.superworldsun.superslegend.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

/**
 * Shared fluid type implementation for fluids with custom still, flowing, and
 * first-person overlay textures.
 */
public abstract class CustomFluid extends FluidType {
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final ResourceLocation renderOverlayTexture;
    private final int tintColor;
    private final Vector3f fogColor;
    private final float fogDistance;

    protected CustomFluid(Properties properties, ResourceLocation stillTexture,
                          ResourceLocation flowingTexture, ResourceLocation overlayTexture,
                          int tintColor, Vector3f fogColor, float fogDistance) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.renderOverlayTexture = new ResourceLocation(overlayTexture.getNamespace(),
                "textures/" + overlayTexture.getPath() + ".png");
        this.tintColor = tintColor;
        this.fogColor = fogColor;
        this.fogDistance = fogDistance;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return overlayTexture;
            }

            @Override
            public ResourceLocation getRenderOverlayTexture(Minecraft minecraft) {
                return renderOverlayTexture;
            }

            @Override
            public int getTintColor() {
                return tintColor;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick,
                                                    ClientLevel level, int renderDistance,
                                                    float darkenWorldAmount, Vector3f fluidFogColor) {
                return new Vector3f(fogColor);
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode,
                                        float renderDistance, float partialTick,
                                        float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(0.0F);
                RenderSystem.setShaderFogEnd(Math.min(renderDistance, fogDistance));
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
            }
        });
    }
}
