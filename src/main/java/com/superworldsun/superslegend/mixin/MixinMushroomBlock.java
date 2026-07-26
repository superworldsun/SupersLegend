package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * Adds the Mask of Scents particle effect to vanilla mushrooms and to modded
 * mushrooms that extend Minecraft's {@link MushroomBlock} class.
 */
@Mixin(MushroomBlock.class)
public abstract class MixinMushroomBlock extends BushBlock {
    @Unique
    private static final double SCENT_RANGE = 20.0D;
    @Unique
    private static final int PARTICLE_COUNT = 4;

    protected MixinMushroomBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Player player = Minecraft.getInstance().player;
        if (player == null || !player.isAlive() ||
                CuriosApi.getCuriosHelper().findFirstCurio(player, ItemInit.MASK_MASKOFSCENTS.get()).isEmpty()) {
            return;
        }

        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.35D;
        double z = pos.getZ() + 0.5D;
        if (player.distanceToSqr(x, y, z) > SCENT_RANGE * SCENT_RANGE) {
            return;
        }

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            double particleX = x + (random.nextDouble() - 0.5D) * 0.4D;
            double particleY = y + random.nextDouble() * 0.3D;
            double particleZ = z + (random.nextDouble() - 0.5D) * 0.4D;
            double motionX = (random.nextDouble() - 0.5D) * 0.025D;
            double motionY = 0.04D + random.nextDouble() * 0.04D;
            double motionZ = (random.nextDouble() - 0.5D) * 0.025D;
            level.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ,
                    motionX, motionY, motionZ);
        }
    }
}
