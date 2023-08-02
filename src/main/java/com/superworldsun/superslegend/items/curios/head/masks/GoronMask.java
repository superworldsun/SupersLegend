package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IJumpingEntity;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GoronMask extends Item implements ICurioItem {
    private static final UUID GORON_WATER_MODIFIER_ID = UUID.fromString("9198efe1-249e-4dc9-825b-e79aa2d3e2cf");

    public GoronMask(Properties pProperties) {
        super(pProperties);
    }

    //TODO, dosent work, should it slower loosing air
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        Player player = (Player) livingEntity;

            ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            if (!stack0.isEmpty()) {
                int airSupply = livingEntity.getAirSupply();
                // how often mask should grant air supply
                int airSupplyFrequency = 40;

                if (airSupply < livingEntity.getMaxAirSupply() && livingEntity.tickCount % airSupplyFrequency == 0)
                {
                    livingEntity.setAirSupply(airSupply + 1);
                }

                livingEntity.clearFire();
        }
    }

    /*@Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        multimap.put(AttributeInit.HELL_HEAT_RESISTANCE.get(), new AttributeModifier(uuid, "Hardcoded Modifier", 1.0F, AttributeModifier.Operation.ADDITION));
        return multimap;
    }*/

    /*@Override
    public float getScale(Player player)
    {
        return 1.52F;
    }

    @Override
    public float getRenderScale(Player player)
    {
        return 1.0F;
    }*/

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event)
    {
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }

        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), event.getEntity()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if (!maskStack.isEmpty())
        {
            //TODO, damagesource gone
            /*if (event.getSource() == DamageSource.LAVA || event.getSource().isFire())
            {
                event.setCanceled(true);
            }*/
        }
    }

    //TODO, i removed all the errors but crashes for IJumpingEntity
    /*@SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        // Prevent applying changes 2 times per tick
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }

        // Only if we have the mask
        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), event.player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if (!maskStack.isEmpty())
        {
            if (event.player.isInWater())
            {
                IJumpingEntity jumpingPlayer = (IJumpingEntity) event.player;

                if (!jumpingPlayer.isJumping())
                {
                    Vec3 motion = event.player.getDeltaMovement();
                    event.player.setDeltaMovement(motion.x, -0.5, motion.z);
                }
                if (jumpingPlayer.isJumping())
                {
                    Vec3 motion = event.player.getDeltaMovement();
                    event.player.setDeltaMovement(motion.x, -0.5, motion.z);
                }

                if (event.player.onGround() || !event.player.onGround())
                {
                    // -70%
                    addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID, -0.7F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                }
                else
                {
                    removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID);
                }
            }
            else
            {
                // -10%
                addOrReplaceModifier(event.player, Attributes.MOVEMENT_SPEED, GORON_WATER_MODIFIER_ID, -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID);
            }
        }
        else
        {
            removeModifier(event.player, Attributes.MOVEMENT_SPEED, GORON_WATER_MODIFIER_ID);
            removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID);
        }
    }

    private static void removeModifier(Player player, Attribute attribute, UUID id)
    {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null)
        {
            attributeInstance.removeModifier(modifier);
        }
    }

    private static void addOrReplaceModifier(Player player, Attribute attribute, UUID id, float amount, AttributeModifier.Operation operation)
    {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null && modifier.getAmount() != amount)
        {
            attributeInstance.removeModifier(modifier);
            modifier = new AttributeModifier(id, id.toString(), amount, operation);
        }
        else if (modifier == null)
        {
            modifier = new AttributeModifier(id, id.toString(), amount, operation);
        }

        if (modifier != null && !attributeInstance.hasModifier(modifier))
        {
            attributeInstance.addPermanentModifier(modifier);
        }
    }*/

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("This mask is said to").withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.literal("Enhance ones Pig-like senses to smell fungi").withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
