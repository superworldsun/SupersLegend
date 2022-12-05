package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.player.GoronPlayerModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.interfaces.IJumpingEntity;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import com.superworldsun.superslegend.registries.AttributeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GoronMask extends Item implements IPlayerModelChanger, IEntityResizer, ICurioItem
{
	private static final UUID GORON_WATER_MODIFIER_ID = UUID.fromString("9198efe1-249e-4dc9-825b-e79aa2d3e2cf");
	
	public GoronMask()
	{
		super(new Item.Properties().stacksTo(1).tab(SupersLegendMain.APPAREL));
	}
	
	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		livingEntity.clearFire();
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
		multimap.put(AttributeInit.HELL_HEAT_RESISTANCE.get(), new AttributeModifier(uuid, "Hardcoded Modifier", 0.5F, Operation.ADDITION));
		return multimap;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_RED + "The face of a Goron"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Your skin is stone and cannot be burned"));
		list.add(new StringTextComponent(TextFormatting.RED + "Not a strong swimmer"));
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
		return 1.52F;
	}
	
	@Override
	public float getRenderScale(PlayerEntity player)
	{
		return 1.0F;
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Pre event)
	{
		ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), event.getPlayer()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		if (!maskStack.isEmpty())
		{
			// this code removes visual fire from player
			DataParameter<Byte> dataSharedFlagsId = ObfuscationReflectionHelper.getPrivateValue(Entity.class, null, "field_184240_ax");
			byte sharedData = event.getPlayer().getEntityData().get(dataSharedFlagsId);
			event.getPlayer().getEntityData().set(dataSharedFlagsId, (byte) (sharedData & -2));
			event.getPlayer().clearFire();
		}
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

	//TODO Make it so the player can last underwater longer. (make it so their air supply goes down slower)
	@SubscribeEvent
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
					Vector3d motion = event.player.getDeltaMovement();
					event.player.setDeltaMovement(motion.x, -0.5, motion.z);
				}
				if (jumpingPlayer.isJumping())
				{
					Vector3d motion = event.player.getDeltaMovement();
					event.player.setDeltaMovement(motion.x, -0.5, motion.z);
				}

				if (event.player.isOnGround() || !event.player.isOnGround())
				{
					// -70%
					addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID, -0.7F, Operation.MULTIPLY_TOTAL);
				}
				else
				{
					removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GORON_WATER_MODIFIER_ID);
				}
			}
			else
			{
				// -10%
				addOrReplaceModifier(event.player, Attributes.MOVEMENT_SPEED, GORON_WATER_MODIFIER_ID, -0.1F, Operation.MULTIPLY_TOTAL);
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

	private static void addOrReplaceModifier(PlayerEntity player, Attribute attribute, UUID id, float amount, Operation operation)
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
}