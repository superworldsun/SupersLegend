package com.superworldsun.superslegend.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.client.model.entities.NayrusLoveCrystalModel;
import com.superworldsun.superslegend.entities.NayrusLoveCrystalEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NayrusLoveCrystalRenderer extends GeoEntityRenderer<NayrusLoveCrystalEntity> {
    private static final float ROTATION_DEGREES_PER_TICK = 4.0F;

    public NayrusLoveCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new NayrusLoveCrystalModel());
        shadowRadius = 0.0F;
    }

    @Override
    public void render(NayrusLoveCrystalEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity.isFlashHidden()) {
            return;
        }

        poseStack.pushPose();
        anchorToOwner(entity, partialTick, poseStack);
        // Negative Y rotation is counterclockwise when viewed from above.
        poseStack.mulPose(Axis.YP.rotationDegrees(
                -(entity.tickCount + partialTick) * ROTATION_DEGREES_PER_TICK));
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    private static void anchorToOwner(NayrusLoveCrystalEntity crystal, float partialTick, PoseStack poseStack) {
        Player owner = crystal.getOwnerPlayer();
        if (owner == null) {
            return;
        }

        Vec3 ownerPosition = new Vec3(
                Mth.lerp(partialTick, owner.xo, owner.getX()),
                Mth.lerp(partialTick, owner.yo, owner.getY()),
                Mth.lerp(partialTick, owner.zo, owner.getZ())
        );
        Vec3 crystalPosition = new Vec3(
                Mth.lerp(partialTick, crystal.xo, crystal.getX()),
                Mth.lerp(partialTick, crystal.yo, crystal.getY()),
                Mth.lerp(partialTick, crystal.zo, crystal.getZ())
        );
        Vec3 offset = ownerPosition.subtract(crystalPosition);
        poseStack.translate(offset.x, offset.y, offset.z);
    }

    @Override
    public RenderType getRenderType(NayrusLoveCrystalEntity entity, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        // The supplied texture uses partial alpha; the default GeckoLib cutout layer
        // treats those pixels as opaque.
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public ResourceLocation getTextureLocation(NayrusLoveCrystalEntity entity) {
        return getGeoModel().getTextureResource(entity);
    }
}
