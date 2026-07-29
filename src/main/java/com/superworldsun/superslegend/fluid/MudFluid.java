package com.superworldsun.superslegend.fluid;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.FluidInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MudFluid extends CustomFluid {
    private static final ResourceLocation STILL_TEXTURE = texture("block/mud_still");
    private static final ResourceLocation FLOWING_TEXTURE = texture("block/mud_flowing");
    private static final ResourceLocation OVERLAY_TEXTURE = texture("block/mud_overlay");

    private static final UUID SWIM_SLOWDOWN_ID = UUID.fromString("91b846fd-adb1-4035-bbff-1ecdd49825fc");
    private static final AttributeModifier SWIM_SLOWDOWN = new AttributeModifier(
            SWIM_SLOWDOWN_ID, "Mud swim slowdown", -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public MudFluid() {
        super(FluidType.Properties.create()
                        .descriptionId("fluid_type.superslegend.mud")
                        .density(2048)
                        .viscosity(10000)
                        .motionScale(0.007D)
                        .fallDistanceModifier(0.0F)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                        .canConvertToSource(true),
                STILL_TEXTURE, FLOWING_TEXTURE, OVERLAY_TEXTURE, 0xFFFFFFFF,
                new Vector3f(0.16F, 0.075F, 0.025F), 8.0F);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) {
            return;
        }

        AttributeInstance swimSpeed = entity.getAttribute(ForgeMod.SWIM_SPEED.get());
        if (swimSpeed == null) {
            return;
        }

        boolean inMud = entity.getFluidTypeHeight(FluidInit.MUD_TYPE.get()) > 0.0D;
        if (inMud) {
            if (swimSpeed.getModifier(SWIM_SLOWDOWN_ID) == null) {
                swimSpeed.addTransientModifier(SWIM_SLOWDOWN);
            }
        } else if (swimSpeed.getModifier(SWIM_SLOWDOWN_ID) != null) {
            swimSpeed.removeModifier(SWIM_SLOWDOWN_ID);
        }
    }

    private static ResourceLocation texture(String path) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, path);
    }
}
