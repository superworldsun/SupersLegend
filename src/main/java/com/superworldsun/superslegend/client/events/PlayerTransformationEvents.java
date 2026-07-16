package com.superworldsun.superslegend.client.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IHandRenderer;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerTransformationEvents {
    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        AbstractClientPlayer player = event.getPlayer();
        IPlayerModelChanger changer = IPlayerModelChanger.get(player);

        if (changer == null) {
            return;
        }

        PlayerModel<AbstractClientPlayer> model = changer.getPlayerModel(player);
        ResourceLocation texture = changer.getPlayerTexture(player);
        model.attackTime = 0.0F;
        model.crouching = false;
        model.swimAmount = 0.0F;
        model.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        model.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        boolean rightArm = event.getArm() == HumanoidArm.RIGHT;
        ModelPart hand = rightArm ? model.rightArm : model.leftArm;
        ModelPart handOverlay = rightArm ? model.rightSleeve : model.leftSleeve;

        if (model instanceof IHandRenderer handRenderer) {
            handRenderer.renderFirstPersonHand(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), player, hand, handOverlay, texture);
        }

        event.setCanceled(true);
    }
}
