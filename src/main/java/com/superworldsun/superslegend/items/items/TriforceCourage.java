package com.superworldsun.superslegend.items.items;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class TriforceCourage extends Item implements ICurioItem
{
	public TriforceCourage(Properties properties)
	{
		super(properties);
	}

	//TODO eventually make into curio item with abilties

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
		UUID walkSpeedId = UUID.fromString("8a54df4a-9d09-4894-9adb-4dd2bc475312");
		map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(walkSpeedId, "Curio walk speed", 0.05f, AttributeModifier.Operation.ADDITION));
		return map;
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getItemInHand(hand);
		 BlockPos currentPos = player.blockPosition();
		 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1f, 1f);
					player.addEffect(new EffectInstance(Effect.byId(1), 6000, 0, false, true));
					player.addEffect(new EffectInstance(Effect.byId(11), 6000, 0, false, false));
					player.getCooldowns().addCooldown(this, 100);

		return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GREEN + "This will give you the Courage to fight for what you believe in."));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
	}

	/*@SubscribeEvent
	public static void onLivingHurt(LivingAttackEvent event)
	{
		// Check if it is the Player who takes damage.
		if (event.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			// Get the Ring as an ItemStack
			ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.TRIFORCE_COURAGE.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			// Check if player is wearing it.
			if (!stack.isEmpty())
			{

				//TODO (For some reason the Rands dont always play all functions in brackets
				//example, Sometimes it will play the sound, but not cancel damage, or vice versa.
				int rand = random.nextInt(5);

				if(rand == 0)
				{
					{
						event.setCanceled(true);
						player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1F, 1F);
						player.displayClientMessage(new TranslationTextComponent(TextFormatting.GRAY + "Dodged"), true);
					}
				}
				if(rand == 1)
				{
					{
						event.setCanceled(true);
						player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1F, 1F);
						player.displayClientMessage(new TranslationTextComponent(TextFormatting.GRAY + "Dodged"), true);
					}
				}
				if(rand == 2)
				{

				}
				if(rand == 3)
				{

				}
				if(rand == 4)
				{

				}


				// Don't do a Check to see if the damage comes from DamageSource.GENERIC. I don't know what mob/block uses the "GENERIC" damage in the game so I normally do a (event.getSource !=
				// DamageSource.*Type*) if I don't want it to take less damage from something in particular.

			}
		}
	}*/
} 

