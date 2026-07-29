package com.superworldsun.superslegend.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GiantGroundParticleMessage {
    private final BlockState blockState;
    private final BlockPos groundPosition;
    private final double x;
    private final double y;
    private final double z;
    private final int count;
    private final float horizontalSpread;
    private final float upwardSpread;
    private final float speed;
    private final float scale;

    public GiantGroundParticleMessage(BlockState blockState,
                                      BlockPos groundPosition,
                                      double x,
                                      double y,
                                      double z,
                                      int count,
                                      float horizontalSpread,
                                      float upwardSpread,
                                      float speed,
                                      float scale) {
        this.blockState = blockState;
        this.groundPosition = groundPosition;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
        this.horizontalSpread = horizontalSpread;
        this.upwardSpread = upwardSpread;
        this.speed = speed;
        this.scale = scale;
    }

    public static GiantGroundParticleMessage decode(FriendlyByteBuf buffer) {
        return new GiantGroundParticleMessage(
                Block.stateById(buffer.readVarInt()),
                buffer.readBlockPos(),
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readVarInt(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat()
        );
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(Block.getId(blockState));
        buffer.writeBlockPos(groundPosition);
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeVarInt(count);
        buffer.writeFloat(horizontalSpread);
        buffer.writeFloat(upwardSpread);
        buffer.writeFloat(speed);
        buffer.writeFloat(scale);
    }

    public static void receive(GiantGroundParticleMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(
                Dist.CLIENT,
                () -> () -> spawnClientParticles(message)
        ));
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void spawnClientParticles(GiantGroundParticleMessage message) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null || message.blockState.isAir()) {
            return;
        }

        RandomSource random = level.random;
        float visualScale = Mth.clamp(message.scale, 1.0F, 8.0F);
        double velocityScale = Math.sqrt(visualScale);
        for (int i = 0; i < message.count; i++) {
            double particleX = message.x + (random.nextDouble() * 2.0D - 1.0D) * message.horizontalSpread;
            double particleY = message.y + random.nextDouble() * message.upwardSpread;
            double particleZ = message.z + (random.nextDouble() * 2.0D - 1.0D) * message.horizontalSpread;
            double xSpeed = (random.nextDouble() * 2.0D - 1.0D) * message.speed * velocityScale;
            double ySpeed = (0.08D + random.nextDouble() * message.speed) * velocityScale;
            double zSpeed = (random.nextDouble() * 2.0D - 1.0D) * message.speed * velocityScale;

            GiantTerrainParticle particle = new GiantTerrainParticle(
                    level,
                    particleX,
                    particleY,
                    particleZ,
                    message.blockState,
                    message.groundPosition,
                    visualScale
            );
            particle.setParticleSpeed(xSpeed, ySpeed, zSpeed);
            minecraft.particleEngine.add(particle);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static class GiantTerrainParticle extends TerrainParticle {
        private GiantTerrainParticle(ClientLevel level,
                                     double x,
                                     double y,
                                     double z,
                                     BlockState blockState,
                                     BlockPos groundPosition,
                                     float scale) {
            super(level, x, y, z, 0.0D, 0.0D, 0.0D, blockState, groundPosition);
            updateSprite(blockState, groundPosition);
            scale(scale);
            lifetime = Math.max(lifetime, Mth.ceil(lifetime * Math.sqrt(scale)));
        }
    }
}
