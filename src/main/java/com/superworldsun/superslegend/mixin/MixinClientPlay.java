package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.entities.SpinnerEntity;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MinecartTickableSound;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.math.vector.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayNetHandler.class)
public class MixinClientPlay
{
    @Shadow
    private ClientWorld level;

    @Inject(
            method = "handleAddEntity(Lnet/minecraft/network/play/server/SSpawnObjectPacket;)V",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/network/play/server/SSpawnObjectPacket;getType()Lnet/minecraft/entity/EntityType;"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onEntitySpawn(SSpawnObjectPacket packet, CallbackInfo ci, double x, double y, double z, EntityType<?> type)
    {
        Entity entity;
        if(type == EntityTypeInit.SPINNER.get()) entity = new SpinnerEntity(level, x, y, z);
        else entity = null;

        if(entity != null)
        {
            int entityId = packet.getId();
            entity.setDeltaMovement(Vector3d.ZERO);
            entity.setPos(x, y, z);
            entity.setPacketCoordinates(x, y, z);
            entity.xRot = (float) (packet.getxRot() * 360) / 256.0F;
            entity.yRot = (float) (packet.getyRot() * 360) / 256.0F;
            entity.setId(entityId);
            entity.setUUID(packet.getUUID());
            this.level.putNonPlayerEntity(entityId, entity);
            ci.cancel();
        }
    }
}
