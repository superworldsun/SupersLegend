package com.superworldsun.superslegend.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.superworldsun.superslegend.items.item.LensOfTruth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Reveals invisible entities before either vanilla or modded entity renderers can make their
 * own visibility decisions. The real client-side invisibility state is restored immediately
 * after culling and rendering.
 */
@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher {
    @Unique
    private boolean superslegend$restoreAfterCulling;

    @Unique
    private boolean superslegend$restoreAfterRendering;

    @Inject(method = "shouldRender(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z",
            at = @At("HEAD"))
    private void superslegend$revealBeforeCulling(Entity entity, Frustum frustum,
                                                  double cameraX, double cameraY, double cameraZ,
                                                  CallbackInfoReturnable<Boolean> cir) {
        superslegend$restoreAfterCulling = superslegend$revealForLens(entity);
    }

    @Inject(method = "shouldRender(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z",
            at = @At("RETURN"))
    private void superslegend$restoreAfterCulling(Entity entity, Frustum frustum,
                                                   double cameraX, double cameraY, double cameraZ,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (superslegend$restoreAfterCulling) {
            entity.setInvisible(true);
            superslegend$restoreAfterCulling = false;
        }
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"))
    private void superslegend$revealBeforeRendering(Entity entity, double x, double y, double z,
                                                     float entityYaw, float partialTick, PoseStack poseStack,
                                                     MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        superslegend$restoreAfterRendering = superslegend$revealForLens(entity);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("TAIL"))
    private void superslegend$restoreAfterRendering(Entity entity, double x, double y, double z,
                                                      float entityYaw, float partialTick, PoseStack poseStack,
                                                      MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (superslegend$restoreAfterRendering) {
            entity.setInvisible(true);
            superslegend$restoreAfterRendering = false;
        }
    }

    @Unique
    private static boolean superslegend$revealForLens(Entity entity) {
        Player player = Minecraft.getInstance().player;
        if (!entity.isInvisible() || player == null || !player.isUsingItem()
                || !(player.getItemInHand(player.getUsedItemHand()).getItem() instanceof LensOfTruth)) {
            return false;
        }

        entity.setInvisible(false);
        return true;
    }
}
