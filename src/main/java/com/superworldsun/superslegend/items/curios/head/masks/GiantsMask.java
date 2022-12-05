package com.superworldsun.superslegend.items.curios.head.masks;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.AllNightMaskModel;
import com.superworldsun.superslegend.client.model.armor.GiantsMaskModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.mana.ManaProvider;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GiantsMask extends Item implements IEntityResizer, ICurioItem
{
    private static final UUID GIANTS_MASK_MODIFIER_ID = UUID.fromString("dfb43a2f-8a3f-476b-8e4c-89f48601cda6");

    @OnlyIn(Dist.CLIENT)
    private Object model;
    // put your texture here
    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/armor/giants_mask.png");

    public GiantsMask(Properties properties) {
        super(properties);
    }

    float manaCost = 0.01F;

    //TODO add so giants can step up 1 tall blocks, have further attack distance,
    // slower walk, dont take as much fall damage, swim alot slower
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        PlayerEntity player = (PlayerEntity) livingEntity;
        boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
        if (!player.abilities.instabuild) {
            float manaCost = 0.01F;
            ManaProvider.get(player).spendMana(manaCost);
        }
        if (hasMana)
        {
            addOrReplaceModifier(player, ForgeMod.REACH_DISTANCE.get(), GIANTS_MASK_MODIFIER_ID, 20.0F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        }
        //TODO when the player takes off the mask before it reaches 0, they will still have the reach distance
        else
        {
            removeModifier(player, ForgeMod.REACH_DISTANCE.get(), GIANTS_MASK_MODIFIER_ID);
        }
    }

    //This dosent work, look into a way to equip items on right click
    @Deprecated
    public boolean canRightClickEquip(ItemStack stack) {
        return defaultInstance.canRightClickEquip();
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
        float manaCost = 0.01F;
        boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
        return hasMana ? 4.0F : 1.0F;
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
        if (!(this.model instanceof GiantsMaskModel))
        {
            model = new GiantsMaskModel<>();
        }

        GiantsMaskModel<?> maskModel = (GiantsMaskModel<?>) this.model;
        ICurio.RenderHelper.followHeadRotations(livingEntity, maskModel.base);
        IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, maskModel.renderType(TEXTURE), false, stack.hasFoil());
        maskModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
