package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaxHealthEvents 
{
    public static final UUID BASE_HEALTH_MODIFIER_ID = UUID.fromString("6ed6de9f-a743-4bee-8e59-8a56d54bb054");

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event)
    {
        // Only change health of players
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }

        Player player = (Player) event.getEntity();
        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        //AttributeModifier baseHealthModifier = new AttributeModifier(BASE_HEALTH_MODIFIER_ID, "Base", Config.getInstance().playerMaxHealth() - 20, AttributeModifier.Operation.ADDITION);

        // Add base modifier only if not added yet
        /*if (!maxHealth.hasModifier(baseHealthModifier))
        {
            maxHealth.addPermanentModifier(baseHealthModifier);
        }
        // Or if config updated and value changed
        else if (maxHealth.getModifier(BASE_HEALTH_MODIFIER_ID).getAmount() != baseHealthModifier.getAmount())
        {
            // Remove old instance and apply new one
            maxHealth.removeModifier(BASE_HEALTH_MODIFIER_ID);
            maxHealth.addPermanentModifier(baseHealthModifier);
        }*/

        // Fixing health being higher then max health
        if (player.getHealth() > player.getMaxHealth())
        {
            player.setHealth(player.getMaxHealth());
        }
    }
}
