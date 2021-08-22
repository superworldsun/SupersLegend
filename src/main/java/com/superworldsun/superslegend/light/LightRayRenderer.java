package com.superworldsun.superslegend.light;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
public class LightRayRenderer
{
	private static final ResourceLocation TEXTURE_OUTER = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/block/light.png");
	private static final ResourceLocation TEXTURE_INNER = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/block/light_inner.png");
	
	public static void render(AbstractLightEmitter emitter, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int combinedLight)
	{
		Vector3d lightDirection = emitter.getLightDirection();
		
		if (lightDirection == Vector3d.ZERO)
		{
			return;
		}
		
		matrixStack.pushPose();
		matrixStack.translate(lightDirection.x * 1.001F, lightDirection.y * 1.001F, lightDirection.z * 1.001F);
		matrixStack.translate(0.5D, 0.5D, 0.5D);
		matrixStack.mulPose(Vector3f.YP.rotation((float) (Math.atan2(lightDirection.x, lightDirection.z) - Math.PI / 2)));
		
		if (lightDirection.y != 0)
		{
			matrixStack.mulPose(Vector3f.ZP.rotation((float) Math.atan2(lightDirection.y, lightDirection.x)));
		}
		
		matrixStack.translate(-0.5D, -0.5D, -0.5D);
		float prevRaySize = (float) emitter.prevLightVector.length();
		float raySize = (float) emitter.lightVector.length();
		matrixStack.scale(prevRaySize + (raySize - prevRaySize) * partialTicks, 1, 1);
		float outerRayWidth = 14;
		float innerRayWidth = 6;
		
		float outerRayOffset = ((16F - outerRayWidth) / 2F) / 16F;
		matrixStack.translate(0, outerRayOffset, outerRayOffset);
		matrixStack.scale(1, outerRayWidth / 16F, outerRayWidth / 16F);
		drawCubeQuads(matrixStack, renderBuffer, RenderType.beaconBeam(TEXTURE_OUTER, true), combinedLight);
		matrixStack.scale(1, 16F / outerRayWidth, 16F / outerRayWidth);
		matrixStack.translate(0, -outerRayOffset, -outerRayOffset);
		
		float innerRayOffset = ((16F - innerRayWidth) / 2F) / 16F;
		matrixStack.translate(0, innerRayOffset, innerRayOffset);
		matrixStack.scale(1, innerRayWidth / 16F, innerRayWidth / 16F);
		drawCubeQuads(matrixStack, renderBuffer, RenderType.beaconBeam(TEXTURE_INNER, false), combinedLight);
		
		matrixStack.popPose();
	}
	
	private static void drawCubeQuads(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, RenderType renderType, int combinedLight)
	{
		IVertexBuilder vertexBuilderBlockQuads = renderBuffer.getBuffer(renderType);
		
		Matrix4f matrixPos = matrixStack.last().pose();
		Matrix3f matrixNormal = matrixStack.last().normal();
		Vector2f bottomLeftUV = new Vector2f(0.0F, 1.0F);
		
		float UVwidth = 1.0F;
		float UVheight = 1.0F;
		
		final float WIDTH = 1.0F;
		final float HEIGHT = 1.0F;
		
		final Vector3d EAST_FACE_MIDPOINT = new Vector3d(1.0, 0.5, 0.5);
		final Vector3d WEST_FACE_MIDPOINT = new Vector3d(0.0, 0.5, 0.5);
		final Vector3d NORTH_FACE_MIDPOINT = new Vector3d(0.5, 0.5, 0.0);
		final Vector3d SOUTH_FACE_MIDPOINT = new Vector3d(0.5, 0.5, 1.0);
		final Vector3d UP_FACE_MIDPOINT = new Vector3d(0.5, 1.0, 0.5);
		final Vector3d DOWN_FACE_MIDPOINT = new Vector3d(0.5, 0.0, 0.5);
		
		addFace(Direction.EAST, matrixPos, matrixNormal, vertexBuilderBlockQuads, EAST_FACE_MIDPOINT, WIDTH, HEIGHT, bottomLeftUV, UVwidth, UVheight,
				combinedLight);
		addFace(Direction.WEST, matrixPos, matrixNormal, vertexBuilderBlockQuads, WEST_FACE_MIDPOINT, WIDTH, HEIGHT, bottomLeftUV, UVwidth, UVheight,
				combinedLight);
		addFace(Direction.NORTH, matrixPos, matrixNormal, vertexBuilderBlockQuads, NORTH_FACE_MIDPOINT, WIDTH, HEIGHT, bottomLeftUV, UVwidth, UVheight,
				combinedLight);
		addFace(Direction.SOUTH, matrixPos, matrixNormal, vertexBuilderBlockQuads, SOUTH_FACE_MIDPOINT, WIDTH, HEIGHT, bottomLeftUV, UVwidth, UVheight,
				combinedLight);
		addFace(Direction.UP, matrixPos, matrixNormal, vertexBuilderBlockQuads, UP_FACE_MIDPOINT, WIDTH, HEIGHT, bottomLeftUV, UVwidth, UVheight,
				combinedLight);
		addFace(Direction.DOWN, matrixPos, matrixNormal, vertexBuilderBlockQuads, DOWN_FACE_MIDPOINT, WIDTH, HEIGHT, bottomLeftUV, UVwidth, UVheight,
				combinedLight);
	}
	
	private static void addFace(Direction whichFace, Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder renderBuffer, Vector3d centrePos, float width,
			float height, Vector2f bottomLeftUV, float texUwidth, float texVheight, int lightmapValue)
	{
		Vector3f leftToRightDirection, bottomToTopDirection;
		
		switch (whichFace)
		{
			case NORTH:
			{ // bottom left is east
				leftToRightDirection = new Vector3f(-1, 0, 0);
				bottomToTopDirection = new Vector3f(0, 1, 0);
				break;
			}
			case SOUTH:
			{ // bottom left is west
				leftToRightDirection = new Vector3f(1, 0, 0);
				bottomToTopDirection = new Vector3f(0, 1, 0);
				break;
			}
			case EAST:
			{ // bottom left is south
				leftToRightDirection = new Vector3f(0, 0, -1);
				bottomToTopDirection = new Vector3f(0, 1, 0);
				break;
			}
			case WEST:
			{ // bottom left is north
				leftToRightDirection = new Vector3f(0, 0, 1);
				bottomToTopDirection = new Vector3f(0, 1, 0);
				break;
			}
			case UP:
			{ // bottom left is southwest by minecraft block convention
				leftToRightDirection = new Vector3f(-1, 0, 0);
				bottomToTopDirection = new Vector3f(0, 0, 1);
				break;
			}
			case DOWN:
			{ // bottom left is northwest by minecraft block convention
				leftToRightDirection = new Vector3f(1, 0, 0);
				bottomToTopDirection = new Vector3f(0, 0, 1);
				break;
			}
			default:
			{ // should never get here, but just in case;
				leftToRightDirection = new Vector3f(0, 0, 1);
				bottomToTopDirection = new Vector3f(0, 1, 0);
				break;
			}
		}
		
		leftToRightDirection.mul(0.5F * width);
		bottomToTopDirection.mul(0.5F * height);
		
		Vector3f bottomLeftPos = new Vector3f(centrePos);
		bottomLeftPos.sub(leftToRightDirection);
		bottomLeftPos.sub(bottomToTopDirection);
		
		Vector3f bottomRightPos = new Vector3f(centrePos);
		bottomRightPos.add(leftToRightDirection);
		bottomRightPos.sub(bottomToTopDirection);
		
		Vector3f topRightPos = new Vector3f(centrePos);
		topRightPos.add(leftToRightDirection);
		topRightPos.add(bottomToTopDirection);
		
		Vector3f topLeftPos = new Vector3f(centrePos);
		topLeftPos.sub(leftToRightDirection);
		topLeftPos.add(bottomToTopDirection);
		
		Vector2f bottomLeftUVpos = new Vector2f(bottomLeftUV.x, bottomLeftUV.y);
		Vector2f bottomRightUVpos = new Vector2f(bottomLeftUV.x + texUwidth, bottomLeftUV.y);
		Vector2f topLeftUVpos = new Vector2f(bottomLeftUV.x + texUwidth, bottomLeftUV.y + texVheight);
		Vector2f topRightUVpos = new Vector2f(bottomLeftUV.x, bottomLeftUV.y + texVheight);
		
		Vector3f normalVector = whichFace.step();
		
		addQuad(matrixPos, matrixNormal, renderBuffer, bottomLeftPos, bottomRightPos, topRightPos, topLeftPos, bottomLeftUVpos, bottomRightUVpos, topLeftUVpos,
				topRightUVpos, normalVector, lightmapValue);
	}
	
	private static void addQuad(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder renderBuffer, Vector3f blpos, Vector3f brpos, Vector3f trpos,
			Vector3f tlpos, Vector2f blUVpos, Vector2f brUVpos, Vector2f trUVpos, Vector2f tlUVpos, Vector3f normalVector, int lightmapValue)
	{
		addVertex(matrixPos, matrixNormal, renderBuffer, blpos, blUVpos, normalVector, lightmapValue);
		addVertex(matrixPos, matrixNormal, renderBuffer, brpos, brUVpos, normalVector, lightmapValue);
		addVertex(matrixPos, matrixNormal, renderBuffer, trpos, trUVpos, normalVector, lightmapValue);
		addVertex(matrixPos, matrixNormal, renderBuffer, tlpos, tlUVpos, normalVector, lightmapValue);
		addVertex(matrixPos, matrixNormal, renderBuffer, tlpos, tlUVpos, normalVector, lightmapValue);
		addVertex(matrixPos, matrixNormal, renderBuffer, trpos, trUVpos, normalVector, lightmapValue);
		addVertex(matrixPos, matrixNormal, renderBuffer, brpos, brUVpos, normalVector, lightmapValue);
		addVertex(matrixPos, matrixNormal, renderBuffer, blpos, blUVpos, normalVector, lightmapValue);
	}
	
	private static void addVertex(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder renderBuffer, Vector3f pos, Vector2f uv, Vector3f normal,
			int lightmap)
	{
		renderBuffer.vertex(matrixPos, pos.x(), pos.y(), pos.z()).color(1F, 1F, 1F, 1F).uv(uv.x, uv.y).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmap)
				.normal(matrixNormal, normal.x(), normal.y(), normal.z()).endVertex();
	}
}
