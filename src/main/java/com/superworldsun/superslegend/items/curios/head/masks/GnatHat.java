package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.GnatHatModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GnatHat extends NonEnchantItem implements IEntityResizer, ICurioItem {
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/armor/gnat_hat.png");
	private static final float PLAYER_SCALE_MULTIPLIER = 0.22F;
	private static final float ATTACK_DAMAGE_BONUS = -2.0F;
	private static final float REACH_DISTANCE_BONUS = -2.6F;
	private Object cachedModel;

	public GnatHat(Properties properties) {
		super(properties);
	}

	// TODO: make it so the player cant see inside blocks when hugging walls
	// TODO: make player take more damage?

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			return;
		}

		Optional<ImmutableTriple<String, Integer, ItemStack>> maskCurio = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GIANTSMASK.get(), event.player);

		if (maskCurio.isPresent()) {
			applyMaskAttributeModifiers(event.player);
		} else {
			removeMaskAttributeModifiers(event.player);
		}
	}

	private static void applyMaskAttributeModifiers(PlayerEntity player) {
		UUID modifiersId = UUID.fromString("232b49dc-54ca-47c5-99ce-c3c84ac1dda9");
		addOrReplaceModifier(player, ForgeMod.REACH_DISTANCE.get(), modifiersId, REACH_DISTANCE_BONUS, AttributeModifier.Operation.ADDITION);
		addOrReplaceModifier(player, Attributes.ATTACK_DAMAGE, modifiersId, ATTACK_DAMAGE_BONUS, AttributeModifier.Operation.ADDITION);
	}

	private static void removeMaskAttributeModifiers(PlayerEntity player) {
		UUID modifiersId = UUID.fromString("232b49dc-54ca-47c5-99ce-c3c84ac1dda9");
		removeModifier(player, ForgeMod.REACH_DISTANCE.get(), modifiersId);
		removeModifier(player, Attributes.ATTACK_DAMAGE, modifiersId);
	}

	private static void removeModifier(PlayerEntity player, Attribute attribute, UUID id) {
		ModifiableAttributeInstance attributeInstance = player.getAttribute(attribute);
		AttributeModifier modifier = attributeInstance.getModifier(id);

		if (modifier != null) {
			attributeInstance.removeModifier(modifier);
		}
	}

	private static void addOrReplaceModifier(PlayerEntity player, Attribute attribute, UUID id, float amount, AttributeModifier.Operation operation) {
		ModifiableAttributeInstance attributeInstance = player.getAttribute(attribute);
		AttributeModifier modifier = attributeInstance.getModifier(id);

		if (modifier != null && modifier.getAmount() != amount) {
			attributeInstance.removeModifier(modifier);
			modifier = new AttributeModifier(id, id.toString(), amount, operation);
		} else if (modifier == null) {
			modifier = new AttributeModifier(id, id.toString(), amount, operation);
		}

		if (modifier != null && !attributeInstance.hasModifier(modifier)) {
			attributeInstance.addPermanentModifier(modifier);
		}
	}

	@Override
	public float getScale(PlayerEntity player) {
		return PLAYER_SCALE_MULTIPLIER;
	}

	@Override
	public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing,
			float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
		if (cachedModel == null) {
			cachedModel = new GnatHatModel<>();
		}

		GnatHatModel<?> maskModel = (GnatHatModel<?>) cachedModel;
		ICurio.RenderHelper.followHeadRotations(livingEntity, maskModel.base);
		IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, maskModel.renderType(TEXTURE), false, stack.hasFoil());
		maskModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
