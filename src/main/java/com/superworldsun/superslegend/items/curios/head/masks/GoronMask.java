package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.GoronPlayerModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.interfaces.IJumpingEntity;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GoronMask extends Item implements IPlayerModelChanger, IEntityResizer, ICurioItem
{
	private static final UUID GORON_WATER_MODIFIER_ID = UUID.fromString("0fd3562e-c58f-4c6c-b912-d9d6c36bb5cb");
	
	public GoronMask()
	{
		super(new Item.Properties().stacksTo(1).tab(SupersLegendMain.APPAREL));
	}
	
	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event)
	{
		if (!(event.getEntity() instanceof PlayerEntity))
		{
			return;
		}
		
		ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), event.getEntityLiving()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		if (!maskStack.isEmpty())
		{
			if (event.getSource() == DamageSource.LAVA || event.getSource().isFire())
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		// Prevent applying changes 2 times per tick
		if (event.phase == TickEvent.Phase.START)
		{
			return;
		}
		
		// Only if we have boots
		ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), event.player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		if (!maskStack.isEmpty())
		{
			if (event.player.isInWater())
			{
				IJumpingEntity jumpingPlayer = (IJumpingEntity) event.player;
				
				if (!jumpingPlayer.isJumping())
				{
					Vector3d motion = event.player.getDeltaMovement();
					event.player.setDeltaMovement(motion.x, -0.6, motion.z);
				}
				
				if (event.player.isOnGround())
				{
					// +100%
					addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID, -1.0F, AttributeModifier.Operation.MULTIPLY_TOTAL);
				}
				else
				{
					removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID);
				}
			}
			else
			{
				removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID);
			}
		}
		else
		{
			removeModifier(event.player, Attributes.MOVEMENT_SPEED, GORON_WATER_MODIFIER_ID);
			removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID);
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
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_RED + "The face of a Goron"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "Your skin is like stone and cannot stay withered"));
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public PlayerModel<AbstractClientPlayerEntity> getPlayerModel(AbstractClientPlayerEntity player)
	{
		return new GoronPlayerModel();
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public ResourceLocation getPlayerTexture(AbstractClientPlayerEntity player)
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/goron_player.png");
	}
	
	@Override
	public float getScale(PlayerEntity player)
	{
		return 1.5F;
	}
	
	@Override
	public float getRenderScale(PlayerEntity player)
	{
		return 1.0F;
	}
}