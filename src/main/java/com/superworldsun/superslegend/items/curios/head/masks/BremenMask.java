package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.List;
import java.util.UUID;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.keys.KeyBindings;
import com.superworldsun.superslegend.client.model.armor.BremenMaskModel;
import com.superworldsun.superslegend.client.sound.BremenMaskSound;
import com.superworldsun.superslegend.entities.ai.FollowBremenMaskGoal;
import com.superworldsun.superslegend.interfaces.IMaskAbility;

import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class BremenMask extends NonEnchantItem implements IMaskAbility, ICurioItem
{
	@OnlyIn(Dist.CLIENT)
	private Object model;
	// put your texture here
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/armor/bremen_mask.png");
	
	public BremenMask(Properties properties)
	{
		super(properties);
	}
	
	// Adds goal for following players with Bremen mask into every animal
	@SubscribeEvent
	public static void onEntityConstructing(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof AnimalEntity)
		{
			AnimalEntity animal = (AnimalEntity) event.getEntity();
			
			if (!(animal.getNavigation() instanceof GroundPathNavigator) && !(animal.getNavigation() instanceof FlyingPathNavigator))
			{
				return;
			}
			
			animal.goalSelector.addGoal(3, new FollowBremenMaskGoal(animal, 1.2D, false));
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		String keybind = KeyBindings.MASK_ABILITY.getKey().getDisplayName().getString();
		list.add(new StringTextComponent(TextFormatting.WHITE + "A mask animals would love!"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold '" + keybind + "' to have animals follow you"));
	}
	
	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		if (livingEntity.getType() != EntityType.PLAYER)
			return;
		
		if (isPlayerUsingAbility((PlayerEntity) livingEntity) && livingEntity.isSprinting())
		{
			livingEntity.setSprinting(false);
		}
	}
	
	@Override
	public void startUsingAbility(PlayerEntity player)
	{
		if (player.level.isClientSide)
		{
			playMaskSound(player);
		}
		
		UUID slowId = UUID.fromString("7176f8ab-df6b-4065-9232-3c314fadb655");
		// -0.3 is 30% slower
		AttributeModifier modifier = new AttributeModifier(slowId, "Bremen Mask Slow", -0.3, Operation.MULTIPLY_BASE);
		ModifiableAttributeInstance movespeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
		movespeed.addTransientModifier(modifier);
		IMaskAbility.super.startUsingAbility(player);
	}
	
	@Override
	public void stopUsingAbility(PlayerEntity player)
	{
		UUID slowId = UUID.fromString("7176f8ab-df6b-4065-9232-3c314fadb655");
		AttributeModifier modifier = player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(slowId);
		ModifiableAttributeInstance movespeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
		
		if (modifier != null)
		{
			movespeed.removeModifier(modifier);
		}
		
		IMaskAbility.super.stopUsingAbility(player);
	}
	
	@OnlyIn(Dist.CLIENT)
	private void playMaskSound(PlayerEntity player)
	{
		Minecraft client = Minecraft.getInstance();
		client.getSoundManager().play(new BremenMaskSound(player));
	}
	
	@Override
	public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		return true;
	}
	
	// TODO the mask is a bit small, it could probably be remade or made larger
	@OnlyIn(Dist.CLIENT)
	@Override
	public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		// put your model here
		if (!(this.model instanceof BremenMaskModel))
		{
			model = new BremenMaskModel<>();
		}
		
		BremenMaskModel<?> maskModel = (BremenMaskModel<?>) this.model;
		ICurio.RenderHelper.followHeadRotations(livingEntity, maskModel.base);
		IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, maskModel.renderType(TEXTURE), false, stack.hasFoil());
		maskModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}