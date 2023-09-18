package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.armor.DesertVoeArmorRenderer;
import com.superworldsun.superslegend.client.render.armor.ZoraArmorArmorRenderer;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.UUID;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ZoraArmorArmor extends NonEnchantArmor implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final UUID ZORA_ARMOR_MODIFIER_ID = UUID.fromString("9b86d513-457e-4231-8d90-bdd270ec6748");
    public ZoraArmorArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ZoraArmorArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new ZoraArmorArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    private PlayState predicate(AnimationState animationState){
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide)
        {
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.ZORA_ARMOR_CAP.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.ZORA_ARMOR_TUNIC.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.ZORA_ARMOR_LEGGINGS.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.ZORA_ARMOR_BOOTS.get();
            if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
            {
                if(player.isInWater())
                {
                    player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 0, false, false, false));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }

        // Only if we have flippers
        boolean isHelmetOn = event.player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.ZORA_ARMOR_CAP.get();
        boolean isChestplateOn = event.player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.ZORA_ARMOR_TUNIC.get();
        boolean isLeggingsOn = event.player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.ZORA_ARMOR_LEGGINGS.get();
        boolean isBootsOn = event.player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.ZORA_ARMOR_BOOTS.get();
        if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
        {
            if (event.player.isInWater())
            {

                if(event.player.isInWater()&&event.player.isSprinting())
                {
                    // +50%
                    addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID, 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                }
                else
                {
                    removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID);
                }
            }
        }
        else
        {
            removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID);
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
    }
}
