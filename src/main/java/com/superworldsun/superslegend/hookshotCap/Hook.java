package com.superworldsun.superslegend.hookshotCap;




import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Hook {

    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            HookModel skillModel = new HookModel();

            HookProvider provider = new HookProvider(skillModel);

            event.addCapability(new ResourceLocation("zelda_hs", "cap_hook"), provider);

            event.addListener(provider::invalidate);
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
        SyncToClient.send(e.getPlayer());

    }

    @SubscribeEvent
    public void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        SyncToClient.send(e.getPlayer());

    }
}
