package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.player.ZoraPlayerModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaskZoramask extends NonEnchantItem implements IPlayerModelChanger, IEntityResizer, ICurioItem
{
	private static final UUID ZORA_WATER_MODIFIER_ID = UUID.fromString("734af1f7-e76b-47f0-ba52-5a1a12a9edd2");
	float manaCost = 0.03F;
	
	public MaskZoramask(Properties properties)
	{
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public PlayerModel<AbstractClientPlayerEntity> getPlayerModel(AbstractClientPlayerEntity player)
	{
		return new ZoraPlayerModel();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ResourceLocation getPlayerTexture(AbstractClientPlayerEntity player)
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/zora_player.png");
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "The face of a Zora"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "You can swim with the grace of a Zora"));
	}

	//TODO Make it so when the player is underwater,swimming,have nothing in main hand, and has mana
	// the player will create a aura around them that will deal high damage to all nearyby entities
	// that are also in the water. There will also be particles around the player to show effect.
	/*@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent.RightClickEmpty event)
	{

	}*/

	@Override
	public float getScale(PlayerEntity player) {
		return 1.32F;
	}

	@Override
	public float getRenderScale(PlayerEntity player) {
		return 1.0F;
	}

	//TODO ADD WEAKNESS TO ICE/FIRE/ELECTRICITY
	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		PlayerEntity player = (PlayerEntity) livingEntity;
		boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;

		if(player.isUnderWater())
		{
			player.addEffect(new EffectInstance(Effect.byId(13), 10, 0, false, false, false));
		}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
		UUID swimSpeedId = UUID.fromString("a1376302-aa12-4076-a7a5-f1e4c96b4bca");
		map.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(swimSpeedId, "Zora swim speed", 1.5f, AttributeModifier.Operation.ADDITION));
		return map;
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		// Prevent applying changes 2 times per tick
		if (event.phase == TickEvent.Phase.START)
		{
			return;
		}

		// Only if we have the mask
		ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_ZORAMASK.get(), event.player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

		if (!maskStack.isEmpty())
		{
			if (event.player.isInWater())
			{
				if (event.player.isSwimming())
				{
					// +60%
					addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_WATER_MODIFIER_ID, 0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
				}
				else
				{
					removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_WATER_MODIFIER_ID);
				}
			}
		}
		else
		{
			removeModifier(event.player, Attributes.MOVEMENT_SPEED, ZORA_WATER_MODIFIER_ID);
			removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_WATER_MODIFIER_ID);
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
}