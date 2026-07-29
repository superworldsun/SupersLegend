package com.superworldsun.superslegend.client.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.screen.OcarinaScreen;
import com.superworldsun.superslegend.items.item.FairyOcarina;
import com.superworldsun.superslegend.items.item.OcarinaOfTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class OcarinaPlayerPoseEvents {
    private OcarinaPlayerPoseEvents() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void holdOcarinaAtFace(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        InteractionHand hand = findPlayingHand(player);
        if (hand == null) {
            return;
        }

        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
        HumanoidArm arm = hand == InteractionHand.MAIN_HAND
                ? player.getMainArm()
                : opposite(player.getMainArm());
        if (arm == HumanoidArm.RIGHT) {
            model.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
        } else {
            model.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
    }

    private static InteractionHand findPlayingHand(Player player) {
        Minecraft minecraft = Minecraft.getInstance();
        if (player == minecraft.player && minecraft.screen instanceof OcarinaScreen screen) {
            return screen.getPlayingHand();
        }
        if (player.isUsingItem() && isOcarina(player.getUseItem())) {
            return player.getUsedItemHand();
        }
        return null;
    }

    private static boolean isOcarina(ItemStack stack) {
        return stack.getItem() instanceof OcarinaOfTime || stack.getItem() instanceof FairyOcarina;
    }

    private static HumanoidArm opposite(HumanoidArm arm) {
        return arm == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }
}
