package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.items.item.FairyOcarina;
import com.superworldsun.superslegend.items.item.OcarinaOfTime;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayOcarinaNoteMessage {
    private static final int NOTE_COUNT = 5;
    private static final double NOTE_PARTICLE_BASE_HEIGHT = 0.12D;

    private final int note;

    public PlayOcarinaNoteMessage(int note) {
        this.note = note;
    }

    public static PlayOcarinaNoteMessage decode(FriendlyByteBuf buffer) {
        return new PlayOcarinaNoteMessage(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(note);
    }

    public static void receive(PlayOcarinaNoteMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null || message.note < 0 || message.note >= NOTE_COUNT || !isHoldingOcarina(player)) {
                return;
            }

            ServerLevel level = player.serverLevel();
            level.playSound(player, player.getX(), player.getEyeY(), player.getZ(),
                    getNoteSound(message.note), SoundSource.PLAYERS, 1.0F, 1.0F);

            Vec3 particlePosition = player.getEyePosition()
                    .add(player.getViewVector(1.0F).scale(0.7D))
                    .add(0.0D, NOTE_PARTICLE_BASE_HEIGHT + getNoteHeight(message.note), 0.0D);
            double noteColor = message.note / 24.0D;
            level.sendParticles(ParticleTypes.NOTE,
                    particlePosition.x, particlePosition.y, particlePosition.z,
                    0, noteColor, 0.0D, 0.0D, 1.0D);
        });
        context.setPacketHandled(true);
    }

    private static boolean isHoldingOcarina(ServerPlayer player) {
        return isOcarina(player.getMainHandItem()) || isOcarina(player.getOffhandItem());
    }

    private static boolean isOcarina(ItemStack stack) {
        return stack.getItem() instanceof OcarinaOfTime || stack.getItem() instanceof FairyOcarina;
    }

    private static SoundEvent getNoteSound(int note) {
        return switch (note) {
            case 0 -> SoundInit.OCARINA_NOTE_U.get();
            case 1 -> SoundInit.OCARINA_NOTE_L.get();
            case 2 -> SoundInit.OCARINA_NOTE_R.get();
            case 3 -> SoundInit.OCARINA_NOTE_D.get();
            default -> SoundInit.OCARINA_NOTE_A.get();
        };
    }

    private static double getNoteHeight(int note) {
        return switch (note) {
            case 0 -> 0.45D;  // Up / highest note
            case 1 -> 0.375D; // Left
            case 2 -> 0.20D;  // Right
            case 3 -> 0.10D;  // Down
            default -> 0.0D;  // A / lowest note
        };
    }
}
