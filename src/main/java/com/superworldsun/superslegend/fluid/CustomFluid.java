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
import net.minecraft.tags.ITag;
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
public abstract class CustomFluid extends ForgeFlowingFluid
{
	protected CustomFluid(Properties properties)
	{
		super(properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderBlockOverlayEvent event)
	{
		if (event.getOverlayType() != OverlayType.WATER)
		{
			return;
		}
		
		double d0 = event.getPlayer().getEyeY() - 0.11111111F;
		BlockPos blockpos = new BlockPos(event.getPlayer().getX(), d0, event.getPlayer().getZ());
		FluidState fluidstate = event.getPlayer().level.getFluidState(blockpos);
		Fluid fluid = fluidstate.getType();
		
		if (fluid instanceof CustomFluid && fluidstate.is(((CustomFluid) fluid).getFluidTag()))
		{
			event.setCanceled(true);
			renderOverlay(event.getMatrixStack(), ((CustomFluid) fluid).getOverlayTexture());
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	private static void renderOverlay(MatrixStack matrix, ResourceLocation overlayTexture)
	{
		Minecraft client = Minecraft.getInstance();
		RenderSystem.enableTexture();
		client.getTextureManager().bind(overlayTexture);
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		float f = client.player.getBrightness();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		float f7 = -client.player.yRot / 64.0F;
		float f8 = client.player.xRot / 64.0F;
		Matrix4f matrix4f = matrix.last().pose();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
		bufferbuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).color(f, f, f, 1.0F).uv(4.0F + f7, 4.0F + f8).endVertex();
		bufferbuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).color(f, f, f, 1.0F).uv(0.0F + f7, 4.0F + f8).endVertex();
		bufferbuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).color(f, f, f, 1.0F).uv(0.0F + f7, 0.0F + f8).endVertex();
		bufferbuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).color(f, f, f, 1.0F).uv(4.0F + f7, 0.0F + f8).endVertex();
		bufferbuilder.end();
		WorldVertexBufferUploader.end(bufferbuilder);
		RenderSystem.disableBlend();
	}
	
	protected abstract ITag<Fluid> getFluidTag();
	
	protected abstract ResourceLocation getOverlayTexture();

	public static class Flowing extends CustomFluid
	{
		private final ResourceLocation overlayTexture;
		private final ITag<Fluid> fluidTag;

		public Flowing(ITag<Fluid> fluidTag, ResourceLocation overlayTexture, Properties properties)
		{
			super(properties);
			this.overlayTexture = overlayTexture;
			this.fluidTag = fluidTag;
			registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
		}
		
		protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder)
		{
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}
		
		public int getAmount(FluidState state)
		{
			return state.getValue(LEVEL);
		}
		
		public boolean isSource(FluidState state)
		{
			return false;
		}

		@Override
		protected ITag<Fluid> getFluidTag()
		{
			return fluidTag;
		}

		@Override
		protected ResourceLocation getOverlayTexture()
		{
			return overlayTexture;
		}
	}
	
	public static class Source extends CustomFluid
	{
		private ResourceLocation overlayTexture;
		private ITag<Fluid> fluidTag;

		public Source(ITag<Fluid> fluidTag, ResourceLocation overlayTexture, Properties properties)
		{
			super(properties);
			this.overlayTexture = overlayTexture;
			this.fluidTag = fluidTag;
		}
		
		public int getAmount(FluidState state)
		{
			return 8;
		}
		
		public boolean isSource(FluidState state)
		{
			return true;
		}

		@Override
		protected ITag<Fluid> getFluidTag()
		{
			return fluidTag;
		}

		@Override
		protected ResourceLocation getOverlayTexture()
		{
			return overlayTexture;
		}
	}	
}
