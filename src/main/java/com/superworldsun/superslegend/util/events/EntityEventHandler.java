package com.superworldsun.superslegend.util.events;

import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SelectInteractionMessage;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

public class EntityEventHandler {

    @SubscribeEvent
    public void onpigjump(LivingEvent.LivingJumpEvent event)
    {
        if(event.getEntityLiving() instanceof PigEntity) {

                event.getEntityLiving().addEffect(new EffectInstance(Effects.LEVITATION, 1000, 5));
                event.getEntityLiving().playSound(SoundInit.PIGFLY.get(), 0.25F, 1.0F);
        }
    }

    @SubscribeEvent
    public void cooldown(LivingEntityUseItemEvent.Start event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            //The above player-check is better than using Minecraft.getInstance().player, as it ensures multiplayer compatibility
            if (PotionUtils.getPotion(event.getItem()) == Potion.byName("minecraft:night_vision")) {
                            event.setCanceled(true);
                        }
                    }
    }

    @SubscribeEvent
    public void addToInventory(GuiScreenEvent event)
    {
        if(event.getGui() instanceof InventoryScreen)
        {
            Button openSelectButton = new Button(event.getGui().width / 2 - 176 / 2 + 154,
                    event.getGui().height / 2 - 23 + 6, 16, 16,
                    new StringTextComponent("SL"), a ->
                    NetworkDispatcher.networkChannel.sendToServer(new SelectInteractionMessage(0, true)));

            ((List<Widget>) ObfuscationReflectionHelper.getPrivateValue(Screen.class, event.getGui(), "buttons"))
                    .add(openSelectButton);
            ((List<IGuiEventListener>) ObfuscationReflectionHelper.getPrivateValue(Screen.class, event.getGui(),
                    "children")).add(openSelectButton);
        }
    }
}
