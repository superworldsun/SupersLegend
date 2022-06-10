package com.superworldsun.superslegend.items.items;

import java.util.List;
import java.util.UUID;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.events.MaxHealthEvents;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class HeartContainer extends Item
{
	public static final UUID HEARTS_MODIFIER_ID = UUID.fromString("3dc4214d-14eb-455c-9700-a2ab1433dfcc");

	public HeartContainer(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack itemstack = player.getItemInHand(hand);
		AttributeModifier heartsModifier = player.getAttribute(Attributes.MAX_HEALTH).getModifier(HEARTS_MODIFIER_ID);
		AttributeModifier baseModifier = player.getAttribute(Attributes.MAX_HEALTH).getModifier(MaxHealthEvents.BASE_HEALTH_MODIFIER_ID);
		double playerBaseHealth = player.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
		
		// Can't use if already have 40 or more maximum health (base + hearts, bonuses from other mods don't count)
		if (heartsModifier != null && playerBaseHealth + baseModifier.getAmount() + heartsModifier.getAmount() >= 40)
		{
			return ActionResult.fail(itemstack);
		}
		else
		{
			if (heartsModifier == null)
			{
				heartsModifier = new AttributeModifier(HEARTS_MODIFIER_ID, "Hearts", 2.0D, Operation.ADDITION);
			}
			else
			{
				player.getAttribute(Attributes.MAX_HEALTH).removeModifier(heartsModifier);
				heartsModifier = new AttributeModifier(HEARTS_MODIFIER_ID, "Hearts", heartsModifier.getAmount() + 2.0D, Operation.ADDITION);
			}

			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.HEART.get(), SoundCategory.PLAYERS, 1f, 1f);
			itemstack.shrink(1);
			// Add 2 health (1 heart)
			player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(heartsModifier);
			// Also restore 2 health
			player.heal(2.0F);
			return ActionResult.consume(itemstack);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.Clone event)
	{
		// We are copying health modifier only on respawn
		if (!event.isWasDeath())
		{
			return;
		}
		
		AttributeModifier heartsModifier = event.getOriginal().getAttribute(Attributes.MAX_HEALTH).getModifier(HEARTS_MODIFIER_ID);
		
		if (heartsModifier != null)
		{
			event.getPlayer().getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(heartsModifier);
			// Also update current health
			event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.RED + "Increases Maximum Health"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
	}
}
