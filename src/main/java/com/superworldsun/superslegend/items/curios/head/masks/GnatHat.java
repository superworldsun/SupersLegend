package com.superworldsun.superslegend.items.curios.head.masks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.GnatHatModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GnatHat extends NonEnchantItem implements IEntityResizer, ICurioItem
{
    private static final UUID GNATS_MASK_REACH_MODIFIER_ID = UUID.fromString("56978eb7-a1ff-4a5b-b662-bdb3ddc2c0d5");
    private static final UUID GNATS_MASK_DAMAGE_MODIFIER_ID = UUID.fromString("13f98ee4-a207-4d9c-a5c1-26b6c7a1bde8");

    @OnlyIn(Dist.CLIENT)
    private Object model;
    // put your texture here
    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/armor/gnat_hat.png");

    private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();

    public GnatHat(Properties properties) {
        super(properties);
    }

    //TODO make it so the player cant see inside blocks when hugging walls,
    // make the players range smaller, reduce damage they deal, increase damage they take

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        // Prevent applying changes 2 times per tick
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }
        // Only if we have the mask
        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GNAT_HAT.get(), event.player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if (!maskStack.isEmpty())
        {
            if (event.player.isAlive())
            {
                addOrReplaceModifier(event.player, ForgeMod.REACH_DISTANCE.get(), GNATS_MASK_REACH_MODIFIER_ID, -2.6F, AttributeModifier.Operation.ADDITION);
                addOrReplaceModifier(event.player, Attributes.ATTACK_DAMAGE, GNATS_MASK_DAMAGE_MODIFIER_ID, -2.0F, AttributeModifier.Operation.ADDITION);
            }
            else
            {
                removeModifier(event.player, ForgeMod.REACH_DISTANCE.get(), GNATS_MASK_REACH_MODIFIER_ID);
                removeModifier(event.player, Attributes.ATTACK_DAMAGE, GNATS_MASK_DAMAGE_MODIFIER_ID);
            }
        }
        else
        {
            removeModifier(event.player, ForgeMod.REACH_DISTANCE.get(), GNATS_MASK_REACH_MODIFIER_ID);
            removeModifier(event.player, Attributes.ATTACK_DAMAGE, GNATS_MASK_DAMAGE_MODIFIER_ID);
        }
    }

    private static void removeModifier(PlayerEntity player, Attribute attribute, UUID id)
    {
        ModifiableAttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null)
        {
            attributeInstance.removeModifier(modifier);
        }
    }

    private static void addOrReplaceModifier(PlayerEntity player, Attribute attribute, UUID id, float amount, AttributeModifier.Operation operation)
    {
        ModifiableAttributeInstance attributeInstance = player.getAttribute(attribute);
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
    
    @Override
    public float getScale(PlayerEntity player) {
        return 0.22F;
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
    {
        // put your model here
        if (!(this.model instanceof GnatHatModel))
        {
            model = new GnatHatModel<>();
        }

        GnatHatModel<?> maskModel = (GnatHatModel<?>) this.model;
        ICurio.RenderHelper.followHeadRotations(livingEntity, maskModel.base);
        IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, maskModel.renderType(TEXTURE), false, stack.hasFoil());
        maskModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
