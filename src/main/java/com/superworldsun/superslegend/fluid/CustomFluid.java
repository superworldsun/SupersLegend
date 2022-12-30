package com.superworldsun.superslegend.fluid;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public abstract class CustomFluid extends ForgeFlowingFluid {
	private ResourceLocation overlayTexture;

	private CustomFluid(ResourceLocation overlayTexture, Properties properties) {
		super(properties);
		this.overlayTexture = overlayTexture;
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderBlockOverlayEvent event) {
		if (event.getOverlayType() != OverlayType.WATER) {
			return;
		}

		BlockPos blockpos = new BlockPos(event.getPlayer().getX(), event.getPlayer().getEyeY() - 0.11111111F, event.getPlayer().getZ());
		FluidState fluidstate = event.getPlayer().level.getFluidState(blockpos);
		Fluid fluid = fluidstate.getType();

		if (fluid instanceof CustomFluid) {
			event.setCanceled(true);
			renderOverlay(event.getMatrixStack(), ((CustomFluid) fluid).overlayTexture);
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static void renderOverlay(MatrixStack matrix, ResourceLocation overlayTexture) {
		Minecraft client = Minecraft.getInstance();
		RenderSystem.enableTexture();
		client.getTextureManager().bind(overlayTexture);
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		float brightness = client.player.getBrightness();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		float uOffset = -client.player.yRot / 64.0F;
		float vOffset = client.player.xRot / 64.0F;
		Matrix4f matrix4f = matrix.last().pose();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
		bufferbuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).color(brightness, brightness, brightness, 1.0F).uv(4.0F + uOffset, 4.0F + vOffset).endVertex();
		bufferbuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).color(brightness, brightness, brightness, 1.0F).uv(0.0F + uOffset, 4.0F + vOffset).endVertex();
		bufferbuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).color(brightness, brightness, brightness, 1.0F).uv(0.0F + uOffset, 0.0F + vOffset).endVertex();
		bufferbuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).color(brightness, brightness, brightness, 1.0F).uv(4.0F + uOffset, 0.0F + vOffset).endVertex();
		bufferbuilder.end();
		WorldVertexBufferUploader.end(bufferbuilder);
		RenderSystem.disableBlend();
	}

	public static class Flowing extends CustomFluid {
		public Flowing(ResourceLocation overlayTexture, Properties properties) {
			super(overlayTexture, properties);
			registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
		}

		protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}

	public static class Source extends CustomFluid {
		public Source(ResourceLocation overlayTexture, Properties properties) {
			super(overlayTexture, properties);
		}

		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}
}
