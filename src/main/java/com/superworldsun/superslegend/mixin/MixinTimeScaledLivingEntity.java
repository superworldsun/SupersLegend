package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import com.superworldsun.superslegend.registries.ModGameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLevel.class)
public abstract class MixinTimeScaledLivingEntity {
    @Redirect(
            method = "tickNonPassenger",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V")
    )
    private void superslegend$scaleMobTick(Entity entity) {
        if (!(entity instanceof Mob)) {
            entity.tick();
            return;
        }

        TimeSongSavedData.Mode mode = TimeSongSavedData.getMode(entity.level());
        boolean scaleMobSpeed = Config.timeSongsAffectMobSpeedAndAttacks()
                && entity.level().getGameRules().getBoolean(ModGameRules.TIME_SONG_AFFECTS_MOB_SPEED);
        boolean scaleAnimalGrowth = Config.timeSongsAffectAnimalGrowth();

        if (!scaleMobSpeed) {
            superslegend$tickWithNormalMobSpeed(entity, mode, scaleAnimalGrowth);
            return;
        }
        if (!scaleAnimalGrowth && entity instanceof AgeableMob ageableMob) {
            superslegend$tickScaledMobWithNormalAge(entity, ageableMob, mode);
            return;
        }

        if (mode != TimeSongSavedData.Mode.INVERTED
                || TimeSongSavedData.shouldRunInvertedTick(entity.level())) {
            entity.tick();
        }
        if (mode == TimeSongSavedData.Mode.DOUBLE && entity.isAlive()) {
            entity.tick();
        }
    }

    @Unique
    private static void superslegend$tickWithNormalMobSpeed(Entity entity, TimeSongSavedData.Mode mode,
                                                             boolean scaleAnimalGrowth) {
        if (!scaleAnimalGrowth || !(entity instanceof AgeableMob ageableMob)
                || mode == TimeSongSavedData.Mode.NORMAL) {
            entity.tick();
            return;
        }

        int ageBeforeTick = ageableMob.getAge();
        entity.tick();
        if (!entity.isAlive()) {
            return;
        }

        int ageAfterTick = ageableMob.getAge();
        if (mode == TimeSongSavedData.Mode.DOUBLE) {
            ageableMob.setAge(superslegend$stepAgeTowardAdult(ageAfterTick));
        } else if (!TimeSongSavedData.shouldRunInvertedTick(entity.level())
                && ageBeforeTick != 0
                && ageAfterTick == superslegend$stepAgeTowardAdult(ageBeforeTick)) {
            ageableMob.setAge(ageBeforeTick);
        }
    }

    @Unique
    private static void superslegend$tickScaledMobWithNormalAge(Entity entity, AgeableMob ageableMob,
                                                                 TimeSongSavedData.Mode mode) {
        if (mode == TimeSongSavedData.Mode.NORMAL) {
            entity.tick();
            return;
        }

        if (mode == TimeSongSavedData.Mode.DOUBLE) {
            entity.tick();
            if (!entity.isAlive()) {
                return;
            }
            int ageAfterNormalTick = ageableMob.getAge();
            entity.tick();
            int ageAfterExtraTick = ageableMob.getAge();
            if (ageAfterNormalTick != 0
                    && ageAfterExtraTick == superslegend$stepAgeTowardAdult(ageAfterNormalTick)) {
                ageableMob.setAge(ageAfterNormalTick);
            }
            return;
        }

        if (TimeSongSavedData.shouldRunInvertedTick(entity.level())) {
            entity.tick();
        } else {
            int currentAge = ageableMob.getAge();
            if (currentAge != 0) {
                ageableMob.setAge(superslegend$stepAgeTowardAdult(currentAge));
            }
        }
    }

    @Unique
    private static int superslegend$stepAgeTowardAdult(int age) {
        if (age < 0) {
            return age + 1;
        }
        if (age > 0) {
            return age - 1;
        }
        return 0;
    }
}
